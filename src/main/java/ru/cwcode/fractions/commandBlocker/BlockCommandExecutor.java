package ru.cwcode.fractions.commandBlocker;

import ru.cwcode.fractions.config.CommandBlockerStorage;
import tkachgeek.commands.command.arguments.executor.Executor;
import tkachgeek.tkachutils.messages.MessageReturn;

public class BlockCommandExecutor extends Executor {
  @Override
  public void executeForPlayer() throws MessageReturn {
    String fraction_name = argS(0);
    String rank_name = argWithSpaces(1);
    String command = argS(2);
  
    CommandBlockerStorage.getInstance().blockCMD(fraction_name, rank_name, command);
  }
}
