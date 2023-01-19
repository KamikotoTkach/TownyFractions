package ru.cwcode.fractions.fractions.commands.command;

import ru.cwcode.fractions.config.Messages;
import ru.cwcode.fractions.config.PlayerStorage;
import ru.cwcode.fractions.utils.Validate;
import tkachgeek.commands.command.arguments.executor.Executor;
import tkachgeek.config.minilocale.Placeholder;
import tkachgeek.tkachutils.messages.MessageReturn;

import java.util.Map;

public class StatsCmd extends Executor {
  @Override
  public void executeForPlayer() throws MessageReturn {
    var player = PlayerStorage.get(player());
    Validate.youHasFraction(player);
    
    Messages.getInstance().stats_header_$rank$salary.send(player(), Placeholder.add("rank", player.getFormattedRank())
                                                                               .add("salary", player.getRank().salary()));
    
    for (Map.Entry<String, Integer> statEntry : player.getStats().getValues().entrySet()) {
      
      String prefix = player.getFraction().name() + ":";
      if (statEntry.getKey().startsWith(prefix)) {
        Messages.getInstance().stats_statEntry$name$value.send(player, Placeholder.add("name", statEntry.getKey().split(":")[1])
                                                                                  .add("value", statEntry.getValue()));
      }
    }
  }
}
