package ru.cwcode.fractions.criminal.commands.command;

import ru.cwcode.fractions.config.Messages;
import ru.cwcode.fractions.criminal.CriminalAPI;
import tkachgeek.commands.command.arguments.executor.Executor;
import tkachgeek.tkachutils.messages.MessageReturn;

public class DeletePrison extends Executor {
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
    
    CriminalAPI.deletePrison(prison_name);
  }
}
