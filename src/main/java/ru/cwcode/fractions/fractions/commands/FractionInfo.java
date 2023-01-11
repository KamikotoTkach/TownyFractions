package ru.cwcode.fractions.fractions.commands;

import ru.cwcode.fractions.config.Messages;
import ru.cwcode.fractions.config.PlayerStorage;
import ru.cwcode.fractions.fractions.storage.Rank;
import ru.cwcode.fractions.utils.Validate;
import tkachgeek.commands.command.arguments.executor.Executor;
import tkachgeek.config.minilocale.Placeholder;
import tkachgeek.tkachutils.messages.MessageReturn;

public class FractionInfo extends Executor {
  @Override
  public void executeForPlayer() throws MessageReturn {
    var fp = PlayerStorage.get(player());
    Validate.youHasFraction(fp);
    
    var fraction = fp.getFraction();
    var totalPlayers = fraction.getTotalPlayers();
    var onlinePlayers = fraction.getOnlinePlayers();
    
    Messages.getInstance().fractionInfoHeader$name$totalPlayers$online.send(sender(), Placeholder.add("name", fraction.getPrefix() + fraction.getName())
                                                                                                 .add("totalPlayers", totalPlayers)
                                                                                                 .add("online", onlinePlayers));
    
    Messages.getInstance().fractionInfoRanksHeader.send(sender());
    
    for (Rank rank : fraction.getRanks()) {
      Messages.getInstance().fractionInfoRank$name$salary.send(sender(), Placeholder.add("name", rank.name())
                                                                                    .add("salary", rank.salary()));
    }
  }
}
