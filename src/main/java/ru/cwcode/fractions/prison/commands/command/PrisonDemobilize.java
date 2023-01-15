package ru.cwcode.fractions.prison.commands.command;

import ru.cwcode.fractions.prison.CriminalAPI;
import tkachgeek.commands.command.arguments.executor.Executor;
import tkachgeek.tkachutils.messages.MessageReturn;

public class PrisonDemobilize extends Executor {
  @Override
  public void executeForPlayer() throws MessageReturn {
    String name = argS(1);
    CriminalAPI.demobilizePlayer(name);
  }
}
