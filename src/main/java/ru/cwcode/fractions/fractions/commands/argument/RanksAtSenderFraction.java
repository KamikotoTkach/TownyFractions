package ru.cwcode.fractions.fractions.commands.argument;

import org.bukkit.command.CommandSender;
import ru.cwcode.fractions.fractions.FractionPlayer;
import tkachgeek.commands.command.Argument;
import tkachgeek.tkachutils.text.SpacesHider;

import java.util.Collections;
import java.util.List;

public class RanksAtSenderFraction extends Argument {
  @Override
  public boolean valid(String s) {
    return true;
  }
  
  @SuppressWarnings({"OptionalIsPresent", "DataFlowIssue"})
  @Override
  public List<String> completions(CommandSender commandSender) {
    var fraction = FractionPlayer.get(commandSender);
    if (fraction.isEmpty()) return Collections.emptyList();
    return fraction.get().getFraction().getRanks().stream().map(x -> SpacesHider.hide(x.name())).toList();
  }
  
  @Override
  public String argumentName() {
    return "ранг";
  }
}
