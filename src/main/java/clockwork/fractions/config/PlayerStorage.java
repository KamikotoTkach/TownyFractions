package clockwork.fractions.config;

import clockwork.fractions.Fractions;
import clockwork.fractions.fractions.FractionInstance;
import clockwork.fractions.fractions.storage.FractionPlayer;
import com.google.common.collect.HashBiMap;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tkachgeek.config.yaml.YmlConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    if (!getInstance().players.containsKey(player)) {
      getInstance().players.put(player, new FractionPlayer());
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
