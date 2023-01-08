package clockwork.fractions.storage.config;

import clockwork.fractions.storage.FractionPlayer;
import clockwork.fractions.Fractions;
import clockwork.fractions.storage.fractions.FractionInstance;
import com.google.common.collect.HashBiMap;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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
    if (!getInstance().players.containsKey(uuid)) {
      getInstance().players.put(Bukkit.getOfflinePlayer(uuid), new FractionPlayer());
    }
    return getInstance().players.get(uuid);
  }
  
  public static FractionPlayer get(OfflinePlayer player) {
    return get(player.getUniqueId());
  }
  
  public static FractionPlayer get(Player player) {
    return get(player.getUniqueId());
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
  
  public static List<OfflinePlayer> getPlayerWithFraction(FractionInstance fraction) {
    List<OfflinePlayer> ret = new ArrayList<>();
    for (var playerAndFraction : instance.players.entrySet()) {
      if(playerAndFraction.getValue().hasFraction() && playerAndFraction.getValue().getFraction().equals(fraction)) {
        ret.add(playerAndFraction.getKey());
      }
    }
    return ret;
  }
}
