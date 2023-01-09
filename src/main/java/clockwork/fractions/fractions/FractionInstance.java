package clockwork.fractions.fractions;

import clockwork.fractions.config.Messages;
import clockwork.fractions.fractions.storage.FractionPlayer;
import clockwork.fractions.fractions.storage.Rank;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
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
   @JsonSubTypes.Type(value = ArmyFraction.class, name = "army"),
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
  
  public FractionInstance setRanks(List<Rank> ranks) {
    this.ranks = ranks;
    return this;
  }
  
  public String getName() {
    return this.name;
  }
  
  public String getPrefix() {
    return this.prefix;
  }
  
  public List<Rank> getRanks() {
    return this.ranks;
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
  
  public boolean isArmyFraction() {
    return this instanceof ArmyFraction;
  }
}
