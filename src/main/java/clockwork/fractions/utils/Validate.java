package clockwork.fractions.utils;

import clockwork.fractions.storage.FractionPlayer;
import clockwork.fractions.storage.config.Messages;
import tkachgeek.config.minilocale.Placeholder;
import tkachgeek.tkachutils.messages.MessageReturn;

public class Validate {
  public static void rankGreater(FractionPlayer fraction_general, FractionPlayer fraction_player) throws MessageReturn {
    if (!fraction_general.isGreater(fraction_player)) {
      Messages.getInstance().your_rank_is_not_greater_than_the_$player.throwback(Placeholder.add("player", fraction_player.getName()));
    }
  }
  
  public static void equalFractions(FractionPlayer fraction_general, FractionPlayer fraction_player) throws MessageReturn {
    if (!fraction_general.getFractionName().equalsIgnoreCase(fraction_player.getFractionName())) {
      Messages.getInstance().you_are_not_in_the_same_fraction.throwback();
    }
  }
  
  public static void hasFraction(FractionPlayer fraction_player) throws MessageReturn {
    if (!fraction_player.hasFraction()) {
      Messages.getInstance().player_hasnt_fraction.throwback();
    }
  }
  
  public static void canChangeRank(FractionPlayer fraction_general) throws MessageReturn {
    if (!fraction_general.canChangeRank()) {
      Messages.getInstance().you_not_general.throwback();
    }
  }
  
  public static void canChangeMembers(FractionPlayer fraction_general) throws MessageReturn{
    if (!fraction_general.canChangeMembers()) {
      Messages.getInstance().you_hasnt_changemembers_permission.throwback();
    }
  }
}