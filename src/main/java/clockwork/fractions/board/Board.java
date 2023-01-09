package clockwork.fractions.board;

import fr.minuskube.netherboard.Netherboard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Board {
  public static final HashMap<Integer, String> lines = new HashMap<>();
  public static final List<String> placeholders = Arrays.asList(
     "%money%", "%game%", "%team%"
  );
  private static final Netherboard board = Netherboard.instance();
  public static String boardName;
  String string = """
     <Название сервера>
     <дата>
     Ник: <ник>
     Локация: <локация>
     Деньги: <деньги>
     Город: <если есть то писать <название города> | <налог за город>а если нет то ->
     Предприятие: если есть то писать <название предприятия> | <налог за предприятие>а если нет то ->
     Дом: <если есть то писать <название дома> | <налог за дом>а если нет то писать ->
     Налог: <налог за всё>
     Звание: <звание в полиции или на военной службе. Писать синим звание в полиции или писать звание коричневым на военной службе>
     Штраф: <если есть то писать штраф в деньгах если нет писать ->
     Онлайн: <онлайн>
     До рестарта: <сколько осталось>
     <ссылка на сайт>
     """;
  
  public static void initialize(JavaPlugin plugin) {
    Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
      Bukkit.getServer().getOnlinePlayers().forEach(Board::update);
    }, 0, 20);
    //Bukkit.getPluginManager().registerEvents(new Board(), plugin);
  }
  
  public static void createBoard(Player player) {
    board.createBoard(player, boardName);
  }
  
  public static List<String> viewPlaceholders() {
    List<String> placeholders = new ArrayList<>();
    placeholders.add("Плэйсхолдеры:");
    Board.placeholders.forEach(placeholder -> {
      placeholders.add("- §d" + placeholder);
    });
    return placeholders;
  }
  
  public static void update(Player player) {
    if (!board.hasBoard(player)) {
      board.createBoard(player, boardName);
    }
    board.getBoard(player).clear();
    lines.keySet().forEach(key -> {
      String line = replacePlaceholders(player, key);
      board.getBoard(player).set(line, key);
    });
  }
  
  private static String replacePlaceholders(Player player, int key) {
    String line = lines.get(key);
    //if (Vote.pollingStations.containsKey(player.getWorld().getName())) {
    //line = line.replaceAll("%game%", Vote.pollingStations.get(player.getWorld().getName()).name);
    //} else {
    line = line.replaceAll("%game%", "§bGame");
    //}
    
    //line = line.replaceAll("%money%", Coins.players.getOrDefault(player.getName(), 0.0).toString());
    
    //String team = Teams.getTeam(player);
    //if (!team.isEmpty()) {
    //line = line.replaceAll("%team%", Teams.teams.get(team).color + Teams.teams.get(team).name);
    //} else {
    //  line = line.replaceAll("%team%", "§bTeam");
    //}
    return line;
  }
  
  public static String setBoardName(String name) {
    boardName = name;
    //Cfg.boardCfg.setBoardName(name);
    Bukkit.getOnlinePlayers().forEach(player -> {
      board.createBoard(player, boardName);
    });
    return "§aНазвание успешно задано!";
  }
  
  public static String setLine(int line, String value) {
    lines.put(line, value);
    //Cfg.boardCfg.setLine(line, value);
    return "§aЛиния успешно установлена!";
  }
  
  public static String removeLine(int line) {
    if (lines.containsKey(line)) {
      lines.remove(line);
      //Cfg.boardCfg.removeLine(line);
      return "§aЛиния успешно удалена!";
    }
    return "§cЛиния не задана!";
  }
}
