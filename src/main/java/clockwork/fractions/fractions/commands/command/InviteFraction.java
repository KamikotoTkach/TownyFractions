package clockwork.fractions.fractions.commands.command;

import clockwork.fractions.config.PlayerStorage;
import clockwork.fractions.fractions.FractionsAPI;
import clockwork.fractions.fractions.storage.FractionPlayer;
import clockwork.fractions.utils.Validate;
import tkachgeek.commands.command.arguments.executor.Executor;
import tkachgeek.tkachutils.messages.MessageReturn;

import java.util.Optional;

public class InviteFraction extends Executor {
  @Override
  public void executeForPlayer() throws MessageReturn {
    Optional<FractionPlayer> fractionPlayer = PlayerStorage.get(argS(1));
    
    Validate.isPresent(fractionPlayer, "Игрок");
    
    FractionsAPI.invitePlayer(player(), fractionPlayer.get());
  }
}
