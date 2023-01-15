package ru.cwcode.fractions.board;

import ru.cwcode.fractions.Fractions;
import tkachgeek.config.base.Reloadable;
import tkachgeek.config.minilocale.Message;
import tkachgeek.config.yaml.YmlConfig;

public class BoardStorage extends YmlConfig implements Reloadable {
  static BoardStorage instance;
  
  Message serverName = new Message("<bold><white>Mine<gold>Capital</bold>");
  String timeFormat = "HH:mm yyyy.MM.dd";
  Message time = new Message("  <white>Время: <value>");
  Message wanted = new Message("  <white>Розыск: <value>");
  Message player = new Message("  <white>Игрок:<gold> <value>");
  Message rank = new Message("  <white>Должность:<gold> <value>");
  Message balance = new Message("  <white>Баланс:<gold> <value>");
  Message location = new Message("  <white>Локация:<gold> <value>");
  Message taxes = new Message("  <white>Налог:<gold> <value>");
  Message fine = new Message("  <white>Штраф:<gold> <value>");
  Message online = new Message("  <white>Онлайн:<gold> <value>");
  Message restart = new Message("  <white>Рестарт через:<gold> <value>");
  Message siteLink = new Message("  <white>Наш сайт: <link>");
  
  public BoardStorage() {
  }
  
  public static BoardStorage getInstance() {
    if (instance == null) load();
    return instance;
  }
  
  public static void load() {
    instance = Fractions.yml.load("Board", BoardStorage.class);
  }
  
  @Override
  public void reload() {
    load();
  }
}
