package clockwork.fractions;

import clockwork.fractions.storage.fractions.FractionInstance;
import clockwork.fractions.storage.FractionPlayer;
import clockwork.fractions.storage.Rank;
import clockwork.fractions.storage.config.FractionsStorage;
import clockwork.fractions.storage.config.Messages;
import clockwork.fractions.storage.config.PlayerStorage;
import clockwork.fractions.utils.Validate;
import org.bukkit.entity.Player;
import tkachgeek.tkachutils.messages.MessageReturn;

import java.util.List;
import java.util.Optional;

public class FractionsAPI {
  public static FractionsStorage cfg = FractionsStorage.getInstance();
  
  public static void setRank(Player general, FractionPlayer fraction_player, String rank_name) throws MessageReturn {
    FractionPlayer fraction_general = PlayerStorage.get(general);
    
    Validate.canChangeRank(fraction_general);
    Validate.hasFraction(fraction_player);
    Validate.equalFractions(fraction_general, fraction_player);
    Validate.rankGreater(fraction_general, fraction_player);
    
    Optional<Rank> rank = fraction_general.getFractionRank(rank_name);
    if (!rank.isPresent()) {
      Messages.getInstance().is_not_fraction_rank.throwback();
    }
    fraction_player.setRank(rank.get());
    Messages.getInstance().rank_successfully_set.send(general);
  }
  
  public static void invitePlayer(Player general, FractionPlayer fraction_player) throws MessageReturn {
    FractionPlayer fraction_general = PlayerStorage.get(general);
  
    Validate.canChangeMembers(fraction_general);
    
    fraction_player.addInvite(fraction_general.getFractionName());
    Messages.getInstance().player_invited_successfully.send(general);
  }
  
  public static void kickPlayer(Player general, FractionPlayer fraction_player) throws MessageReturn {
    FractionPlayer fraction_general = PlayerStorage.get(general);
  
    Validate.hasFraction(fraction_player);
    Validate.equalFractions(fraction_general, fraction_player);
    Validate.canChangeMembers(fraction_general);
    Validate.rankGreater(fraction_general, fraction_player);
  
    fraction_player.kickedFraction();
    Messages.getInstance().player_kicked_successfully.send(general);
  }
  
  public static List<FractionInstance> getFractions() {
    return cfg.getFractions();
  }
  
  public static boolean hasFraction(String name) {
    return cfg.hasFraction(name);
  }
}
