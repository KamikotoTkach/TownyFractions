package ru.cwcode.fractions.criminal;

import net.kyori.adventure.audience.Audience;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import ru.cwcode.fractions.Fractions;
import ru.cwcode.fractions.config.Messages;
import ru.cwcode.fractions.config.PlayerStorage;
import ru.cwcode.fractions.fractions.FractionInstance;
import ru.cwcode.fractions.fractions.FractionPlayer;
import ru.cwcode.fractions.fractions.FractionsAPI;
import tkachgeek.config.minilocale.Message;
import tkachgeek.config.minilocale.Placeholder;
import tkachgeek.config.minilocale.Placeholders;
import tkachgeek.tkachutils.messages.MessageReturn;
import tkachgeek.tkachutils.scheduler.Scheduler;
import tkachgeek.townyterritory.territory.Territory;
import tkachgeek.townyterritory.territory.TerritoryResident;

import java.util.*;

public class Raid {
  private final FractionInstance aggressor_fraction;
  private final Territory aggressor_territory;
  private final Territory victim;
  private final int preparing = CriminalStorage.getInstance().raidPreparing;
  private final int duration = CriminalStorage.getInstance().raidMaxDuration;
  private final int timeoutScheduler;
  Placeholders placeholders;
  private Direction direction = null;
  private RaidState state;
  private int aggressorKills;
  private int victimKills;
  private Set<UUID> aggressor_members = new HashSet<>();
  private Set<UUID> victim_members = new HashSet<>();
  
  public Raid(Territory victim, @Nullable FractionInstance aggressor_fraction, @Nullable Territory aggressor_territory) throws MessageReturn {
    this.state = RaidState.PREPARING;
    this.aggressor_fraction = aggressor_fraction;
    this.aggressor_territory = aggressor_territory;
    this.victim = victim;
    
    this.setupDirection(victim, aggressor_fraction, aggressor_territory);
    
    if (direction == null)
      Messages.getInstance().you_cant_raid_$name.throwback(Placeholder.add("name", victim.getName()));
    
    placeholders = Placeholder.add("aggressor", direction.aggressorIsFraction() ? aggressor_fraction.name() : aggressor_territory.getName())
                              .add("victim", victim.getName());
    
    direction.preparingSendMessages(this.aggressorAudience(), this.victim, placeholders);
    
    Scheduler.create(this).async().perform(Raid::start).register(Fractions.plugin, preparing * 20);
    
    this.timeoutScheduler = Scheduler.create(this)
                                     .perform(raid -> raid.win(Winner.TIE))
                                     .register(Fractions.plugin, (preparing + duration) * 20);
  }
  
  private void setupDirection(Territory victim, @Nullable FractionInstance aggressor_fraction, @Nullable Territory aggressor_territory) {
    if (aggressor_territory == null && aggressor_fraction != null && aggressor_fraction.isMilitaryFraction() && victim.isBandit())
      direction = Direction.MILITARY_TO_BANDIT;
    else if (aggressor_fraction == null && aggressor_territory != null && aggressor_territory.isBandit() && !victim.isBandit())
      direction = Direction.BANDIT_TO_PEACEFUL;
    else if (aggressor_fraction == null && aggressor_territory != null && !aggressor_territory.isBandit() && victim.isBandit())
      direction = Direction.PEACEFUL_TO_BANDIT;
    else if (aggressor_fraction == null && aggressor_territory != null && aggressor_territory.isBandit() && victim.isBandit())
      direction = Direction.BANDIT_TO_BANDIT;
  }
  
  private void start() {
    this.state = RaidState.STARTED;
    
    this.aggressorKills = 0;
    this.victimKills = 0;
    
    direction.startSendMessages(this.aggressorAudience(), this.victim, placeholders);
    
    this.addMembers();
    
    Scheduler.create(this).async()
             .until(Raid::isStarted)
             .perform(Raid::everySecondTick)
             .register(Fractions.plugin, 20);
  }
  
  private void addMembers() {
    if (direction.aggressorIsFraction) {
      aggressor_members.addAll(PlayerStorage.getAllPlayersWithFraction(aggressor_fraction).stream().map(OfflinePlayer::getUniqueId).toList());
  
      for (FractionPlayer fractionPlayer : PlayerStorage.getAllFractionPlayersWithFraction(aggressor_fraction)) {
        fractionPlayer.incrementStatistics("Рейдов");
      }
    } else {
      List<UUID> list = new ArrayList<>();
      for (TerritoryResident offlinePlayer : aggressor_territory.getResidents()) {
        list.add(offlinePlayer.getPlayer().getUniqueId());
      }
      aggressor_members.addAll(list);
    }
  
    List<UUID> list = new ArrayList<>();
  
    for (TerritoryResident territoryResident : victim.getResidents()) {
      list.add(territoryResident.getPlayer().getUniqueId());
    }
  
    if (direction == Direction.BANDIT_TO_PEACEFUL) {
      for (OfflinePlayer player : PlayerStorage.getAllPlayersWithFraction(FractionsAPI.getFraction(FractionsAPI.FractionName.POLICE))) {
        list.add(player.getUniqueId());
      }
    
      for (OfflinePlayer offlinePlayer : PlayerStorage.getAllPlayersWithFraction(FractionsAPI.getFraction(FractionsAPI.FractionName.MILITARY))) {
        list.add(offlinePlayer.getUniqueId());
      }
    
      victim_members.addAll(list);
    }
  }
  
  public boolean isStarted() {
    return state == RaidState.STARTED;
  }
  
  public RaidState getState() {
    return this.state;
  }
  
  public boolean isMember(Player player) {
    return aggressor_members.contains(player.getUniqueId()) || victim_members.contains(player.getUniqueId());
  }
  
  public void progressRaid(Player killed_player) {
    if (this.aggressor_members.contains(killed_player.getUniqueId())) {
      this.victimKills += 1;
    }
    
    if (this.victim_members.contains(killed_player.getUniqueId())) {
      this.aggressorKills += 1;
    }
    
    this.everySecondTick();
  }
  
  private void everySecondTick() {
    if (aggressorKills >= victim.getOnlinePlayers().size()) {
      this.win(Winner.AGGRESSOR);
    }
    
    if (victimKills >= this.aggressorOnline()) {
      this.win(Winner.VICTIM);
    }
  }
  
  private void win(Winner winner) {
    switch (winner) {
      case AGGRESSOR -> {
        direction.aggressorWinSendMessages(this.aggressorAudience(), this.victim, placeholders);
        
        switch (direction) {
          case BANDIT_TO_PEACEFUL -> {
            victim.setUnderControl(aggressor_territory);
          }
          case MILITARY_TO_BANDIT, BANDIT_TO_BANDIT, PEACEFUL_TO_BANDIT -> {
            victim.destruct();
          }
        }
      }
      case VICTIM -> {
        direction.victimWinSendMessages(this.aggressorAudience(), this.victim, placeholders);
      }
      case TIE -> {
        direction.tieSendMessages(this.aggressorAudience(), this.victim, placeholders);
      }
    }
    endRaid();
  }
  
  private void endRaid() {
    CriminalAPI.raid = null;
    Scheduler.cancelTask(timeoutScheduler);
  }
  
  private int aggressorOnline() {
    if (direction.aggressorIsFraction) {
      return aggressor_fraction.getOnlinePlayers();
    } else {
      return aggressor_territory.getOnlinePlayers().size();
    }
  }
  
  private Audience aggressorAudience() {
    if (direction.aggressorIsFraction) {
      return FractionsAPI.getMilitaryAudience();
    } else {
      return aggressor_territory;
    }
  }
  
  enum Winner {
    AGGRESSOR,
    VICTIM,
    TIE
  }
  
  enum Direction {
    BANDIT_TO_PEACEFUL(false,
                       Messages.getInstance().BANDIT_TO_PEACEFUL_startMessage_toAggressor,
                       Messages.getInstance().BANDIT_TO_PEACEFUL_preparingMessage_toAggressor,
                       Messages.getInstance().BANDIT_TO_PEACEFUL_winMessage_toAggressor,
                       Messages.getInstance().BANDIT_TO_PEACEFUL_loseMessage_toAggressor,
                       Messages.getInstance().BANDIT_TO_PEACEFUL_tieMessage_toAggressor,
                       Messages.getInstance().BANDIT_TO_PEACEFUL_startMessage_toVictim,
                       Messages.getInstance().BANDIT_TO_PEACEFUL_preparingMessage_toVictim,
                       Messages.getInstance().BANDIT_TO_PEACEFUL_winMessage_toVictim,
                       Messages.getInstance().BANDIT_TO_PEACEFUL_loseMessage_toVictim,
                       Messages.getInstance().BANDIT_TO_PEACEFUL_tieMessage_toVictim
    ),
    MILITARY_TO_BANDIT(true,
                       Messages.getInstance().MILITARY_TO_BANDIT_startMessage_toAggressor,
                       Messages.getInstance().MILITARY_TO_BANDIT_preparingMessage_toAggressor,
                       Messages.getInstance().MILITARY_TO_BANDIT_winMessage_toAggressor,
                       Messages.getInstance().MILITARY_TO_BANDIT_loseMessage_toAggressor,
                       Messages.getInstance().MILITARY_TO_BANDIT_tieMessage_toAggressor,
                       Messages.getInstance().MILITARY_TO_BANDIT_startMessage_toVictim,
                       Messages.getInstance().MILITARY_TO_BANDIT_preparingMessage_toVictim,
                       Messages.getInstance().MILITARY_TO_BANDIT_winMessage_toVictim,
                       Messages.getInstance().MILITARY_TO_BANDIT_loseMessage_toVictim,
                       Messages.getInstance().MILITARY_TO_BANDIT_tieMessage_toVictim
    ),
    BANDIT_TO_BANDIT(false,
                     Messages.getInstance().BANDIT_TO_BANDIT_startMessage_toAggressor,
                     Messages.getInstance().BANDIT_TO_BANDIT_preparingMessage_toAggressor,
                     Messages.getInstance().BANDIT_TO_BANDIT_winMessage_toAggressor,
                     Messages.getInstance().BANDIT_TO_BANDIT_loseMessage_toAggressor,
                     Messages.getInstance().BANDIT_TO_BANDIT_tieMessage_toAggressor,
                     Messages.getInstance().BANDIT_TO_BANDIT_startMessage_toVictim,
                     Messages.getInstance().BANDIT_TO_BANDIT_preparingMessage_toVictim,
                     Messages.getInstance().BANDIT_TO_BANDIT_winMessage_toVictim,
                     Messages.getInstance().BANDIT_TO_BANDIT_loseMessage_toVictim,
                     Messages.getInstance().BANDIT_TO_BANDIT_tieMessage_toVictim
    ),
    PEACEFUL_TO_BANDIT(false,
                       Messages.getInstance().PEACEFUL_TO_BANDIT_startMessage_toAggressor,
                       Messages.getInstance().PEACEFUL_TO_BANDIT_preparingMessage_toAggressor,
                       Messages.getInstance().PEACEFUL_TO_BANDIT_winMessage_toAggressor,
                       Messages.getInstance().PEACEFUL_TO_BANDIT_loseMessage_toAggressor,
                       Messages.getInstance().PEACEFUL_TO_BANDIT_tieMessage_toAggressor,
                       Messages.getInstance().PEACEFUL_TO_BANDIT_startMessage_toVictim,
                       Messages.getInstance().PEACEFUL_TO_BANDIT_preparingMessage_toVictim,
                       Messages.getInstance().PEACEFUL_TO_BANDIT_winMessage_toVictim,
                       Messages.getInstance().PEACEFUL_TO_BANDIT_loseMessage_toVictim,
                       Messages.getInstance().PEACEFUL_TO_BANDIT_tieMessage_toVictim
    );
    
    final boolean aggressorIsFraction;
    final Message startMessage_toAggressor;
    final Message preparingMessage_toAggressor;
    final Message winMessage_toAggressor;
    final Message loseMessage_toAggressor;
    final Message tieMessage_toAggressor;
    final Message startMessage_toVictim;
    final Message preparingMessage_toVictim;
    final Message winMessage_toVictim;
    final Message loseMessage_toVictim;
    final Message tieMessage_toVictim;
    
    Direction(boolean isFraction, Message startMessage_toAggressor, Message preparingMessage_toAggressor, Message winMessage_toAggressor, Message loseMessage_toAggressor, Message tieMessage_toAggressor, Message startMessage_toVictim, Message preparingMessage_toVictim, Message winMessage_toVictim, Message loseMessage_toVictim, Message tieMessage_toVictim) {
      this.startMessage_toAggressor = startMessage_toAggressor;
      this.preparingMessage_toAggressor = preparingMessage_toAggressor;
      this.aggressorIsFraction = isFraction;
      this.winMessage_toAggressor = winMessage_toAggressor;
      this.loseMessage_toAggressor = loseMessage_toAggressor;
      this.tieMessage_toAggressor = tieMessage_toAggressor;
      this.startMessage_toVictim = startMessage_toVictim;
      this.preparingMessage_toVictim = preparingMessage_toVictim;
      this.winMessage_toVictim = winMessage_toVictim;
      this.loseMessage_toVictim = loseMessage_toVictim;
      this.tieMessage_toVictim = tieMessage_toVictim;
    }
    
    public boolean aggressorIsFraction() {
      return aggressorIsFraction;
    }
    
    public void startSendMessages(Audience aggressorAudience, Territory victim, Placeholders placeholders) {
      this.startMessage_toAggressor.send(aggressorAudience, placeholders);
      this.startMessage_toVictim.send(victim, placeholders);
    }
    
    public void preparingSendMessages(Audience aggressorAudience, Territory victim, Placeholders placeholders) {
      this.preparingMessage_toAggressor.send(aggressorAudience, placeholders);
      this.preparingMessage_toVictim.send(victim, placeholders);
    }
    
    public void aggressorWinSendMessages(Audience aggressorAudience, Territory victim, Placeholders placeholders) {
      this.winMessage_toAggressor.send(aggressorAudience, placeholders);
      this.loseMessage_toVictim.send(victim, placeholders);
    }
    
    public void victimWinSendMessages(Audience aggressorAudience, Territory victim, Placeholders placeholders) {
      this.winMessage_toVictim.send(aggressorAudience, placeholders);
      this.loseMessage_toAggressor.send(victim, placeholders);
    }
    
    public void tieSendMessages(Audience aggressorAudience, Territory victim, Placeholders placeholders) {
      this.tieMessage_toAggressor.send(aggressorAudience, placeholders);
      this.tieMessage_toVictim.send(victim, placeholders);
    }
  }
}
