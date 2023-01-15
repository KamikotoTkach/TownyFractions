package ru.cwcode.fractions.criminal.commands.command;

import org.bukkit.Location;
import ru.cwcode.fractions.config.Messages;
import ru.cwcode.fractions.criminal.CriminalAPI;
import tkachgeek.commands.command.arguments.executor.Executor;
import tkachgeek.tkachutils.messages.MessageReturn;
import tkachgeek.townyterritory.TerrAPI;

public class CreatePrison extends Executor {
  @Override
  public void executeForPlayer() throws MessageReturn {
    String prison_name = CriminalAPI.federal;
    if (isPresent(1)) {
      prison_name = argS(1);
    }
    
    if (prison_name.equalsIgnoreCase(CriminalAPI.federal)) {
      if (!sender().hasPermission("*")) {
        Messages.getInstance().defaults.you_dont_have_permissions.throwback();
      }
    }
    
    if (!TerrAPI.isPrisonTerritory(player().getLocation())) {
      Messages.getInstance().you_need_to_register_this_territory_as_jail.throwback();
    }
    
    Location prison_location = player().getLocation();
    
    CriminalAPI.createPrison(prison_name, prison_location);
  }
  
  @Override
  public void executeForNonPlayer() throws MessageReturn {
    Messages.getInstance().defaults.for_player_only.throwback();
  }
}
