package ru.cwcode.fractions.criminal.commands.command;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import ru.cwcode.fractions.config.Messages;
import ru.cwcode.fractions.criminal.CriminalAPI;
import tkachgeek.commands.command.arguments.executor.Executor;
import tkachgeek.tkachutils.messages.MessageReturn;

public class PrisonArrest extends Executor {
  @Override
  public void executeForPlayer() throws MessageReturn {
    String prison_name = CriminalStorage.getInstance().federal;
    if (isPresent(1)) {
      prison_name = argS(1);
    }
    
    //Entity player = player().getTargetEntity(5, true);
    var rtr = player().getWorld().rayTraceEntities(player().getEyeLocation().add(player().getLocation().getDirection()), player().getLocation().getDirection(), 5);
    var player = rtr.getHitEntity();
    
    if (player == null || !player.getType().equals(EntityType.PLAYER)
       || !CriminalAPI.shocked.containsKey(player.getUniqueId())) {
      Messages.getInstance().isnt_shocked.throwback();
    }
    
    CriminalAPI.putPlayer(player(), (Player) player, prison_name);
  }
  
  @Override
  public void executeForNonPlayer() throws MessageReturn {
    Messages.getInstance().defaults.for_player_only.throwback();
  }
}
