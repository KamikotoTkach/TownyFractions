package clockwork.fractions.commandBlocker;

import clockwork.fractions.config.CommandBlocker;
import clockwork.fractions.config.Messages;
import clockwork.fractions.config.PlayerStorage;
import clockwork.fractions.fractions.storage.FractionPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandListener implements Listener {
  @EventHandler
  void onCommand(PlayerCommandPreprocessEvent event) {
    String message = event.getMessage();
    for (BlockedCommand command : CommandBlocker.getInstance().blocked_commands) {
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