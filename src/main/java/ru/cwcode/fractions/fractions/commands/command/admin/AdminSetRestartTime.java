package ru.cwcode.fractions.fractions.commands.command.admin;

import ru.cwcode.fractions.board.Board;
import ru.cwcode.fractions.config.Messages;
import tkachgeek.commands.command.arguments.executor.Executor;
import tkachgeek.tkachutils.messages.MessageReturn;

public class AdminSetRestartTime extends Executor {
  @Override
  public void executeForPlayer() throws MessageReturn {
    String[] split = argS(1).split(":");
    int time = Integer.parseInt(split[0]) * 60 + Integer.parseInt(split[1]);
    
    Board.getInstance().setRestartTime(time);
    Messages.getInstance().defaults.done.throwback();
  }
}
