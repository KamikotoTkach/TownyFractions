package ru.cwcode.fractions.fractions.commands.command.admin;

import ru.cwcode.fractions.criminal.CriminalStorage;
import ru.cwcode.fractions.fractions.FractionPlayer;
import tkachgeek.commands.command.arguments.executor.Executor;
import tkachgeek.tkachutils.messages.MessageReturn;

public class AdminSetWanted extends Executor {
  @Override
  public void executeForPlayer() throws MessageReturn {
    CriminalStorage.getInstance().setWantedLevel(FractionPlayer.get(argS(1)).get().getPlayer().getPlayer(), argI(2));
  }
}
