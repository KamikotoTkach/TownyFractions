package ru.cwcode.fractions.utils;

import ru.cwcode.fractions.config.Messages;
import ru.cwcode.fractions.fractions.storage.FractionPlayer;
import tkachgeek.config.minilocale.Placeholder;
import tkachgeek.tkachutils.messages.MessageReturn;

import java.util.Optional;

public class Validate {
  public static void rankGreater(FractionPlayer fraction_general, FractionPlayer fraction_player) throws MessageReturn {
    if (!fraction_general.isGreater(fraction_player)) {
      Messages.getInstance().your_rank_is_not_greater_than_the_$player.throwback(Placeholder.add("player", fraction_player.getName()));
    }
  }
  
  public static void equalFractions(FractionPlayer fraction_general, FractionPlayer fraction_player) throws MessageReturn {
    if (!fraction_general.getFraction().getName().equalsIgnoreCase(fraction_player.getFraction().getName())) {
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
  
  public static void canChangeMembers(FractionPlayer fraction_general) throws MessageReturn {
    if (!fraction_general.canChangeMembers()) {
      Messages.getInstance().you_hasnt_changemembers_permission.throwback();
    }
  }
  
  public static void isPresent(Optional<?> object, String name) throws MessageReturn {
    if (object.isEmpty()) {
      Messages.getInstance().$name_not_exist.throwback(Placeholder.add("name", name));
    }
  }
  
  public static void hasTopRank(FractionPlayer fp) throws MessageReturn {
    if (!fp.hasFraction() || !fp.hasTopRank()) {
      Messages.getInstance().you_need_to_lead_fraction_before_use_this.throwback();
    }
  }
  
  public static void youHasFraction(FractionPlayer fp) throws MessageReturn {
    if (!fp.hasFraction()) {
      Messages.getInstance().you_hasnt_fraction.throwback();
    }
  }
}
