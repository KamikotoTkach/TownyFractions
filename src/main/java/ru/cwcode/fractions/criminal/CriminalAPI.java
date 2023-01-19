package ru.cwcode.fractions.criminal;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import ru.cwcode.fractions.Fractions;
import ru.cwcode.fractions.config.Messages;
import ru.cwcode.fractions.fractions.FractionInstance;
import ru.cwcode.fractions.fractions.FractionPlayer;
import ru.cwcode.fractions.utils.Validate;
import tkachgeek.banks.Banks;
import tkachgeek.config.minilocale.Placeholder;
import tkachgeek.tkachutils.messages.MessageReturn;
import tkachgeek.townyterritory.TerrAPI;
import tkachgeek.townyterritory.territory.Territory;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class CriminalAPI {
  public static final HashMap<UUID, Integer> shocked = new HashMap<>();
  public static Raid raid = null;
  
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
  
      for (Prisoner prisoner : CriminalStorage.getInstance().getPrisoners()) {
        if (prisoner.timeExpired()) {
          CriminalStorage.getInstance().demobilizePlayer(prisoner);
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
  
  public static void stealMoney(Player player, Player killer) {
    double money = Banks.getEconomy().getBalance(player) * CriminalStorage.getInstance().kill_steal / 100;
    Banks.getEconomy().depositPlayer(killer, money);
    Banks.getEconomy().withdrawPlayer(player, money);
  }
  
  public static void arrestPlayer(Player policeman, Player player, String prison_name) throws MessageReturn {
    Validate.isWanted(player);
    Validate.existPrison(prison_name);
    Validate.isNotPrisoner(player);
    
    int wanted_level = CriminalStorage.getInstance().getWantedLevel(player);
    int seconds = wanted_level * CriminalStorage.getInstance().prison_time;
    int fine = wanted_level * CriminalStorage.getInstance().prison_fine;
    
    Banks.getEconomy().depositPlayer(policeman, fine);
    Banks.getEconomy().withdrawPlayer(player, fine);
    
    CriminalStorage.getInstance().arrestPlayer(player, prison_name, seconds);
    Messages.getInstance().$player_arrested_successfully.send(policeman, Placeholder.add("player", player.getName()));
  }
  
  public static void demobilizePlayer(String name) throws MessageReturn {
    Validate.isPrisoner(name);
    Prisoner prisoner = CriminalStorage.getInstance().getPrisoner(name);
    if (prisoner == null) {
      Messages.getInstance().isnt_prisoner.throwback();
    }
  
    CriminalStorage.getInstance().demobilizePlayer(prisoner);
    Messages.getInstance().$player_demobilized_successfully.throwback(Placeholder.add("player", name));
  }
  
  public static void shockPlayer(Player player) {
    shocked.put(player.getUniqueId(), CriminalStorage.getInstance().shockTime);
  }
  
  public static void startRaid(Player player, String territoryName) throws MessageReturn {
    if (raid != null) {
      Messages.getInstance().raid_started.throwback();
    }
    
    FractionPlayer fractionPlayer = FractionPlayer.get(player);
    
    FractionInstance aggressor_fraction = null;
    Territory aggressor_territory = TerrAPI.getTerritoryAt(player).orElseGet(null);
    
    if (fractionPlayer.hasFraction() && fractionPlayer.isMilitary()) {
      Validate.canRaid(fractionPlayer);
      aggressor_fraction = fractionPlayer.getFraction();
    }
    
    Optional<Territory> victim_territory = TerrAPI.getTerritoryBy(territoryName);
    Validate.isPresent(victim_territory, territoryName);
    
    raid = new Raid(victim_territory.get(), aggressor_fraction, aggressor_territory);
  }
}
