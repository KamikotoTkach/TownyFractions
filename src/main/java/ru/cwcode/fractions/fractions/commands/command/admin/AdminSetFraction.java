package ru.cwcode.fractions.fractions.commands.command.admin;

import ru.cwcode.fractions.config.Messages;
import ru.cwcode.fractions.config.PlayerStorage;
import ru.cwcode.fractions.fractions.FractionsAPI;
import ru.cwcode.fractions.fractions.Rank;
import ru.cwcode.fractions.utils.Validate;
import tkachgeek.commands.command.arguments.executor.Executor;
import tkachgeek.tkachutils.messages.MessageReturn;

import java.util.Optional;

public class AdminSetFraction extends Executor {
  @Override
  public void executeForPlayer() throws MessageReturn {
    var player = PlayerStorage.get(argS(1));
    Validate.isPresent(player, "Игрок");
    
    var fraction = FractionsAPI.getFraction(argS(2));
    Validate.isPresent(fraction, "Фракция");
    
    Optional<Rank> rank = Optional.empty();
    if (isPresent(3)) {
      rank = fraction.get().getRank(argWithSpaces(3));
      Validate.isPresent(rank, "Ранг");
    }
    
    player.get().setFraction(fraction.get());
    
    rank.ifPresent(value -> player.get().setRank(value));
    
    Messages.getInstance().defaults.done.throwback();
  }
}
