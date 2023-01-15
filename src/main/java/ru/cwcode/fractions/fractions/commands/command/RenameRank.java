package ru.cwcode.fractions.fractions.commands.command;

import ru.cwcode.fractions.config.Messages;
import ru.cwcode.fractions.fractions.FractionPlayer;
import ru.cwcode.fractions.utils.Validate;
import tkachgeek.commands.command.arguments.executor.Executor;
import tkachgeek.tkachutils.messages.MessageReturn;

public class RenameRank extends Executor {
  @Override
  public void executeForPlayer() throws MessageReturn {
    var fp = FractionPlayer.get(player());
    Validate.hasTopRank(fp);
    
    var fraction = fp.getFraction();
    
    var rank = fraction.getRank(argWithSpaces(1));
    Validate.isPresent(rank, "Ранг");
    
    rank.get().name(argWithSpaces(2));
    
    Messages.getInstance().defaults.done.throwback();
  }
}
