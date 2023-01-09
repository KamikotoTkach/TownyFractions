package clockwork.fractions;

import clockwork.fractions.board.BoardListener;
import clockwork.fractions.commandBlocker.BlockCommandExecutor;
import clockwork.fractions.commandBlocker.CommandListener;
import clockwork.fractions.fractions.commands.argument.FractionsArg;
import clockwork.fractions.fractions.commands.argument.InvitedToFractionsArg;
import clockwork.fractions.fractions.commands.argument.PlayersAtFraction;
import clockwork.fractions.fractions.commands.argument.RanksAtFractions;
import clockwork.fractions.fractions.commands.command.*;
import clockwork.fractions.fractions.storage.FractionPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import tkachgeek.commands.command.ArgumentSet;
import tkachgeek.commands.command.Command;
import tkachgeek.commands.command.arguments.ExactStringArg;
import tkachgeek.commands.command.arguments.PlayerArg;
import tkachgeek.commands.command.arguments.spaced.SpacedStringArg;
import tkachgeek.config.yaml.YmlConfigManager;

import java.util.Random;
import java.util.function.Predicate;

public final class Fractions extends JavaPlugin {
  private static final Predicate<CommandSender> HAS_FRACTION = x -> {
    var fractionPlayer = FractionPlayer.get(x);
    return fractionPlayer.isPresent() && fractionPlayer.get().hasFraction();
  };
  private static final Predicate<CommandSender> HASNT_FRACTION = x -> HAS_FRACTION.negate().test(x);
  private static final Predicate<CommandSender> CAN_INVITE = HAS_FRACTION;
  private static final Predicate<CommandSender> CAN_KICK = CAN_INVITE;
  public static YmlConfigManager yml;
  
  @Override
  public void onEnable() {
    yml = new YmlConfigManager(this);
    sendLogo();
    
    new Command("fraction")
       .arguments(
          new ArgumentSet(
             new JoinFraction(),
             new ExactStringArg("join"),
             new InvitedToFractionsArg()
          ).canExecute(HASNT_FRACTION),
          
          new ArgumentSet(
             new LeaveFraction(),
             new ExactStringArg("leave")
          ).canExecute(HAS_FRACTION).confirmWith("подтверждаю", 60),
          
          new ArgumentSet(
             new KickFromFraction(),
             new ExactStringArg("kick"),
             new PlayersAtFraction()
          ).canExecute(CAN_KICK),
          
          new ArgumentSet(
             new InviteFraction(),
             new ExactStringArg("invite"),
             new PlayerArg()
          ).canExecute(CAN_INVITE),

          new ArgumentSet(
             new SetFractionRank(),
             new ExactStringArg("setRank"),
             new PlayersAtFraction(),
             new RanksAtFractions()
          ).canExecute(CAN_INVITE)
       ).register(this);
  
    new Command("blockCMD",
                new BlockCommandExecutor(),
                new FractionsArg(),
                new RanksAtFractions(),
                new SpacedStringArg("команда"))
       .register(this);
  
    Bukkit.getPluginManager().registerEvents(new CommandListener(), this);
    Bukkit.getPluginManager().registerEvents(new BoardListener(), this);
  }
  
  private void sendLogo() {
    char[] symbols = "1234569abcde".toCharArray();
    char symbol = symbols[new Random().nextInt(symbols.length)];
  
    Bukkit.getConsoleSender().sendMessage("");
    Bukkit.getConsoleSender().sendMessage("§z  ___ _         _  __      __       _   ".replace('z', symbol));
    Bukkit.getConsoleSender().sendMessage("§z / __| |___  __| |_\\ \\    / /__ _ _| |__".replace('z', symbol));
    Bukkit.getConsoleSender().sendMessage("§z| (__| / _ \\/ _| / /\\ \\/\\/ / _ \\ '_| / /".replace('z', symbol));
    Bukkit.getConsoleSender().sendMessage("§z \\___|_\\___/\\__|_\\_\\ \\_/\\_/\\___/_| |_\\_\\".replace('z', symbol));
    Bukkit.getConsoleSender().sendMessage("§z                            cwcode.ru/vk".replace('z', symbol));
    Bukkit.getConsoleSender().sendMessage("");
  }
  
  @Override
  public void onDisable() {
    super.onDisable();
  }
}
