package ru.cwcode.fractions.fractions;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.cwcode.fractions.config.Messages;
import ru.cwcode.fractions.config.PlayerStorage;
import tkachgeek.config.minilocale.Placeholder;
import tkachgeek.tkachutils.messages.MessageReturn;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@JsonTypeInfo(
   use = JsonTypeInfo.Id.NAME,
   include = JsonTypeInfo.As.PROPERTY,
   property = "type")
@JsonSubTypes({
   @JsonSubTypes.Type(value = PoliceFraction.class, name = "police"),
   @JsonSubTypes.Type(value = MilitaryFraction.class, name = "military"),
   @JsonSubTypes.Type(value = BanditFraction.class, name = "bandit")
})

public class FractionInstance {
  private String name;
  private String prefix;
  private List<Rank> ranks = new ArrayList<>();
  
  public FractionInstance(String name, String prefix) {
    this.name = name;
    this.prefix = prefix;
  }
  
  public FractionInstance() {
  }
  
  public String name() {
    return this.name;
  }
  
  public String getPrefix() {
    return this.prefix;
  }
  
  public List<Rank> getRanks() {
    return this.ranks;
  }
  
  public FractionInstance setRanks(List<Rank> ranks) {
    this.ranks = ranks;
    return this;
  }
  
  public boolean canLeave(FractionPlayer player) {
    return true;
  }
  
  public void onLeave(FractionPlayer player) throws MessageReturn {
    if (!canLeave(player)) {
      Messages.getInstance().you_cannot_leave.throwback();
    }
  }
  
  public boolean isFractionRank(String rank_name) {
    for (Rank rank : this.getRanks()) {
      if (rank.name().equalsIgnoreCase(rank_name)) {
        return true;
      }
    }
    return false;
  }
  
  public Optional<Rank> getRank(String rank_name) {
    for (Rank rank : this.getRanks()) {
      if (rank.name().equalsIgnoreCase(rank_name)) {
        return Optional.of(rank);
      }
    }
    return Optional.empty();
  }
  
  public boolean isBanditFraction() {
    return this instanceof BanditFraction;
  }
  
  public boolean isPoliceFraction() {
    return this instanceof PoliceFraction;
  }
  
  public boolean isMilitaryFraction() {
    return this instanceof MilitaryFraction;
  }
  
  public boolean hasTopRank(FractionPlayer player) {
    return getRanks().indexOf(player.getRank()) == getRanks().size() - 1;
  }
  
  public boolean hasRank(String rank) {
    for (Rank x : getRanks()) {
      if (x.name().equals(rank)) {
        return true;
      }
    }
    return false;
  }
  
  public void onJoin(FractionPlayer fractionPlayer) {
    Messages.getInstance().you_joined_fraction_$name.send(fractionPlayer.getUUID(), Placeholder.add("name", getFormattedName()));
    fractionPlayer.setRank(getLowestRank());
  }
  
  public FractionInstance setOnlineToAttack(int onlineToAttack) {
    this.onlineToAttack = onlineToAttack;
    return this;
  }
  
  public int getTotalPlayers() {
    return PlayerStorage.getAllPlayersWithFraction(this).size();
  }
  
  public int getOnlinePlayers() {
    return PlayerStorage.getOnlineFractionPlayersWithFraction(this).size();
  }
  
  public String getFormattedName() {
    return getPrefix() + name();
  }
  
  public int getOnlineToDefence() {
    return this.onlineToDefence;
  }
  
  public FractionInstance setOnlineToDefence(int onlineToDefence) {
    this.onlineToDefence = onlineToDefence;
    return this;
  }
  
  public boolean requireInvite() {
    return requireInvite;
  }
}
