package clockwork.fractions.fractions.storage;

import clockwork.fractions.config.FractionsStorage;
import clockwork.fractions.config.Messages;
import clockwork.fractions.config.PlayerStorage;
import clockwork.fractions.fractions.FractionInstance;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import tkachgeek.config.minilocale.Placeholder;
import tkachgeek.tkachutils.messages.MessageReturn;

import java.util.*;

public class FractionPlayer {
  transient UUID uuid = null;
  private FractionInstance fraction = null;
  private Rank rank = null;
  private final List<String> invitedTo = new ArrayList<>();
  
  public FractionPlayer() {
  }
  
  public FractionPlayer(FractionInstance fraction, Rank rank, UUID uuid) {
    this.fraction = fraction;
    this.rank = rank;
  }
  
  public static FractionPlayer get(UUID player) {
    return PlayerStorage.get(player);
  }
  
  public static FractionPlayer get(OfflinePlayer player) {
    return PlayerStorage.get(player);
  }
  
  public static FractionPlayer get(Player player) {
    return PlayerStorage.get(player);
  }
  
  public static Optional<FractionPlayer> get(CommandSender player) {
    return PlayerStorage.get(player);
  }
  public static Optional<FractionPlayer> get(String name) {
    return PlayerStorage.get(name);
  }
  
  
  public Rank getRank() {
    return rank;
  }
  
  public void setRank(Rank rank) {
    this.rank = rank;
    Messages.getInstance().your_rank_was_updated_to_$rank.send(getUUID(), Placeholder.add("rank", rank.name()));
  }
  
  public boolean hasFraction() {
    return fraction != null;
  }
  
  public boolean canChangeRank() {
    return rank != null && rank.permissions().canChangeRank();
  }
  
  public boolean canChangeMembers() {
    return rank.permissions().canChangeMembers();
  }
  
  public int getPriority() {
    return fraction.getRanks().indexOf(rank);
  }
  
  public @Nullable FractionInstance getFraction() {
    return this.fraction;
  }
  
  public boolean isGreater(FractionPlayer toCheck) {
    return fraction.getRanks().indexOf(rank) > fraction.getRanks().indexOf(toCheck.rank);
  }
  
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    
    FractionPlayer that = (FractionPlayer) o;
    
    return Objects.equals(uuid, that.uuid);
  }
  
  @Override
  public int hashCode() {
    return uuid != null ? uuid.hashCode() : 0;
  }
  
  public boolean isGreaterOrEquals(FractionPlayer toCheck) {
    return this.isGreaterOrEquals(toCheck.getRank());
  }
  
  public boolean isGreaterOrEquals(Rank toCheck) {
    return fraction.getRanks().indexOf(rank) >= fraction.getRanks().indexOf(toCheck);
  }
  
  public List<String> getInvitedTo() {
    return invitedTo;
  }
  
  public String getName() {
    return getPlayer().getName();
  }
  
  public OfflinePlayer getPlayer() {
    return Bukkit.getPlayer(getUUID());
  }
  
  public UUID getUUID() {
    if (uuid != null) return uuid;
    return PlayerStorage.getUUID(this);
  }
  
  public boolean isInvited(String fraction_name) {
    return invitedTo.contains(fraction_name);
  }
  
  public void addInvite(String fraction_name) throws MessageReturn {
    if (isInvited(fraction_name)) {
      Messages.getInstance().player_has_fraction_invite.throwback();
    }
  
    invitedTo.add(fraction_name);
    Messages.getInstance().you_invited_$fraction.send(getUUID(), Placeholder.add("fraction", fraction.getName()));
  }
  
  public void removeInvite(String fraction_name) throws MessageReturn {
    if (!isInvited(fraction_name)) {
      Messages.getInstance().you_not_invited_to_$fraction.throwback();
    }
    
    invitedTo.remove(fraction_name);
  }
  
  public void acceptInvite(String fractionName) throws MessageReturn {
    if (hasFraction()) {
      Messages.getInstance().you_has_fraction.throwback();
    }
    
    removeInvite(fractionName);
    Optional<FractionInstance> fraction = FractionsStorage.getInstance().getFraction(fractionName);
    if (!fraction.isPresent()) {
      Messages.getInstance().fraction_$name_not_exist.throwback(Placeholder.add("name", fractionName));
    }
    
    this.fraction = fraction.get();
  }
  
  public void leaveFraction() throws MessageReturn {
    fraction.onLeave(this);
    fraction = null;
    rank = null;
    Messages.getInstance().you_leaved_$fraction.send(getUUID(), Placeholder.add("fraction", fraction.getName()));
  }
  
  public void kickedFraction() {
    fraction = null;
    rank = null;
    Messages.getInstance().you_kicked_$fraction.send(getUUID(), Placeholder.add("fraction", fraction.getName()));
  }
}
