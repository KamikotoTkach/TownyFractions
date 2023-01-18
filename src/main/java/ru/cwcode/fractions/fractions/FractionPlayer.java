package ru.cwcode.fractions.fractions;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.Nullable;
import ru.cwcode.fractions.config.FractionsStorage;
import ru.cwcode.fractions.config.Messages;
import ru.cwcode.fractions.config.PlayerStorage;
import tkachgeek.config.minilocale.Placeholder;
import tkachgeek.tkachutils.messages.MessageReturn;

import java.util.*;

public class FractionPlayer implements Audience {
  private final List<String> invitedTo = new ArrayList<>();
  private UUID uuid = null;
  private String fraction = null;
  private String rank = null;
  
  public FractionPlayer() {
  }
  
  public FractionPlayer(FractionInstance fraction, Rank rank, UUID uuid) {
    this.fraction = fraction.name();
    this.rank = rank.name();
    this.uuid = uuid;
  }
  
  public FractionPlayer(UUID uniqueId) {
    this.uuid = uniqueId;
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
    return getFraction().getRank(rank).get();
  }
  
  public void setRank(Rank rank) {
    this.rank = rank.name();
    Messages.getInstance().your_rank_was_updated_to_$rank.send(getUUID(), Placeholder.add("rank", rank.name()));
  }
  
  public boolean hasFraction() {
    return fraction != null;
  }
  
  public boolean canChangeRank() {
    return rank != null && getRank().permissions().canChangeRank();
  }
  
  public boolean canChangeMembers() {
    return getRank().permissions().canChangeMembers();
  }
  
  public boolean canRaid() {
    return getRank().permissions().canRaid();
  }
  
  public @Nullable FractionInstance getFraction() {
    return fraction == null ? null : FractionsAPI.getFraction(fraction).get();
  }
  
  public void setFraction(FractionInstance fraction) throws MessageReturn {
    leaveFraction();
    this.fraction = fraction.name();
    getFraction().onJoin(this);
  }
  
  public boolean isGreater(FractionPlayer toCheck) {
    return getFraction().getRanks().indexOf(rank) > getFraction().getRanks().indexOf(toCheck.rank);
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
    return getFraction().getRanks().indexOf(getRank()) >= getFraction().getRanks().indexOf(toCheck);
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
    uuid = PlayerStorage.getUUID(this);
    return uuid;
  }
  
  public boolean isInvited(String fraction_name) {
    return invitedTo.contains(fraction_name);
  }
  
  public void addInvite(String fraction_name) throws MessageReturn {
    if (isInvited(fraction_name)) {
      Messages.getInstance().player_has_fraction_invite.throwback();
    }
  
    invitedTo.add(fraction_name);
    Messages.getInstance().you_invited_$fraction.send(getUUID(), Placeholder.add("fraction", getFraction().name()));
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
  
    this.fraction = fraction.get().name();
  }
  
  public void leaveFraction() throws MessageReturn {
    if (fraction == null) return;
    getFraction().onLeave(this);
    Messages.getInstance().you_leaved_$fraction.send(getUUID(), Placeholder.add("fraction", getFraction().name()));
    fraction = null;
    rank = null;
  }
  
  public void kickedFraction() {
    Messages.getInstance().you_kicked_$fraction.send(getUUID(), Placeholder.add("fraction", getFraction().name()));
    fraction = null;
    rank = null;
  }
  
  public String getFormattedRank() {
    return getFraction().getPrefix() + getRank().name();
  }
  
  public boolean hasTopRank() {
    return hasFraction() && getFraction().hasTopRank(this);
  }
  
  public boolean isBandit() {
    return hasFraction() && getFraction().isBanditFraction();
  }
  
  public boolean isPoliceman() {
    return hasFraction() && getFraction().isPoliceFraction();
  }
  
  public boolean isMilitary() {
    return hasFraction() && getFraction().isMilitaryFraction();
  }
  
  @Override
  public void sendMessage(@NonNull Component message) {
    OfflinePlayer player = getPlayer();
    if (player.isOnline()) {
      player.getPlayer().sendMessage(message);
    }
  }
}
