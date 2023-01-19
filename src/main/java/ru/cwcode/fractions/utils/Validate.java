package ru.cwcode.fractions.utils;

import org.bukkit.entity.Player;
import ru.cwcode.fractions.config.Config;
import ru.cwcode.fractions.config.Messages;
import ru.cwcode.fractions.criminal.CriminalAPI;
import ru.cwcode.fractions.criminal.CriminalStorage;
import ru.cwcode.fractions.fractions.FractionPlayer;
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
    if (!fraction_general.getFraction().name().equalsIgnoreCase(fraction_player.getFraction().name())) {
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
  
  public static void notNull(Object object, String name) throws MessageReturn {
    if (object != null) {
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
  
  public static void isNotPrisoner(Player player) throws MessageReturn {
    if (CriminalAPI.isPrisoner(player)) {
      Messages.getInstance().is_prisoner.throwback();
    }
  }
  
  public static void isPrisoner(Player player) throws MessageReturn {
    if (!CriminalAPI.isPrisoner(player)) {
      Messages.getInstance().you_is_prisoner.throwback();
    }
  }
  
  public static void existPrison(String prison_name) throws MessageReturn {
    if (!CriminalAPI.hasPrison(prison_name)) {
      Messages.getInstance().hasnt_$prison.throwback(Placeholder.add("prison", prison_name));
    }
  }
  
  public static void notExistPrison(String prison_name) throws MessageReturn {
    if (CriminalAPI.hasPrison(prison_name)) {
      Messages.getInstance().has_prison.throwback();
    }
  }
  
  public static void isWanted(Player player) throws MessageReturn {
    if (!CriminalStorage.getInstance().isWanted(player)) {
      Messages.getInstance().isnt_wanted_$player.throwback(Placeholder.add("player", player.getName()));
    }
  }
  
  public static void isntWanted(Player player) throws MessageReturn {
    if (CriminalStorage.getInstance().isWanted(player)) {
      Messages.getInstance().is_wanted_$player.throwback(Placeholder.add("player", player.getName()));
    }
  }
  
  public static void isPrisoner(String name) throws MessageReturn {
    if (!CriminalAPI.isPrisoner(name)) {
      Messages.getInstance().isnt_prisoner.throwback();
    }
  }
  
  public static void canRaid(FractionPlayer fractionPlayer) throws MessageReturn {
    if (!fractionPlayer.canRaid()) {
      Messages.getInstance().you_not_general.throwback();
    }
  }
  
  public static void isNotPeacefulWipe() throws MessageReturn {
    if (Config.getInstance().isPeacefulWipe()) {
      Messages.getInstance().peaceful_wipe_message.throwback();
    }
  }
}
