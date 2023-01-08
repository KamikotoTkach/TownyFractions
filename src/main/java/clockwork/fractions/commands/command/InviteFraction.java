package clockwork.fractions.commands.command;

import clockwork.fractions.FractionsAPI;
import org.bukkit.Bukkit;
import tkachgeek.commands.command.arguments.executor.Executor;
import tkachgeek.tkachutils.messages.MessageReturn;

public class InviteFraction extends Executor {
  @Override
  public void executeForPlayer() throws MessageReturn {
    FractionsAPI.invitePlayer(player(), Bukkit.getPlayer(argS(1)));
  }
}
