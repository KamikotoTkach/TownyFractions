package ru.cwcode.fractions.fractions.commands.command;

import ru.cwcode.fractions.fractions.FractionPlayer;
import tkachgeek.commands.command.arguments.executor.Executor;
import tkachgeek.tkachutils.messages.MessageReturn;

public class JoinFraction extends Executor {
  @Override
  public void executeForPlayer() throws MessageReturn {
    FractionPlayer.get(player()).join(argS(1));
  }
}
