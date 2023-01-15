package ru.cwcode.fractions.criminal.commands.command;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import ru.cwcode.fractions.config.Messages;
import ru.cwcode.fractions.criminal.CriminalAPI;
import tkachgeek.commands.command.arguments.executor.Executor;
import tkachgeek.tkachutils.messages.MessageReturn;

public class PrisonPut extends Executor {
  @Override
  public void executeForPlayer() throws MessageReturn {
    String prison_name = CriminalAPI.federal;
    if (isPresent(0)) {
      prison_name = argS(0);
    }
    
    Entity player = player().getTargetEntity(5);
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
