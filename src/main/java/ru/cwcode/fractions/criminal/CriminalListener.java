package ru.cwcode.fractions.criminal;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import ru.cwcode.fractions.fractions.FractionPlayer;

public class CriminalListener implements Listener {
  @EventHandler
  void onDamage(EntityDamageByEntityEvent e) { //todo: протестить шокер из блядского датапка (╯ ° □ °) ╯ (┻━┻)
    if (e.getEntity().getType().equals(EntityType.PLAYER)) {
      if (e.getDamager().getType().equals(EntityType.PLAYER)) {
        if (CriminalStorage.getInstance().isShocker(((Player) e.getDamager()).getInventory().getItemInMainHand())) {
          e.setCancelled(true);
          CriminalAPI.shockPlayer((Player) e.getEntity());
        }
      }
    }
  }
  
  @EventHandler
  void onKill(PlayerDeathEvent e) {
    Player killer = e.getEntity().getKiller();
    if (killer != null) {
      FractionPlayer fractionPlayer = FractionPlayer.get(killer);
      if (fractionPlayer.isPoliceman() || fractionPlayer.isMilitary()) {
        return;
      }
      
      CriminalStorage.getInstance().upWantedLevel(killer);
    }
  }
  
  @EventHandler
  void onTeleport(PlayerTeleportEvent e) {
    if (CriminalAPI.shocked.containsKey(e.getPlayer().getUniqueId())) {
      e.setCancelled(true);
    }
    
    if (CriminalAPI.isPrisoner(e.getPlayer())) {
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
