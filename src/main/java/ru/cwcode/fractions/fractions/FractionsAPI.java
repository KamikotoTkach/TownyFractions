package ru.cwcode.fractions.fractions;

import net.kyori.adventure.audience.Audience;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.cwcode.fractions.config.FractionsStorage;
import ru.cwcode.fractions.config.Messages;
import ru.cwcode.fractions.config.PlayerStorage;
import ru.cwcode.fractions.utils.Validate;
import tkachgeek.tkachutils.messages.MessageReturn;

import java.util.List;
import java.util.Optional;

public class FractionsAPI {
  public static FractionsStorage cfg = FractionsStorage.getInstance();
  
  public static void setRank(Player general, FractionPlayer fraction_player, String rank_name) throws MessageReturn {
    FractionPlayer fraction_general = PlayerStorage.get(general);
  
    Validate.canChangeRank(fraction_general);
    Validate.hasFraction(fraction_player);
    Validate.equalFractions(fraction_general, fraction_player);
    Validate.rankGreater(fraction_general, fraction_player);
  
    Optional<Rank> rank = fraction_general.getFraction().getRank(rank_name);
    if (!rank.isPresent()) {
      Messages.getInstance().is_not_fraction_rank.throwback();
    }
    fraction_player.setRank(rank.get());
    Messages.getInstance().rank_successfully_set.send(general);
  }
  
  public static void invitePlayer(Player general, FractionPlayer fraction_player) throws MessageReturn {
    FractionPlayer fraction_general = PlayerStorage.get(general);
  
    Validate.canChangeMembers(fraction_general);
  
    fraction_player.addInvite(fraction_general.getFraction().name());
    Messages.getInstance().player_invited_successfully.send(general);
  }
  
  public static void kickPlayer(Player general, FractionPlayer fraction_player) throws MessageReturn {
    FractionPlayer fraction_general = PlayerStorage.get(general);
  
    Validate.hasFraction(fraction_player);
    Validate.equalFractions(fraction_general, fraction_player);
    Validate.canChangeMembers(fraction_general);
    Validate.rankGreater(fraction_general, fraction_player);
  
    fraction_player.kickedFraction();
    Messages.getInstance().player_kicked_successfully.send(general);
  }
  
  public static List<FractionInstance> getFractions() {
    return cfg.getFractions();
  }
  
  public static boolean hasFraction(String name) {
    return cfg.hasFraction(name);
  }
  
  public static boolean isBandit(CommandSender sender) {
    if (sender instanceof Player) {
      Optional<FractionPlayer> fractionPlayer = PlayerStorage.get(sender);
      return fractionPlayer.isPresent() && fractionPlayer.get().hasFraction() && fractionPlayer.get().getFraction().isBanditFraction();
    }
    return false;
  }
  
  public static Optional<FractionInstance> getFraction(String name) {
    return getFractions().stream().filter(x -> x.name().equals(name)).findFirst();
  }
  
  public static Audience getBanditAudience() {
    return Audience.audience(PlayerStorage.getOnlineFractionPlayersWithFraction(getFraction(FractionName.BANDIT)));
  }
  
  public static Audience getPoliceAudience() {
    return Audience.audience(PlayerStorage.getOnlineFractionPlayersWithFraction(getFraction(FractionName.POLICE)));
  }
  
  public static Audience getMilitaryAudience() {
    return Audience.audience(PlayerStorage.getOnlineFractionPlayersWithFraction(getFraction(FractionName.MILITARY)));
  }
  
  public static Audience getMilitaryAndPoliceAudience() {
    return Audience.audience(PlayerStorage.getOnlineFractionPlayersWithFractions(getFraction(FractionName.MILITARY), getFraction(FractionName.POLICE)));
  }
  
  public static FractionInstance getFraction(FractionName fractionName) {
    return getFraction(fractionName.getName()).get();
  }
  
  public static boolean hasTopRank(CommandSender sender) {
    var fp = FractionPlayer.get(sender);
    if (fp.isEmpty()) return false;
    if (!fp.get().hasFraction()) return false;
    return fp.get().getFraction().hasTopRank(fp.get());
  }
  
  public enum FractionName {
    BANDIT("Бандиты"), MILITARY("Военные"), POLICE("Полиция");
    
    private final String name;
    
    FractionName(String name) {
      this.name = name;
    }
    
    public String getName() {
      return name;
    }
  }
}
