package ru.cwcode.fractions.config;

import ru.cwcode.fractions.Fractions;
import tkachgeek.config.minilocale.Message;
import tkachgeek.config.minilocale.messagePacks.DefaultMessagePack;
import tkachgeek.config.yaml.YmlConfig;

public class Messages extends YmlConfig {
  static Messages instance;
  public DefaultMessagePack defaults = new DefaultMessagePack();
  public Message you_not_general = new Message("<gold>Необходимо обладать рангом не ниже <yellow>\"Генерал\"");
  public Message player_hasnt_fraction = new Message("<gold>У игрока нет фракции");
  public Message you_are_not_in_the_same_fraction = new Message("<gold> У вас разные фракции");
  public Message your_rank_is_not_greater_than_the_$player = new Message("<gold>Ваш ранг должен быть выше, чем у <player>");
  public Message player_invited_successfully = new Message("<gold>Приглашение во фракцию успешно отправлено");
  public Message player_kicked_successfully = new Message("<gold>Игрок успешно исключен из фракции");
  public Message you_cannot_leave = new Message("<gold>Вы не можете покинуть фракцию");
  public Message you_leaved_$fraction = new Message("<gold>Вы покинули фракцию <yellow><fraction>");
  public Message you_kicked_$fraction = new Message("<gold>Вы были исключены из фракции <yellow><fraction>");
  public Message you_invited_$fraction = new Message("<gold>Вы были приглашены в фракцию <yellow><fraction>");
  public Message you_not_invited_to_$fraction = new Message("<gold>Вы не приглашены во фракцию <yellow><fraction>");
  public Message player_has_fraction_invite = new Message("<gold>Он уже приглашён");
  public Message you_has_fraction = new Message("<gold>У вас уже есть фракция. Покиньте её, чтобы принять приглашение");
  public Message fraction_$name_not_exist = new Message("<gold>Фракция с именем <yellow><name> <gold>не найдена");
  public Message is_not_fraction_rank = new Message("<gold>Введите правильный ранг фракции");
  public Message rank_successfully_set = new Message("<gold>Ранг успешно изменён");
  public Message your_rank_was_updated_to_$rank = new Message("<gold>Теперь вы <yellow><rank>");
  public Message you_hasnt_changemembers_permission = new Message("<gold>У вас нет прав менять состав участников фракции");
  public Message $name_not_exist = new Message("<yellow><name> <gold>не найден");
  public Message command_already_blocked = new Message("<gold>Такая команда уже заблокирована");
  public Message command_blocked_successfully = new Message("<gold>Команда успешно заблокирована");
  public Message you_need_to_lead_fraction_before_use_this = new Message("<gold>Необходимо возглавить фракцию, чтобы использовать эту команду");
  public Message you_hasnt_fraction = new Message("У вас нет фракции");
  public Message fractionInfoHeader$name$totalPlayers$online = new Message("<gray>Фракция <gold><name>");
  public Message fractionInfoRank$name$salary = new Message("<gray>- <gold><name> <gray>[<gold><salary>$<gray>]");
  public Message fractionInfoRanksHeader = new Message("Ранги:");
  public Message you_joined_fraction_$name = new Message("<gold>Вы вступили во фракцию <yellow><name>");
  
  public Messages() {
  }
  
  public static Messages getInstance() {
    if (instance == null) load();
    return instance;
  }
  
  public static void load() {
    instance = Fractions.yml.load("Messages", Messages.class);
  }
}
