package ru.cwcode.fractions.fractions.commands.command;

import ru.cwcode.fractions.config.Messages;
import ru.cwcode.fractions.criminal.CriminalAPI;
import tkachgeek.commands.command.arguments.executor.Executor;
import tkachgeek.tkachutils.messages.MessageReturn;

public class RaidCommand extends Executor {
  @Override
  public void executeForPlayer() throws MessageReturn {
    String territory_name = argS(0);
    CriminalAPI.startRaid(player(), territory_name);
  }
  
  @Override
  public void executeForNonPlayer() throws MessageReturn {
    Messages.getInstance().defaults.for_player_only.throwback();
  }
}
