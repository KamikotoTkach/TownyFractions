package clockwork.fractions.fractions.commands.argument;

import clockwork.fractions.fractions.FractionInstance;
import clockwork.fractions.fractions.FractionsAPI;
import org.bukkit.command.CommandSender;
import tkachgeek.commands.command.Argument;

import java.util.List;

public class FractionsArg extends Argument {
  @Override
  public boolean valid(String s) {
    return FractionsAPI.getFractions().stream().anyMatch(x -> x.getName().equals(s));
  }
  
  @Override
  public List<String> completions(CommandSender commandSender) {
    return FractionsAPI.getFractions().stream().map(FractionInstance::getName).toList();
  }
  
  @Override
  public String argumentName() {
    return "название фракции";
  }
}
