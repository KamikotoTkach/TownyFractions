package ru.cwcode.fractions.fractions.commands.command;

import ru.cwcode.fractions.config.PlayerStorage;
import ru.cwcode.fractions.fractions.FractionsAPI;
import ru.cwcode.fractions.fractions.storage.FractionPlayer;
import ru.cwcode.fractions.utils.Validate;
import tkachgeek.commands.command.arguments.executor.Executor;
import tkachgeek.tkachutils.messages.MessageReturn;

import java.util.Optional;

public class SetFractionRank extends Executor {
  @Override
  public void executeForPlayer() throws MessageReturn {
    Optional<FractionPlayer> fractionPlayer = PlayerStorage.get(argS(1));
    Validate.isPresent(fractionPlayer, "Игрок");
    FractionsAPI.setRank(player(), fractionPlayer.get(), argS(2));
  }
}
