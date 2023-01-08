package clockwork.fractions.commands.command;

import clockwork.fractions.FractionsAPI;
import org.bukkit.Bukkit;
import tkachgeek.commands.command.arguments.executor.Executor;
import tkachgeek.tkachutils.messages.MessageReturn;

public class KickFromFraction extends Executor {
  @Override
  public void executeForPlayer() throws MessageReturn {
    FractionsAPI.kickPlayer(player(), Bukkit.getPlayer(argS(1)));
  }
}
