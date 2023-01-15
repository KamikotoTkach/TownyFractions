package ru.cwcode.fractions.criminal.commands.argument;

import org.bukkit.command.CommandSender;
import ru.cwcode.fractions.criminal.CriminalStorage;
import tkachgeek.commands.command.Argument;

import java.util.List;

public class PrisonsArg extends Argument {
  @Override
  public boolean valid(String raw) {
    return CriminalStorage.getInstance().hasPrison(raw);
  }
  
  @Override
  public List<String> completions(CommandSender commandSender) {
    return CriminalStorage.getInstance().getPrisonNames();
  }
  
  @Override
  public String argumentName() {
    return "тюрьма";
  }
}
