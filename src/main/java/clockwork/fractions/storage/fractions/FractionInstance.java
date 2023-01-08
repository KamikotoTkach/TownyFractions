package clockwork.fractions.storage.fractions;

import clockwork.fractions.storage.FractionPlayer;
import clockwork.fractions.storage.Rank;
import clockwork.fractions.storage.config.Messages;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import tkachgeek.tkachutils.messages.MessageReturn;

import java.util.ArrayList;
import java.util.List;
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
  public String name;
  public List<Rank> ranks = new ArrayList<>();
  
  public FractionInstance(String name) {
    this.name = name;
  }
  
  public FractionInstance() {
  }
  
  public FractionInstance setRanks(List<Rank> ranks) {
    this.ranks = ranks;
    return this;
  }
  
  
  public String getName() {
    return name;
  }
  
  public boolean canLeave(FractionPlayer player) {
    return true;
  }
  
  public void onLeave(FractionPlayer player) throws MessageReturn {
    if (!canLeave(player)) {
      Messages.getInstance().you_cannot_leave.throwback();
    }
  }
}
