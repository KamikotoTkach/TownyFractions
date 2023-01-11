package ru.cwcode.fractions.config;

import ru.cwcode.fractions.Fractions;
import ru.cwcode.fractions.commandBlocker.BlockedCommand;
import tkachgeek.config.yaml.YmlConfig;
import tkachgeek.tkachutils.messages.MessageReturn;

import java.util.ArrayList;
import java.util.List;

public class CommandBlockerStorage extends YmlConfig {
  static CommandBlockerStorage instance;
  public final List<BlockedCommand> blocked_commands = new ArrayList<>();
  
  public CommandBlockerStorage() {
  }
  
  public static CommandBlockerStorage getInstance() {
    if (instance == null) load();
    return instance;
  }
  
  public static void load() {
    instance = Fractions.yml.load("CommandBlocker", CommandBlockerStorage.class);
  }
  
  public void blockCMD(String fraction_name, String rank_name, String command) throws MessageReturn {
    for (BlockedCommand blocked_command : blocked_commands) {
      if (blocked_command.getFractionName().equals(fraction_name) && blocked_command.getRankName().equals(rank_name)) {
        blocked_command.blockCMD(command); //throwback
      }
    }
    
    BlockedCommand blocked_command = new BlockedCommand(fraction_name, rank_name);
    blocked_commands.add(blocked_command);
    blocked_command.blockCMD(command);
  }
}
