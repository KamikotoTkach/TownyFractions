package clockwork.fractions.commands.arguments;

import clockwork.fractions.storage.FractionPlayer;
import clockwork.fractions.storage.config.PlayerStorage;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import tkachgeek.commands.command.Argument;

import java.util.Collections;
import java.util.List;

public class PlayersAtFraction extends Argument {
  @Override
  public boolean valid(String s) {
    return true;
  }
  
  @SuppressWarnings("OptionalIsPresent")
  @Override
  public List<String> completions(CommandSender commandSender) {
    var fp = FractionPlayer.get(commandSender);
    if(fp.isEmpty()) return Collections.emptyList();
    return PlayerStorage.getPlayerWithFraction(fp.get().getFraction()).stream().map(OfflinePlayer::getName).toList();
  }
  
  @Override
  public String argumentName() {
    return "член фракции";
  }
}
