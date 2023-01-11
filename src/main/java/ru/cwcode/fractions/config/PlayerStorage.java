package ru.cwcode.fractions.config;

import com.google.common.collect.HashBiMap;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.cwcode.fractions.Fractions;
import ru.cwcode.fractions.fractions.FractionInstance;
import ru.cwcode.fractions.fractions.storage.FractionPlayer;
import tkachgeek.config.yaml.YmlConfig;

import java.util.*;

public class PlayerStorage extends YmlConfig {
  static PlayerStorage instance;
  public final HashBiMap<OfflinePlayer, FractionPlayer> players = HashBiMap.create();
  
  public PlayerStorage() {
  }
  
  public static PlayerStorage getInstance() {
    if (instance == null) load();
    return instance;
  }
  
  public static void load() {
    instance = Fractions.yml.load("PlayerStorage", PlayerStorage.class);
  }
  
  public static Optional<FractionPlayer> get(CommandSender sender) {
    if (sender instanceof OfflinePlayer player) {
      return Optional.of(get(player.getUniqueId()));
    } else {
      return Optional.empty();
    }
  }
  
  public static FractionPlayer get(UUID uuid) {
    return get(Bukkit.getOfflinePlayer(uuid));
  }
  
  public static FractionPlayer get(OfflinePlayer player) {
    Optional<Map.Entry<OfflinePlayer, FractionPlayer>> oldPlayer = getInstance().players.entrySet().stream().filter(x -> x.getKey().getUniqueId().equals(player.getUniqueId())).findFirst();
    if (oldPlayer.isPresent()) {
      FractionPlayer fp = oldPlayer.get().getValue();
      getInstance().players.remove(oldPlayer.get().getKey());
      getInstance().players.put(player, fp);
    } else {
      getInstance().players.put(player, new FractionPlayer(player.getUniqueId()));
    }
    return getInstance().players.get(player);
  }
  
  public static FractionPlayer get(Player player) {
    return get(Bukkit.getOfflinePlayer(player.getUniqueId()));
  }
  
  public static UUID getUUID(FractionPlayer fractionPlayer) {
    return instance.players.inverse().get(fractionPlayer).getUniqueId();
  }
  
  public static Optional<FractionPlayer> get(String name) {
    for (var playerAndFraction : instance.players.entrySet()) {
      if (playerAndFraction.getValue().getName().equals(name)) {
        return Optional.of(playerAndFraction.getValue());
      }
    }
  
    return Optional.empty();
  }
  
  public static List<OfflinePlayer> getAllPlayersWithFraction(FractionInstance fraction) {
    List<OfflinePlayer> ret = new ArrayList<>();
    for (var playerAndFraction : instance.players.entrySet()) {
      if (playerAndFraction.getValue().hasFraction() && playerAndFraction.getValue().getFraction().equals(fraction)) {
        ret.add(playerAndFraction.getKey());
      }
    }
    return ret;
  }
  
  public static List<FractionPlayer> getAllFractionPlayersWithFraction(FractionInstance fraction) {
    List<FractionPlayer> ret = new ArrayList<>();
    for (var playerAndFraction : instance.players.entrySet()) {
      if (playerAndFraction.getValue().hasFraction() && playerAndFraction.getValue().getFraction().equals(fraction)) {
        ret.add(playerAndFraction.getValue());
      }
    }
    return ret;
  }
  
  public static List<FractionPlayer> getOnlineFractionPlayersWithFraction(FractionInstance fraction) {
    List<FractionPlayer> ret = new ArrayList<>();
    for (var playerAndFraction : instance.players.entrySet()) {
      if (playerAndFraction.getKey().isOnline() && playerAndFraction.getValue().hasFraction() && playerAndFraction.getValue().getFraction().equals(fraction)) {
        ret.add(playerAndFraction.getValue());
      }
    }
    return ret;
  }
}
