package clockwork.fractions.board;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class BoardListener implements Listener {
  @EventHandler
  void onJoin(PlayerJoinEvent e) {
    Board.createBoard(e.getPlayer());
  }
}
