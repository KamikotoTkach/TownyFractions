package ru.cwcode.fractions.commandBlocker;

import ru.cwcode.fractions.config.Messages;
import ru.cwcode.fractions.fractions.FractionPlayer;
import ru.cwcode.fractions.fractions.Rank;
import tkachgeek.tkachutils.messages.MessageReturn;

import java.util.ArrayList;
import java.util.List;

public class BlockedCommand {
  
  private String fraction_name;
  private String rank_name;
  private List<String> commands;
  
  public BlockedCommand() {
  }
  
  public BlockedCommand(String fraction_name, String rank_name, List<String> commands) {
    this.fraction_name = fraction_name;
    this.rank_name = rank_name;
    this.commands = commands;
  }
  
  public BlockedCommand(String fraction_name, String rank_name) {
    this(fraction_name, rank_name, new ArrayList<>());
  }
  
  public String getFractionName() {
    return this.fraction_name;
  }
  
  public String getRankName() {
    return this.rank_name;
  }
  
  public boolean isBlockedCommand(String message) {
    for (String command : this.commands) {
      if (message.startsWith(command) || message.startsWith("/" + command)) {
        return true;
      }
      
      String[] cmd = message.split(":", 2);
      if (cmd.length > 1) {
        if (cmd[1].startsWith(command) || command.startsWith("/") && cmd[1].startsWith(command.substring(1))) {
          return true;
        }
      }
    }
    
    return false;
  }
  
  public boolean canRun(FractionPlayer fraction_player, String message) {
    if (!this.isBlockedCommand(message)) {
      return true;
    }
    
    if (fraction_player.hasFraction()
       && fraction_player.getFraction().name().equals(fraction_name)
       && fraction_player.getFraction().isFractionRank(rank_name)) {
      Rank rank = fraction_player.getFraction().getRank(rank_name).get();
      return fraction_player.isGreaterOrEquals(rank);
    }
    
    return false;
  }
  
  public void blockCMD(String command) throws MessageReturn {
    if (this.isBlockedCommand(command)) {
      Messages.getInstance().command_already_blocked.throwback();
    } else {
      commands.add(command);
      Messages.getInstance().command_blocked_successfully.throwback();
    }
  }
}
