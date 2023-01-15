package ru.cwcode.fractions.criminal;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.cwcode.fractions.Fractions;
import ru.cwcode.fractions.config.Messages;
import tkachgeek.config.base.Reloadable;
import tkachgeek.config.yaml.YmlConfig;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class CriminalStorage extends YmlConfig implements Reloadable {
  private static CriminalStorage instance;
  public int shockTime = 5;
  public int prison_time = 180;
  public int prison_fine = 10;
  private HashMap<String, Prison> prisons = new HashMap<>();
  private HashMap<UUID, Prisoner> prisoners = new HashMap<>();
  private HashMap<UUID, Integer> wanted = new HashMap<>();
  private ItemStack shocker;
  
  public CriminalStorage() {
  }
  
  public static CriminalStorage getInstance() {
    if (instance == null) load();
    return instance;
  }
  
  public static void load() {
    instance = Fractions.yml.load("criminal", CriminalStorage.class);
  }
  
  public boolean hasPrison(String prisonName) {
    return prisons.containsKey(prisonName);
  }
  
  public void addPrison(String prisonName, Location prisonLocation) {
    prisons.put(prisonName, new Prison(prisonLocation));
  }
  
  public void removePrison(String prisonName) {
    prisons.remove(prisonName);
  }
  
  public void putPlayer(Player player, String prisonName, int seconds) {
    Messages.getInstance().you_has_been_arrested.send(player);
    prisoners.put(player.getUniqueId(), new Prisoner(player, LocalDateTime.now().getSecond() + seconds));
    player.teleport(this.getPrison(prisonName).getLocation());
  }
  
  public void demobilizePlayer(UUID player) {
    Messages.getInstance().you_has_been_demobilized.send(player);
    prisoners.remove(player);
  }
  
  public void demobilizePlayer(Player player) {
    this.demobilizePlayer(player);
  }
  
  public void demobilizePlayer(Prisoner prisoner) {
    this.demobilizePlayer(prisoner.getUUID());
  }
  
  public Prison getPrison(String prisonName) {
    return prisons.getOrDefault(prisonName, null);
  }
  
  public Prisoner getPrisoner(UUID uuid) {
    return prisoners.getOrDefault(uuid, null);
  }
  
  public Prisoner getPrisoner(Player player) {
    return this.getPrisoner(player.getUniqueId());
  }
  
  public Prisoner getPrisoner(String name) {
    for (UUID uuid : prisoners.keySet()) {
      Prisoner prisoner = this.getPrisoner(uuid);
      if (prisoner.getName().equalsIgnoreCase(name)) {
        return prisoner;
      }
    }
    
    return null;
  }
  
  public boolean isPrisoner(UUID uuid) {
    return prisoners.containsKey(uuid);
  }
  
  public boolean isPrisoner(Player player) {
    return this.isPrisoner(player.getUniqueId());
  }
  
  public boolean isPrisoner(String name) {
    return this.getPrisoner(name) != null;
  }
  
  public List<String> getPrisonNames() {
    return new ArrayList<>(prisons.keySet());
  }
  
  public List<String> getPrisonersNames() {
    List<String> prisoners = new ArrayList<>();
    for (UUID uuid : this.prisoners.keySet()) {
      prisoners.add(this.prisoners.get(uuid).getName());
    }
    
    return prisoners;
  }
  
  public void setShocker(ItemStack item) {
    if (item == null || item.getType().isAir()) {
      return;
    }
    
    this.shocker = item.clone();
  }
  
  public boolean isShocker(ItemStack item) {
    if (this.shocker == null) {
      return false;
    }
    
    if (item == null) {
      return false;
    }
    
    if (this.shocker.getType() == item.getType()) {
      if ((this.shocker.hasItemMeta() == item.hasItemMeta())) {
        if (item.hasItemMeta()) {
          return Bukkit.getItemFactory().equals(this.shocker.getItemMeta(), item.getItemMeta());
        } else {
          return true;
        }
      }
    }
    
    return false;
  }
  
  public boolean isWanted(Player player) {
    return wanted.containsKey(player.getUniqueId());
  }
  
  public int getWantedLevel(Player player) {
    return wanted.getOrDefault(player.getUniqueId(), 0);
  }
  
  public void upWantedLevel(Player player) {
    int level = this.getWantedLevel(player);
    wanted.put(player.getUniqueId(), level);
  }
  
  @Override
  public void reload() {
    load();
  }
}
