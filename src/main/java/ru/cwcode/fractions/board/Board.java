package ru.cwcode.fractions.board;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Town;
import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.cwcode.fractions.Fractions;
import ru.cwcode.fractions.fractions.FractionPlayer;
import ru.cwcode.fractions.prison.CriminalStorage;
import tkachgeek.banks.Banks;
import tkachgeek.config.minilocale.Placeholder;
import tkachgeek.townyterritory.TerrAPI;
import tkachgeek.townyterritory.territory.Territory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Board {
  private static final String empty = "";
  
  static Board instance;
  private static int restart_time = 0;
  
  static {
    Bukkit.getScheduler().runTaskTimer(Fractions.plugin, () -> {
      //Board.getInstance().update(BoardLine.server_online);
      //Board.getInstance().update(BoardLine.data_time);
      //Board.getInstance().update(BoardLine.money);
      if (restart_time > 0) {
        restart_time--;
      }
      Board.getInstance().update();
    }, 0, 20);
  }

  private final Netherboard board = Netherboard.instance();
  private final Map<UUID, Map<BoardLine, Integer>> player_lines = new HashMap<>();
  private final BoardStorage cfg = BoardStorage.getInstance();
  private final String server_name = cfg.serverName.getLegacySection();
  String string = """
      1. По середине жирными буквами\s
      <bold>БелымMine<gold>Capital\s
      2. Дата и время белым через |
      3. БелымНик серым: <gold><ник>
      4. БелымБаланссерым: <gold><баланс>
      5. БелымЛокациясерым: <gold><локация>
      6. БелымВсе налогисерым: <gold><все налоги>
      7. Белымштрафсерым: красным<штраф>
      8. БелымОнлайнсерым: <онлайн>
      9. Былым время до рестартасерым: <время до рестарта>
      10. По середине название сайта
     """;
  
  private Board() {
  }
  
  public static Board getInstance() {
    if (instance == null) instance = new Board();
    return instance;
  }
  
  public void createBoard(Player player) {
    board.createBoard(player, server_name);
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
      board.set(this.validateLine(lines.get(line)), index);
      player_lines.put(line, index);
      index--;
    }
  }
  
  public void update(Player player, BoardLine line) {
    String value = this.getLine(player, line);
    int index = this.getPlayerLines(player.getUniqueId()).getOrDefault(line, 0);
    if (value.equals(empty) || index == 0) {
      update(player);
      return;
    }
    
    this.getBoard(player).set(value, index);
  }
  
  private String validateLine(String line) {
    if (line.length() > 40) {
      line = line.substring(0, 40);
    }
    
    return line;
  }
  
  private Map<BoardLine, String> getLines(Player player) {
    Map<BoardLine, String> lines = new LinkedHashMap<>();
    for (BoardLine line : BoardLine.values()) {
      String value = this.getLine(player, line);
      if (value.equals(empty)) {
        continue;
      }
      
      lines.put(line, value);
    }
    
    return lines;
  }
  
  private String getLine(Player player, BoardLine line) {
    StringBuilder text = new StringBuilder(empty);
    String value = empty;
  
    switch (line) {
      case data_time -> {
        value = dateTime();
        text.append(BoardStorage.getInstance().time.getLegacySection(Placeholder.add("value", value)));
      }
      case wanted -> {
        value = getWanted(player);
        text.append(BoardStorage.getInstance().wanted.getLegacySection(Placeholder.add("value", value)));
      }
      case player -> {
        value = getPlayerName(player);
        text.append(BoardStorage.getInstance().player.getLegacySection(Placeholder.add("value", value)));
      }
      case rank -> {
        value = getRank(player);
        text.append(BoardStorage.getInstance().rank.getLegacySection(Placeholder.add("value", value)));
      }
      case money -> {
        value = getMoney(player);
        text.append(BoardStorage.getInstance().balance.getLegacySection(Placeholder.add("value", value)));
      }
      case location -> {
        value = getLocation(player);
        text.append(BoardStorage.getInstance().location.getLegacySection(Placeholder.add("value", value)));
      }
      case tax -> {
        value = getTaxes(player);
        text.append(BoardStorage.getInstance().taxes.getLegacySection(Placeholder.add("value", value)));
      }
      case fine -> {
        value = getFine(player);
        text.append(BoardStorage.getInstance().fine.getLegacySection(Placeholder.add("value", value)));
      }
      case server_online -> {
        value = getOnline();
        text.append(BoardStorage.getInstance().online.getLegacySection(Placeholder.add("value", value)));
      }
      case restart_time -> {
        value = getRestartTime();
        text.append(BoardStorage.getInstance().restart.getLegacySection(Placeholder.add("value", value)));
      }
      case server_link -> {
        text.append(BoardStorage.getInstance().siteLink.getLegacySection());
      }
    }
  
    if (value.equals(empty)) {
      return value;
    }
  
    return text.toString();
  }
  
  public String getLocation(Player player) {
    String location = empty;
    
    Town townInst = TownyAPI.getInstance().getTown(player.getLocation());
    if (townInst != null) location = townInst.getName();
  
    Optional<Territory> territoryAt = TerrAPI.getTerritoryBy(player);
    if (territoryAt.isPresent()) location = territoryAt.get().getName();
    
    return location;
  }
  
  public String getMoney(Player player) {
    return Banks.getEconomy().getBalance(player) + "$";
  }
  
  public String dateTime() {
    String delimiter = "§7 | §f";
    return LocalDateTime.now().format(DateTimeFormatter.ofPattern(cfg.timeFormat)).replace(" ", delimiter);
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
  
  public String getWanted(Player player) {
    StringBuilder value = new StringBuilder(empty);
    int wanted_level = CriminalStorage.getInstance().getWantedLevel(player);
    
    if (wanted_level > 0) {
      for (int star = 0; star < 5; star++) {
        if (star < wanted_level) {
          value.append("§6");
        } else {
          value.append("§8");
        }
        value.append("✰").append(" ");
      }
    }
    
    return value.toString();
  }
  
  public String getFine(Player player) {
    StringBuilder value = new StringBuilder(empty);
    int wanted_level = CriminalStorage.getInstance().getWantedLevel(player);
    
    int fine = 0;
    fine += wanted_level * CriminalStorage.getInstance().prison_fine;
    
    if (fine > 0) {
      value.append("§4").append(fine).append("$");
    }
    
    return value.toString();
  }
  
  public String getPlayerName(Player player) {
    return player.getDisplayName();
  }
  
  public String getRank(Player player) {
    StringBuilder value = new StringBuilder(empty);
    
    FractionPlayer fractionPlayer = FractionPlayer.get(player);
    if (fractionPlayer.hasFraction()) {
      value.append(fractionPlayer.getRank().name()).append(" ");
    }
    
    return value.toString();
  }
  
  public String getOnline() {
    return Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers();
  }
  
  public String getRestartTime() {
    if (restart_time == 0) {
      return empty;
    }
    
    Duration duration = Duration.ofSeconds(restart_time);
    long HH = duration.toHours();
    long MM = duration.toMinutesPart();
    long SS = duration.toSecondsPart();
    
    return String.format("%02d:%02d:%02d", HH, MM, SS);
  }
  
  public void setRestartTime(int time) {
    restart_time = time;
  }
}
