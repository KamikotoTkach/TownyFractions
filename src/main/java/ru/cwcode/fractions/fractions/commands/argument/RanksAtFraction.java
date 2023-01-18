package ru.cwcode.fractions.fractions.commands.argument;

import org.bukkit.command.CommandSender;
import ru.cwcode.fractions.fractions.FractionsAPI;
import ru.cwcode.fractions.fractions.Rank;
import tkachgeek.commands.command.arguments.BindedArgument;
import tkachgeek.tkachutils.text.SpacesHider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RanksAtFraction extends BindedArgument {
  public RanksAtFraction(int i) {
    bind(i);
  }
  
  @Override
  public boolean valid(String rank, String fraction) {
    var fractionInst = FractionsAPI.getFraction(fraction);
    return fractionInst.isPresent() && fractionInst.get().hasRank(SpacesHider.restore(rank));
  }
  
  @Override
  public List<String> completions(CommandSender commandSender, String fraction) {
    var fractionInst = FractionsAPI.getFraction(fraction);
    if (fractionInst.isEmpty()) return Collections.emptyList();
    
    List<String> list = new ArrayList<>();
    for (Rank rank : fractionInst.get().getRanks()) {
      list.add(SpacesHider.hide(rank.name()));
    }
    return list;
  }
  
  @Override
  public String argumentName() {
    return "ранг";
  }
}
