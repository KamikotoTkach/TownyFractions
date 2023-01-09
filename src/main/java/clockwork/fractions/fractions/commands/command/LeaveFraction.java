package clockwork.fractions.fractions.commands.command;

import clockwork.fractions.fractions.storage.FractionPlayer;
import tkachgeek.commands.command.arguments.executor.Executor;
import tkachgeek.tkachutils.messages.MessageReturn;

public class LeaveFraction extends Executor {
  @Override
  public void executeForPlayer() throws MessageReturn {
    FractionPlayer.get(player()).leaveFraction();
  }
}
