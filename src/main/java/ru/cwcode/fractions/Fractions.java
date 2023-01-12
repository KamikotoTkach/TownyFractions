package ru.cwcode.fractions;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import ru.cwcode.fractions.board.BoardListener;
import ru.cwcode.fractions.commandBlocker.BlockCommandExecutor;
import ru.cwcode.fractions.commandBlocker.CommandListener;
import ru.cwcode.fractions.fractions.commands.FractionInfo;
import ru.cwcode.fractions.fractions.commands.argument.*;
import ru.cwcode.fractions.fractions.commands.command.*;
import ru.cwcode.fractions.fractions.commands.command.admin.AdminSetFraction;
import ru.cwcode.fractions.fractions.storage.FractionPlayer;
import ru.cwcode.logo.Logo;
import tkachgeek.commands.command.ArgumentSet;
import tkachgeek.commands.command.Command;
import tkachgeek.commands.command.arguments.ExactStringArg;
import tkachgeek.commands.command.arguments.basic.StringArg;
import tkachgeek.commands.command.arguments.bukkit.PlayerArg;
import tkachgeek.commands.command.arguments.spaced.SpacedStringArg;
import tkachgeek.config.yaml.YmlConfigManager;

import java.util.Optional;
import java.util.function.Predicate;

public final class Fractions extends JavaPlugin {
  private static final Predicate<CommandSender> HAS_FRACTION = x -> {
    var fractionPlayer = FractionPlayer.get(x);
    return fractionPlayer.isPresent() && fractionPlayer.get().hasFraction();
  };
  private static final Predicate<CommandSender> HASNT_FRACTION = x -> HAS_FRACTION.negate().test(x);
  private static final Predicate<CommandSender> CAN_INVITE = HAS_FRACTION; //todo:
  private static final Predicate<CommandSender> CAN_KICK = CAN_INVITE;
  private static final Predicate<CommandSender> HAS_BANDIT_FRACTION = x -> {
    Optional<FractionPlayer> fractionPlayer = FractionPlayer.get(x);
    return fractionPlayer.isPresent() && fractionPlayer.get().isBandit() && fractionPlayer.get().hasTopRank();
  };
  public static JavaPlugin plugin;
  public static YmlConfigManager yml;
  
  @Override
  public void onEnable() {
    yml = new YmlConfigManager(this);
    plugin = this;
    Logo.sendLogo();
    
    new Command("fraction", "fraction", new FractionInfo())
       .subCommands(
          new Command("admin")
             .arguments(
                new ArgumentSet(new AdminSetFraction(), new ExactStringArg("editPlayer"),
                                new PlayerArg(),
                                new FractionsArg(),
                                new RanksAtFraction(-1).optional())
                   .help("Позволяет изменить игроку фракцию или ранг"),
      
                new ArgumentSet(new BlockCommandExecutor(), new ExactStringArg("blockCmd"),
                                new FractionsArg(),
                                new RanksAtFraction(-1),
                                new SpacedStringArg("команда"))
                   .help("Позволяет заблокировать команду для всех игроков, кроме тех, кто имеет указанную фракцию и указанный ранг (или ранг выше)")
             )

       ).arguments(
   
          new ArgumentSet(new JoinFraction(), new ExactStringArg("join"), new InvitedToFractionsArg())
             .canExecute(HASNT_FRACTION)
             .help("Позволяет вступить во фракцию, в которую вас пригласили"),
   
          new ArgumentSet(new RenameRank(), new ExactStringArg("renameRank"), new RanksAtSenderFraction(), new StringArg("новое название ранга"))
             .canExecute(HAS_BANDIT_FRACTION)
             .help("Позволяет переименовать ранг (только для бандитов)"),
   
          new ArgumentSet(new LeaveFraction(), new ExactStringArg("leave"))
             .canExecute(HAS_FRACTION)
             .confirmWith("подтверждаю", 60)
             .help("Позволяет покинуть фракцию"),
   
          new ArgumentSet(new KickFromFraction(), new ExactStringArg("kick"), new PlayersAtSenderFraction())
             .canExecute(CAN_KICK)
             .help("Позволяет кикнуть игрока из фракции"),
   
          new ArgumentSet(new InviteFraction(), new ExactStringArg("invite"), new PlayerArg())
             .canExecute(CAN_INVITE)
             .help("Позволяет пригласить игрока во фракцию"),
   
          new ArgumentSet(new SetFractionRank(), new ExactStringArg("setRank"), new PlayersAtSenderFraction(), new RanksAtSenderFraction())
             .canExecute(CAN_INVITE)
             .help("Позволяет установить ранг игроку во фракции")

       ).register(this);
    
    Bukkit.getPluginManager().registerEvents(new CommandListener(), this);
    Bukkit.getPluginManager().registerEvents(new BoardListener(), this);
  }
  
  @Override
  public void onDisable() {
    yml.storeAll();
  }
}
