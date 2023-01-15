package ru.cwcode.fractions.prison;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import ru.cwcode.fractions.Fractions;
import ru.cwcode.fractions.config.Messages;
import ru.cwcode.fractions.utils.Validate;
import tkachgeek.banks.Banks;
import tkachgeek.tkachutils.messages.MessageReturn;

import java.util.HashMap;
import java.util.UUID;

public class CriminalAPI {
  public static final HashMap<UUID, Integer> shocked = new HashMap<>();
  public static final String federal = "Федеральная";
  
  static {
    Bukkit.getScheduler().runTaskTimerAsynchronously(Fractions.plugin, () -> {
      for (UUID uuid : shocked.keySet()) {
        int seconds = shocked.get(uuid);
        seconds--;
        
        if (seconds <= 0) {
          shocked.remove(uuid);
        } else {
          shocked.put(uuid, seconds);
        }
      }
    }, 0, 20L);
  }
  
  public static void createPrison(String prison_name, Location prison_location) throws MessageReturn {
    Validate.notExistPrison(prison_name);
    
    CriminalStorage.getInstance().addPrison(prison_name, prison_location);
    Messages.getInstance().prison_created_successfully.throwback();
  }
  
  public static void deletePrison(String prison_name) throws MessageReturn {
    Validate.existPrison(prison_name);
    
    CriminalStorage.getInstance().removePrison(prison_name);
    Messages.getInstance().prison_deleted_successfully.throwback();
  }
  
  public static boolean hasPrison(String prison_name) {
    return CriminalStorage.getInstance().hasPrison(prison_name);
  }
  
  public static boolean isPrisoner(Player player) {
    return CriminalStorage.getInstance().isPrisoner(player);
  }
  
  public static boolean isPrisoner(String player) {
    return CriminalStorage.getInstance().isPrisoner(player);
  }
  
  public static void putPlayer(Player policeman, Player player, String prison_name) throws MessageReturn {
    Validate.isWanted(player);
    Validate.existPrison(prison_name);
    Validate.isNotPrisoner(player);
    
    int wanted_level = CriminalStorage.getInstance().getWantedLevel(player);
    int seconds = wanted_level * CriminalStorage.getInstance().prison_time;
    int fine = wanted_level * CriminalStorage.getInstance().prison_fine;
    
    Banks.getEconomy().withdrawPlayer(player, fine);
    Banks.getEconomy().depositPlayer(policeman, fine);
    
    CriminalStorage.getInstance().putPlayer(player, prison_name, seconds);
  }
  
  public static void demobilizePlayer(String name) throws MessageReturn {
    Validate.isPrisoner(name);
    Prisoner prisoner = CriminalStorage.getInstance().getPrisoner(name);
    if (prisoner == null) {
      Messages.getInstance().isnt_prisoner.throwback();
    }
    
    CriminalStorage.getInstance().demobilizePlayer(prisoner);
  }
  
  public static void shockPlayer(Player player) {
    shocked.put(player.getUniqueId(), CriminalStorage.getInstance().shockTime);
  }
}
