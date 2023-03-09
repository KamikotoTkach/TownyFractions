package ru.cwcode.fractions.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.cwcode.fractions.Fractions;
import ru.cwcode.fractions.fractions.FractionInstance;
import ru.cwcode.fractions.fractions.FractionPlayer;
import tkachgeek.config.base.Reloadable;
import tkachgeek.config.yaml.YmlConfig;

import java.util.*;

public class PlayerStorage extends YmlConfig implements Reloadable {
  static PlayerStorage instance;
  @JsonProperty("игроки")
  public final HashMap<UUID, FractionPlayer> players = new HashMap<>();
  
  public PlayerStorage() {
  }
  
  public static PlayerStorage getInstance() {
    if (instance == null) load();
    return instance;
  }
  
  public static void load() {
    instance = Fractions.yml.load("PlayerStorage", PlayerStorage.class);
  }
  
  public static UUID getUUID(FractionPlayer fractionPlayer) {
    for (Map.Entry<UUID, FractionPlayer> uuidFractionPlayerEntry : getInstance().players.entrySet()) {
      if (uuidFractionPlayerEntry.getValue().equals(fractionPlayer)) {
        return uuidFractionPlayerEntry.getKey();
      }
    }
    return null;
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
  
  public static FractionPlayer get(OfflinePlayer player) {//todo: поправить код, рабочий, но из стримов был сделан
    Optional<Map.Entry<UUID, FractionPlayer>> oldPlayer = Optional.empty();
    for (Map.Entry<UUID, FractionPlayer> x : getInstance().players.entrySet()) {
      if (x.getKey().equals(player.getUniqueId())) {
        oldPlayer = Optional.of(x);
        break;
      }
    }
    if (oldPlayer.isPresent()) {
      FractionPlayer fp = oldPlayer.get().getValue();
      getInstance().players.remove(oldPlayer.get().getKey());
      getInstance().players.put(player.getUniqueId(), fp);
    } else {
      getInstance().players.put(player.getUniqueId(), new FractionPlayer(player.getUniqueId()));
    }
    return getInstance().players.get(player.getUniqueId());
  }
  
  public static FractionPlayer get(Player player) {
    return get(Bukkit.getOfflinePlayer(player.getUniqueId()));
  }
  
  public static Optional<FractionPlayer> get(String name) {
    if (Bukkit.getPlayer(name) != null) return Optional.of(get(Bukkit.getPlayer(name)));
  
    for (var playerAndFraction : getInstance().players.entrySet()) {
      if (playerAndFraction.getValue().getName().equals(name)) {
        return Optional.of(playerAndFraction.getValue());
      }
    }
    return Optional.empty();
  }
  
  public static List<OfflinePlayer> getAllPlayersWithFraction(FractionInstance fraction) {
    List<OfflinePlayer> ret = new ArrayList<>();
    for (var playerAndFraction : getInstance().players.entrySet()) {
      if (playerAndFraction.getValue().hasFraction() && playerAndFraction.getValue().getFraction().equals(fraction)) {
        ret.add(Bukkit.getOfflinePlayer(playerAndFraction.getKey()));
      }
    }
    return ret;
  }
  
  public static List<FractionPlayer> getAllFractionPlayersWithFraction(FractionInstance fraction) {
    List<FractionPlayer> ret = new ArrayList<>();
    for (var playerAndFraction : getInstance().players.entrySet()) {
      if (playerAndFraction.getValue().hasFraction() && playerAndFraction.getValue().getFraction().equals(fraction)) {
        ret.add(playerAndFraction.getValue());
      }
    }
    return ret;
  }
  
  public static List<FractionPlayer> getOnlineFractionPlayersWithFraction(FractionInstance fraction) {
    List<FractionPlayer> ret = new ArrayList<>();
    
    for (var playerAndFraction : getInstance().players.entrySet()) {
      if (Bukkit.getOfflinePlayer(playerAndFraction.getKey()).isOnline()
         && playerAndFraction.getValue().hasFraction()
         && playerAndFraction.getValue().getFraction().equals(fraction)) {
        ret.add(playerAndFraction.getValue());
      }
    }
    return ret;
  }
  
  public static List<FractionPlayer> getOnlineFractionPlayersWithFractions(FractionInstance... fractions) {
    List<FractionPlayer> ret = new ArrayList<>();
    List<FractionInstance> fractionInstances = List.of(fractions);
  
    for (var playerAndFraction : getInstance().players.entrySet()) {
      if (Bukkit.getOfflinePlayer(playerAndFraction.getKey()).isOnline()
         && playerAndFraction.getValue().hasFraction()
         && fractionInstances.contains(playerAndFraction.getValue().getFraction())) {
        ret.add(playerAndFraction.getValue());
      }
    }
    return ret;
  }
  
  public static List<Player> getOnlinePlayersWithFraction(FractionInstance fraction) {
    List<Player> ret = new ArrayList<>();
    for (var playerAndFraction : getInstance().players.entrySet()) {
      if (playerAndFraction.getValue().hasFraction() && playerAndFraction.getValue().getFraction().equals(fraction)) {
        Player player = Bukkit.getPlayer(playerAndFraction.getKey());
        if (player != null) ret.add(player);
      }
    }
    return ret;
  }
  
  @Override
  public void reload() {
    load();
  }
}
