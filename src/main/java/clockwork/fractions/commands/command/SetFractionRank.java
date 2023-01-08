package clockwork.fractions.commands.command;

import clockwork.fractions.FractionsAPI;
import clockwork.fractions.storage.config.PlayerStorage;
import tkachgeek.commands.command.arguments.executor.Executor;
import tkachgeek.tkachutils.messages.MessageReturn;

public class SetFractionRank extends Executor {
  @Override
  public void executeForPlayer() throws MessageReturn {
    FractionsAPI.setRank(player(), PlayerStorage.get(argS(1)), argS(2));
  }
}
