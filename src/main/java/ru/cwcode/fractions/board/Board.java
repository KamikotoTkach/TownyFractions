package ru.cwcode.fractions.board;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Town;
import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.cwcode.fractions.Fractions;
import ru.cwcode.fractions.fractions.storage.FractionPlayer;
import tkachgeek.banks.Banks;
import tkachgeek.townyterritory.TerrAPI;
import tkachgeek.townyterritory.territory.Territory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Board {
  static Board instance;
  
  static {
    Bukkit.getScheduler().runTaskTimerAsynchronously(Fractions.plugin, () -> {
      //Board.getInstance().update(BoardLine.server_online);
      //Board.getInstance().update(BoardLine.data_time);
      //Board.getInstance().update(BoardLine.money);
      Board.getInstance().update();
    }, 0, 20);
  }

  private final Netherboard board = Netherboard.instance();
  private final Map<UUID, Map<BoardLine, Integer>> player_lines = new HashMap<>();
  private final BoardStorage cfg = BoardStorage.getInstance();
  
  String string = """
     //-> название сервера ~
     -> дата время +
     -> локация +
     -> деньги +
     -> подсчитанные все налоги +?
     -> звание +
     -> штраф -
     -> онлайн +
     -> до рестарта -
     -> сайт ~
     """;
  private String emptyLine = "->";
  
  private Board() {
  }
  
  public static Board getInstance() {
    if (instance == null) instance = new Board();
    return instance;
  }
  
  public void createBoard(Player player) {
    board.createBoard(player, cfg.serverName.getLegacy());
  }
  
  public BPlayerBoard getBoard(Player player) {
    if (!board.hasBoard(player)) {
      this.createBoard(player);
    }
    
    return board.getBoard(player);
  }
  
  public Map<BoardLine, Integer> getPlayerLines(UUID uuid) {
    if (!player_lines.containsKey(uuid)) {
      player_lines.put(uuid, new HashMap<>());
      return player_lines.get(uuid);
    }
    
    return player_lines.get(uuid);
  }
  
  public void update() {
    for (Player player : Bukkit.getOnlinePlayers()) {
      update(player);
    }
  }
  
  public void update(BoardLine line) {
    for (Player player : Bukkit.getOnlinePlayers()) {
      update(player, line);
    }
  }
  
  public void update(Player player) {
    BPlayerBoard board = this.getBoard(player);
    Map<BoardLine, String> lines = this.getLines(player);
    Map<BoardLine, Integer> player_lines = this.getPlayerLines(player.getUniqueId());
    board.clear();
    player_lines.clear();
    int index = lines.size();
    
    for (BoardLine line : lines.keySet()) {
      board.set(lines.get(line), index);
      player_lines.put(line, index);
      index--;
    }
  }
  
  public void update(Player player, BoardLine line) {
    String value = this.getLine(player, line);
    int index = this.getPlayerLines(player.getUniqueId()).getOrDefault(line, 0);
    if (value.equals(emptyLine) || index == 0) {
      update(player);
      return;
    }
    
    this.getBoard(player).set(value, index);
  }
  
  private Map<BoardLine, String> getLines(Player player) {
    Map<BoardLine, String> lines = new LinkedHashMap<>();
    for (BoardLine line : BoardLine.values()) {
      String value = this.getLine(player, line);
      if (value.equals(emptyLine)) {
        continue;
      }
      
      lines.put(line, value);
    }
    
    return lines;
  }
  
  private String getLine(Player player, BoardLine line) {
    String value = emptyLine;
    
    switch (line) {
      case data_time -> {
        return dateTime();
      }
      case location -> {
        return getLocation(player);
      }
      case money -> {
        return getMoney(player);
      }
      case tax -> {
        return getTaxes(player);
      }
      case rank -> {
        return getRank(player);
      }
      case fine -> {
      
      }
      case server_online -> {
        return getOnline();
      }
      case restart_time -> {
      
      }
      case server_link -> {
        return cfg.link.getLegacy();
      }
    }
    
    return value;
  }
  
  public String getLocation(Player player) {
    String location = emptyLine;
    
    Town townInst = TownyAPI.getInstance().getTown(player.getLocation());
    if (townInst != null) location = townInst.getName();
    
    Optional<Territory> territoryAt = TerrAPI.getTerritoryAt(player);
    if (territoryAt.isPresent()) location = territoryAt.get().getName();
    
    return location;
  }
  
  public String getMoney(Player player) {
    return Banks.getEconomy().getBalance(player) + "$";
  }
  
  public String dateTime() {
    return LocalDateTime.now().format(DateTimeFormatter.ofPattern(cfg.timeFormat));
  }
  
  public String getTaxes(Player player) {
    double townTax;
    try {
      townTax = TownyAPI.getInstance().getResident(player).getTown().getTaxes();//не уверен что это вообще значит
    } catch (NotRegisteredException e) {
      townTax = 0;
    }
    return (TerrAPI.getTaxesTotal(player) + townTax) + "$";
  }
  
  public String getRank(Player player) {
    FractionPlayer fractionPlayer = FractionPlayer.get(player);
    return fractionPlayer.hasFraction() ? fractionPlayer.getFormattedRank() : emptyLine;
  }
  
  public String getOnline() {
    return Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers();
  }
}
