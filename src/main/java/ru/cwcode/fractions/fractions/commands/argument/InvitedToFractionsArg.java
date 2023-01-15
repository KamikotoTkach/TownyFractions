package ru.cwcode.fractions.fractions.commands.argument;

import org.bukkit.command.CommandSender;
import ru.cwcode.fractions.fractions.FractionPlayer;
import ru.cwcode.fractions.fractions.FractionsAPI;
import tkachgeek.commands.command.Argument;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class InvitedToFractionsArg extends Argument {
  @Override
  public boolean valid(String s) {
    return FractionsAPI.hasFraction(s);
  }
  
  @Override
  public List<String> completions(CommandSender commandSender) {
    Optional<FractionPlayer> fractionPlayer = FractionPlayer.get(commandSender);
    if (fractionPlayer.isPresent()) return fractionPlayer.get().getInvitedTo();
    return Collections.emptyList();
  }
  
  @Override
  public String argumentName() {
    return "название фракции";
  }
}
