package ru.cwcode.fractions.criminal;

import com.palmergames.bukkit.towny.event.damage.TownyFriendlyFireTestEvent;
import com.palmergames.bukkit.towny.event.damage.TownyPlayerDamagePlayerEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import ru.cwcode.fractions.fractions.FractionPlayer;

public class CriminalListener implements Listener {
  @EventHandler(priority = EventPriority.HIGHEST)
  void onDamage(TownyPlayerDamagePlayerEvent e) { //todo: протестить шокер из блядского датапка (╯ ° □ °) ╯ (┻━┻)
    if (CriminalAPI.raid != null && CriminalAPI.raid.isMember(e.getVictimPlayer())) {
      e.setCancelled(true);
    }
    
    if (CriminalStorage.getInstance().isShocker(e.getAttackingPlayer().getInventory().getItemInMainHand())) {
      e.setCancelled(true);
      CriminalAPI.shockPlayer(e.getVictimPlayer());
    }
  }
  
  @EventHandler(priority = EventPriority.HIGHEST)
  void onDamage(TownyFriendlyFireTestEvent e) {
    if (CriminalAPI.raid != null && CriminalAPI.raid.isMember(e.getDefender())) {
      e.setPVP(true);
    }
    
    if (CriminalStorage.getInstance().isShocker(e.getAttacker().getInventory().getItemInMainHand())) {
      e.setPVP(true);
    }
  }
  
  @EventHandler
  void onKill(PlayerDeathEvent e) {
    Player killer = e.getEntity().getKiller();
    if (killer != null) {
      FractionPlayer fractionPlayer = FractionPlayer.get(killer);
      if (!fractionPlayer.isPoliceman() && !fractionPlayer.isMilitary()) {
        CriminalAPI.stealMoney(e.getEntity(), killer);
  
        CriminalStorage.getInstance().upWantedLevel(killer);
      }
  
      if (CriminalAPI.raid != null && CriminalAPI.raid.getState().equals(RaidState.STARTED)) {
        CriminalAPI.raid.progressRaid(e.getEntity());
      }
    }
  }
  
  @EventHandler
  void onTeleport(PlayerTeleportEvent e) {
    if (CriminalAPI.isPrisoner(e.getPlayer()) || CriminalAPI.shocked.containsKey(e.getPlayer().getUniqueId())) {
      if (TerrAPI.isPrisonTerritory(e.getTo())) return;
      e.setCancelled(true);
    }
  }
  
  @EventHandler
  void onCommand(PlayerCommandPreprocessEvent e) {
    if (CriminalAPI.shocked.containsKey(e.getPlayer().getUniqueId())) {
      e.setCancelled(true);
    }
    
    if (CriminalAPI.isPrisoner(e.getPlayer())) {
      e.setCancelled(true);
    }
  }
}
