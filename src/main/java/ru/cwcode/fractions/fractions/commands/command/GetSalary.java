package ru.cwcode.fractions.fractions.commands.command;

import ru.cwcode.fractions.config.PlayerStorage;
import tkachgeek.commands.command.arguments.executor.Executor;
import tkachgeek.tkachutils.messages.MessageReturn;

public class GetSalary extends Executor {
  @Override
  public void executeForPlayer() throws MessageReturn {
    PlayerStorage.get(player()).tryToGetSalary();
  }
}
