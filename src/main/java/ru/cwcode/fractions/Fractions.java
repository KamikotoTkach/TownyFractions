package ru.cwcode.fractions;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.ServerOperator;
import org.bukkit.plugin.java.JavaPlugin;
import ru.cwcode.fractions.board.BoardListener;
import ru.cwcode.fractions.commandBlocker.BlockCommandExecutor;
import ru.cwcode.fractions.commandBlocker.CommandListener;
import ru.cwcode.fractions.config.Config;
import ru.cwcode.fractions.criminal.CriminalListener;
import ru.cwcode.fractions.criminal.commands.argument.PrisonersArg;
import ru.cwcode.fractions.criminal.commands.argument.PrisonsArg;
import ru.cwcode.fractions.criminal.commands.command.*;
import ru.cwcode.fractions.fractions.FractionPlayer;
import ru.cwcode.fractions.fractions.commands.FractionInfo;
import ru.cwcode.fractions.fractions.commands.argument.*;
import ru.cwcode.fractions.fractions.commands.command.*;
import ru.cwcode.fractions.fractions.commands.command.admin.AdminSetFraction;
import ru.cwcode.fractions.fractions.commands.command.admin.AdminSetRestartTime;
import ru.cwcode.fractions.fractions.commands.command.admin.AdminSetWanted;
import ru.cwcode.logo.Logo;
import tkachgeek.commands.command.ArgumentSet;
import tkachgeek.commands.command.Command;
import tkachgeek.commands.command.arguments.ExactStringArg;
import tkachgeek.commands.command.arguments.basic.IntegerArg;
import tkachgeek.commands.command.arguments.basic.StringArg;
import tkachgeek.commands.command.arguments.bukkit.PlayerArg;
import tkachgeek.commands.command.arguments.datetime.TimeArg;
import tkachgeek.commands.command.arguments.spaced.SpacedStringArg;
import tkachgeek.config.yaml.FlushCommand;
import tkachgeek.config.yaml.ReloadCommand;
import tkachgeek.config.yaml.YmlConfigManager;
import tkachgeek.tkachutils.scheduler.Scheduler;

import java.util.Optional;
import java.util.function.Predicate;

public final class Fractions extends JavaPlugin {
  public static final Predicate<CommandSender> HAS_FRACTION = player -> {
    var fractionPlayer = FractionPlayer.get(player);
    return fractionPlayer.isPresent() && fractionPlayer.get().hasFraction();
  };
  public static final Predicate<CommandSender> HASNT_FRACTION = player -> HAS_FRACTION.negate().test(player);
  public static final Predicate<CommandSender> CAN_RAID = player -> {
    var fractionPlayer = FractionPlayer.get(player);
    return fractionPlayer.isPresent() && (!fractionPlayer.get().hasFraction() || fractionPlayer.get().canRaid());
  };
  public static final Predicate<CommandSender> CAN_INVITE = player -> {
    var fractionPlayer = FractionPlayer.get(player);
    return fractionPlayer.isPresent() && fractionPlayer.get().hasTopRank();
  };
  public static final Predicate<CommandSender> CAN_KICK = CAN_INVITE;
  public static final Predicate<CommandSender> HAS_TOP_RANK = player -> {
    var fractionPlayer = FractionPlayer.get(player);
    return fractionPlayer.isPresent() && fractionPlayer.get().hasTopRank();
  };
  public static final Predicate<CommandSender> IS_MINISTRO_DELLA_POLIZIA = player -> {
    var fractionPlayer = FractionPlayer.get(player);
    return fractionPlayer.isPresent() && fractionPlayer.get().hasTopRank() && fractionPlayer.get().isPoliceman();
  };
  public static final Predicate<CommandSender> IS_MINISTRO_DELLA_OBORONA = player -> {
    var fractionPlayer = FractionPlayer.get(player);
    return fractionPlayer.isPresent() && fractionPlayer.get().hasTopRank() && fractionPlayer.get().isMilitary();
  };
  public static final Predicate<CommandSender> IS_POLICEMAN = player -> {
    var fractionPlayer = FractionPlayer.get(player);
    return fractionPlayer.isPresent() && fractionPlayer.get().isPoliceman();
  };
  public static final Predicate<CommandSender> IS_MILITARY = player -> {
    var fractionPlayer = FractionPlayer.get(player);
    return fractionPlayer.isPresent() && fractionPlayer.get().isMilitary();
  };
  public static final Predicate<CommandSender> IS_BANDIT = player -> {
    var fractionPlayer = FractionPlayer.get(player);
    return fractionPlayer.isPresent() && fractionPlayer.get().isBandit();
  };
  
  public static final Predicate<CommandSender> HAS_BANDIT_FRACTION = x -> {
    Optional<FractionPlayer> fractionPlayer = FractionPlayer.get(x);
    return fractionPlayer.isPresent() && fractionPlayer.get().isBandit() && fractionPlayer.get().hasTopRank();
  };
  public static final Predicate<CommandSender> HAS_INVITE = x -> {
    Optional<FractionPlayer> fractionPlayer = FractionPlayer.get(x);
    return fractionPlayer.isPresent() && fractionPlayer.get().getInvitedTo().size() > 0 || !Config.getInstance().isPeacefulWipe();
  };
  private static final Predicate<CommandSender> NOT_BANDIT = x -> IS_BANDIT.negate().test(x);
  private static final Predicate<CommandSender> HAS_SALARY = x -> {
    Optional<FractionPlayer> fractionPlayer = FractionPlayer.get(x);
    return fractionPlayer.isPresent() && fractionPlayer.get().canWithdrawSalary();
  };
  public static JavaPlugin plugin;
  public static YmlConfigManager yml;
  
  @Override
  public void onEnable() {
    yml = new YmlConfigManager(this);
    yml.scheduleAutosave(20 * 60 * 10, true);
    
    plugin = this;
    Logo.getInstance().sendLogo();
    
    new Command("fraction", new FractionInfo())
       .subCommands(
          new Command("admin")
             .subCommands(
                FlushCommand.get(yml),
                ReloadCommand.get(yml)
             ).arguments(
                new ArgumentSet(new AdminSetFraction(), new ExactStringArg("editPlayer"),
                                new PlayerArg(),
                                new FractionsArg(),
                                new RanksAtFraction(2).optional())
                   .help("?????????????????? ???????????????? ???????????? ?????????????? ?????? ????????"),
                
                new ArgumentSet(new BlockCommandExecutor(), new ExactStringArg("blockCMD"),
                                new FractionsArg(),
                                new RanksAtFraction(1),
                                new SpacedStringArg("??????????????"))
                   .help("?????????????????? ?????????????????????????? ?????????????? ?????? ???????? ??????????????, ?????????? ??????, ?????? ?????????? ?????????????????? ?????????????? ?? ?????????????????? ???????? (?????? ???????? ????????)"),
                
                new ArgumentSet(new AdminSetRestartTime(), new ExactStringArg("setRestartTime"),
                                new TimeArg()),
                
                new ArgumentSet(new AdminSetWanted(), new ExactStringArg("setWanted"),
                                new PlayerArg(),
                                new IntegerArg().setMin(0))
             )
       ).arguments(
          new ArgumentSet(new JoinFraction(), new ExactStringArg("join"), new InvitedToFractionsArg())
             .canExecute(HASNT_FRACTION.and(HAS_INVITE))
             .help("?????????????????? ???????????????? ???? ??????????????, ?? ?????????????? ?????? ????????????????????"),
          
          new ArgumentSet(new RenameRank(), new ExactStringArg("renameRank"), new RanksAtSenderFraction(), new StringArg("?????????? ???????????????? ??????????"))
             .canExecute(HAS_BANDIT_FRACTION)
             .help("?????????????????? ?????????????????????????? ???????? (???????????? ?????? ????????????????)"),
          
          new ArgumentSet(new GetSalary(), new ExactStringArg("getSalary"))
             .canExecute(HAS_SALARY)
             .help("?????????????????? ?????????? ????????????????"),
          
          new ArgumentSet(new LeaveFraction(), new ExactStringArg("leave"))
             .canExecute(HAS_FRACTION)
             .confirmWith("??????????????????????", 60)
             .help("?????????????????? ???????????????? ??????????????"),
          
          new ArgumentSet(new KickFromFraction(), new ExactStringArg("kick"), new PlayersAtSenderFraction())
             .canExecute(CAN_KICK)
             .help("?????????????????? ?????????????? ???????????? ???? ??????????????"),
          
          new ArgumentSet(new InviteFraction(), new ExactStringArg("invite"), new PlayerArg())
             .canExecute(CAN_INVITE)
             .help("?????????????????? ???????????????????? ???????????? ???? ??????????????"),
          
          new ArgumentSet(new SetFractionRank(), new ExactStringArg("setRank"), new PlayersAtSenderFraction(), new RanksAtSenderFraction())
             .canExecute(CAN_INVITE)
             .help("?????????????????? ???????????????????? ???????? ???????????? ???? ??????????????")
       
       ).register(this);
    
    new Command("prison")
       .arguments(
          new ArgumentSet(
             new CreatePrison(),
             new ExactStringArg("create"),
             new StringArg("????????????????").optional()
          ).canExecute(IS_MINISTRO_DELLA_POLIZIA.or(ServerOperator::isOp)).help("?????????????? ????????????"),
   
          new ArgumentSet(
             new DeletePrison(),
             new ExactStringArg("delete"),
             new PrisonsArg().optional()
          ).canExecute(IS_MINISTRO_DELLA_POLIZIA.or(ServerOperator::isOp)).help("?????????????? ????????????"),
   
          new ArgumentSet(
             new PrisonArrest(),
             new ExactStringArg("arrest"),
             new PrisonsArg().optional()
          ).canExecute(IS_POLICEMAN.or(IS_MILITARY)).help("???????????????????? ????????????"),
   
          new ArgumentSet(
             new PrisonDemobilize(),
             new ExactStringArg("free"),
             new PrisonersArg()
          ).canExecute(IS_MINISTRO_DELLA_POLIZIA.or(ServerOperator::isOp)).help("???????????????????? ????????????")

       ).register(this);
    
    new Command("setshoker", new SetShocker())
       .register(this);
    
    new Command("raid")
       .arguments(new ArgumentSet(new RaidCommand(), new AllTerritories()).canExecute(CAN_RAID).help("???????????? ????????")
       ).register(this);
    
    new Command("stats", new StatsCmd()).register(this);
    
    Bukkit.getPluginManager().registerEvents(new CommandListener(), this);
    Bukkit.getPluginManager().registerEvents(new BoardListener(), this);
    Bukkit.getPluginManager().registerEvents(new CriminalListener(), this);
    
    Scheduler.create(yml).perform((yml) -> yml.storeAll(true)).register(this, 60 * 20);
  }
  
  @Override
  public void onDisable() {
    yml.storeAll(true);
  }
}
