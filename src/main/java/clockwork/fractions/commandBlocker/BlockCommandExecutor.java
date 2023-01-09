package clockwork.fractions.commandBlocker;

import clockwork.fractions.config.CommandBlocker;
import tkachgeek.commands.command.arguments.executor.Executor;
import tkachgeek.tkachutils.messages.MessageReturn;

public class BlockCommandExecutor extends Executor {
  @Override
  public void executeForPlayer() throws MessageReturn {
    String fraction_name = arg(0).toString();
    String rank_name = arg(1).toString();
    String command = arg(2).toString();
    
    CommandBlocker.getInstance().blockCMD(fraction_name, rank_name, command);
  }
}
