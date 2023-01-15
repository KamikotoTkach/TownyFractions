package ru.cwcode.fractions.criminal.commands.argument;

import org.bukkit.command.CommandSender;
import ru.cwcode.fractions.criminal.CriminalStorage;
import tkachgeek.commands.command.Argument;

import java.util.List;

public class PrisonersArg extends Argument {
  @Override
  public boolean valid(String raw) {
    return CriminalStorage.getInstance().isPrisoner(raw);
  }
  
  @Override
  public List<String> completions(CommandSender commandSender) {
    return CriminalStorage.getInstance().getPrisonersNames();
  }
  
  @Override
  public String argumentName() {
    return "заключённый";
  }
}
