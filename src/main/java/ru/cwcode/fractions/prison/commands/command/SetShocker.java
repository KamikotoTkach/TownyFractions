package ru.cwcode.fractions.prison.commands.command;

import org.bukkit.inventory.ItemStack;
import ru.cwcode.fractions.config.Messages;
import ru.cwcode.fractions.prison.CriminalStorage;
import tkachgeek.commands.command.arguments.executor.Executor;
import tkachgeek.tkachutils.messages.MessageReturn;

public class SetShocker extends Executor {
  @Override
  public void executeForPlayer() throws MessageReturn {
    ItemStack item = player().getInventory().getItemInMainHand();
    if (item.getType().isAir()) {
      Messages.getInstance().defaults.no_item_in_main_hand.throwback();
    }
    
    CriminalStorage.getInstance().setShocker(item);
  }
  
  @Override
  public void executeForNonPlayer() throws MessageReturn {
    Messages.getInstance().defaults.for_player_only.throwback();
  }
}
