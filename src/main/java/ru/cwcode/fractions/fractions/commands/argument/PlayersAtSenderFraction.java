package ru.cwcode.fractions.fractions.commands.argument;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import ru.cwcode.fractions.config.PlayerStorage;
import ru.cwcode.fractions.fractions.FractionPlayer;
import tkachgeek.commands.command.Argument;

import java.util.Collections;
import java.util.List;

public class PlayersAtSenderFraction extends Argument {
  @Override
  public boolean valid(String s) {
    return true;
  }
  
  @SuppressWarnings("OptionalIsPresent")
  @Override
  public List<String> completions(CommandSender commandSender) {
    var fp = FractionPlayer.get(commandSender);
    if (fp.isEmpty()) return Collections.emptyList();
    return PlayerStorage.getAllPlayersWithFraction(fp.get().getFraction()).stream().map(OfflinePlayer::getName).toList();
  }
  
  @Override
  public String argumentName() {
    return "член фракции";
  }
}
