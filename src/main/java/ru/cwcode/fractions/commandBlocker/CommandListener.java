package ru.cwcode.fractions.commandBlocker;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import ru.cwcode.fractions.config.CommandBlockerStorage;
import ru.cwcode.fractions.config.Messages;
import ru.cwcode.fractions.config.PlayerStorage;
import ru.cwcode.fractions.fractions.storage.FractionPlayer;

public class CommandListener implements Listener {
  @EventHandler
  void onCommand(PlayerCommandPreprocessEvent event) {
    String message = event.getMessage();
    for (BlockedCommand command : CommandBlockerStorage.getInstance().blocked_commands) {
      if (command.isBlockedCommand(message)) {
        FractionPlayer fraction_player = PlayerStorage.get(event.getPlayer());
        if (!command.canRun(fraction_player, message)) {
          event.setCancelled(true);
          Messages.getInstance().defaults.you_dont_have_permissions.send(event.getPlayer());
        }
      
        return;
      }
    }
  }
}