package clockwork.fractions.fractions.commands.argument;

import clockwork.fractions.fractions.storage.FractionPlayer;
import clockwork.fractions.fractions.storage.Rank;
import org.bukkit.command.CommandSender;
import tkachgeek.commands.command.Argument;

import java.util.Collections;
import java.util.List;

public class RanksAtFractions extends Argument {
  @Override
  public boolean valid(String s) {
    return true;
  }
  
  @SuppressWarnings({"OptionalIsPresent", "DataFlowIssue"})
  @Override
  public List<String> completions(CommandSender commandSender) {
    var fraction = FractionPlayer.get(commandSender);
    if(fraction.isEmpty()) return Collections.emptyList();
    return fraction.get().getFraction().ranks.stream().map(x->x.name()).toList();
  }
  
  @Override
  public String argumentName() {
    return "ранг";
  }
}
