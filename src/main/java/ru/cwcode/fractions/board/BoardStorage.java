package ru.cwcode.fractions.board;

import ru.cwcode.fractions.Fractions;
import tkachgeek.config.minilocale.Message;
import tkachgeek.config.yaml.YmlConfig;

public class BoardStorage extends YmlConfig {
  static BoardStorage instance;
  
  Message serverName = new Message("Не указано");
  Message link = new Message("Не указано");
  String timeFormat = "yyyy-MM-dd HH:mm";
  
  public BoardStorage() {
  }
  
  public static BoardStorage getInstance() {
    if (instance == null) load();
    return instance;
  }
  
  public static void load() {
    instance = Fractions.yml.load("Board", BoardStorage.class);
  }
}
