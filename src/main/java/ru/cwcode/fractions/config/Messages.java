package ru.cwcode.fractions.config;

import ru.cwcode.fractions.Fractions;
import tkachgeek.config.base.Reloadable;
import tkachgeek.config.minilocale.Message;
import tkachgeek.config.minilocale.messagePacks.DefaultMessagePack;
import tkachgeek.config.yaml.YmlConfig;

public class Messages extends YmlConfig implements Reloadable {
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
  public Message has_prison = new Message("<gold>Тюрьма с таким названием уже существует");
  public Message hasnt_$prison = new Message("<gold>Тюрьма с названием <yellow><prison> <gold>не существует");
  public Message you_has_been_arrested = new Message("<gold>Вы были арестованы");
  public Message you_has_been_demobilized = new Message("<gold>Вы были освобождены");
  public Message prison_created_successfully = new Message("<gold>Тюрьма успешно создана");
  public Message prison_deleted_successfully = new Message("<gold>Тюрьма успешно удалена");
  public Message isnt_prisoner = new Message("<gold>Этот игрок не заключённый");
  public Message is_prisoner = new Message("<gold>Этот игрок уже мотает срок, отсиживается под шконкой, сидит в тюряжке, зэк");
  public Message isnt_shocked = new Message("<gold>Вы не смотрите на парализованного игрока");
  public Message you_need_to_register_this_territory_as_jail = new Message("<gold>Сперва нужно зарегистрировать данную территорию как тюрьму. \n<gray>/terr create Тюрьма <dark_gray>[название]<gray> и \n/terr claim <dark_gray>[название]<gray> \n(для этого её нужно купить через /plot claim");
  public Message is_wanted_$player = new Message("<yellow><player> <gold>объявлен в розыск");
  public Message isnt_wanted_$player = new Message("<yellow><player> <gold>не объявлен в розыск");
  public Message you_is_prisoner = new Message("<gold>Вы не можете этого сделать, так как являетесь заключённым");
  /* public Message you_are_not_military = new Message("<gold>Вы не военный");
   public Message you_are_not_policeman = new Message("<gold>Вы не полицейский");
   public Message you_are_not_bandit = new Message("<gold>Вы не бандит");
   public Message you_are_not_military_or_policeman = new Message("<gold>Вы не полицейский и не военный");*/
  public Message BANDIT_TO_PEACEFUL_preparingMessage_toAggressor = new Message("<gold>Вы нападаете на <victim>, подготовьтесь как следует");
  public Message BANDIT_TO_PEACEFUL_winMessage_toAggressor = new Message("<gold>Вы выиграли нападение");
  public Message BANDIT_TO_PEACEFUL_loseMessage_toAggressor = new Message("<gold>Ваше нападение не удалось");
  public Message BANDIT_TO_PEACEFUL_tieMessage_toAggressor = new Message("<gold>Ничья, время рейда вышло");
  public Message BANDIT_TO_PEACEFUL_preparingMessage_toVictim = new Message("<gold>На вас нападает <aggressor>, подготовьтесь как следует");
  public Message BANDIT_TO_PEACEFUL_winMessage_toVictim = new Message("<gold>Вы отбили нападение <aggressor>!");
  public Message BANDIT_TO_PEACEFUL_loseMessage_toVictim = new Message("<gold>Вы не смогли отбить нападение <aggressor>!");
  public Message BANDIT_TO_PEACEFUL_tieMessage_toVictim = new Message("<gold>Ничья");
  public Message MILITARY_TO_BANDIT_preparingMessage_toAggressor = new Message("<gold>Вы нападаете на <victim>, подготовьтесь как следует");
  public Message MILITARY_TO_BANDIT_winMessage_toAggressor = new Message("<gold>Вы выиграли нападение");
  public Message MILITARY_TO_BANDIT_loseMessage_toAggressor = new Message("<gold>Ваше нападение не удалось");
  public Message MILITARY_TO_BANDIT_tieMessage_toAggressor = new Message("<gold>Ничья, время рейда вышло");
  public Message MILITARY_TO_BANDIT_preparingMessage_toVictim = new Message("<gold>На вас нападает <aggressor>, подготовьтесь как следует");
  public Message MILITARY_TO_BANDIT_winMessage_toVictim = new Message("<gold>Вы отбили нападение <aggressor>!");
  public Message MILITARY_TO_BANDIT_loseMessage_toVictim = new Message("<gold>Вы не смогли отбить нападение <aggressor>!");
  public Message MILITARY_TO_BANDIT_tieMessage_toVictim = new Message("<gold>Ничья");
  public Message BANDIT_TO_BANDIT_preparingMessage_toAggressor = new Message("<gold>Вы нападаете на <victim>, подготовьтесь как следует");
  public Message BANDIT_TO_BANDIT_winMessage_toAggressor = new Message("<gold>Вы выиграли нападение");
  public Message BANDIT_TO_BANDIT_loseMessage_toAggressor = new Message("<gold>Ваше нападение не удалось");
  public Message BANDIT_TO_BANDIT_tieMessage_toAggressor = new Message("<gold>Ничья, время рейда вышло");
  public Message BANDIT_TO_BANDIT_preparingMessage_toVictim = new Message("<gold>На вас нападает <aggressor>, подготовьтесь как следует");
  public Message BANDIT_TO_BANDIT_winMessage_toVictim = new Message("<gold>Вы отбили нападение <aggressor>!");
  public Message BANDIT_TO_BANDIT_loseMessage_toVictim = new Message("<gold>Вы не смогли отбить нападение <aggressor>!");
  public Message BANDIT_TO_BANDIT_tieMessage_toVictim = new Message("<gold>Ничья");
  public Message PEACEFUL_TO_BANDIT_preparingMessage_toAggressor = new Message("<gold>Вы нападаете на <victim>, подготовьтесь как следует");
  public Message PEACEFUL_TO_BANDIT_winMessage_toAggressor = new Message("<gold>Вы выиграли нападение");
  public Message PEACEFUL_TO_BANDIT_loseMessage_toAggressor = new Message("<gold>Ваше нападение не удалось");
  public Message PEACEFUL_TO_BANDIT_tieMessage_toAggressor = new Message("<gold>Ничья, время рейда вышло");
  public Message PEACEFUL_TO_BANDIT_preparingMessage_toVictim = new Message("<gold>На вас нападает <aggressor>, подготовьтесь как следует");
  public Message PEACEFUL_TO_BANDIT_winMessage_toVictim = new Message("<gold>Вы отбили нападение <aggressor>!");
  public Message PEACEFUL_TO_BANDIT_loseMessage_toVictim = new Message("<gold>Вы не смогли отбить нападение <aggressor>!");
  public Message PEACEFUL_TO_BANDIT_tieMessage_toVictim = new Message("<gold>Ничья");
  public Message BANDIT_TO_PEACEFUL_startMessage_toAggressor = new Message("<gold><aggressor> нападает на <victim>!");
  public Message BANDIT_TO_PEACEFUL_startMessage_toVictim = new Message("<gold><aggressor> нападает на <victim>!");
  public Message MILITARY_TO_BANDIT_startMessage_toAggressor = new Message("<gold><aggressor> нападает на <victim>!");
  public Message MILITARY_TO_BANDIT_startMessage_toVictim = new Message("<gold><aggressor> нападает на <victim>!");
  public Message BANDIT_TO_BANDIT_startMessage_toAggressor = new Message("<gold><aggressor> нападает на <victim>!");
  public Message BANDIT_TO_BANDIT_startMessage_toVictim = new Message("<gold><aggressor> нападает на <victim>!");
  public Message PEACEFUL_TO_BANDIT_startMessage_toAggressor = new Message("<gold><aggressor> нападает на <victim>!");
  public Message PEACEFUL_TO_BANDIT_startMessage_toVictim = new Message("<gold><aggressor> нападает на <victim>!");
  public Message raid_started = new Message("<gold>Дождитесь окончания другого рейда");
  public Message you_cant_raid_$name = new Message("<gold>Вы не можете рейдить <yellow><name>");
  public Message shoker_set_successfully = new Message("<gold>Теперь подобные предметы являются шокером");
  public Message $player_arrested_successfully = new Message("<yellow><player> <gold>успешно арестован");
  public Message $player_demobilized_successfully = new Message("<yellow><player> <gold>успешно освобождён из тюрьмы");
  
  public Messages() {
  
  }
  
  public static Messages getInstance() {
    if (instance == null) load();
    return instance;
  }
  
  public static void load() {
    instance = Fractions.yml.load("Messages", Messages.class);
  }
  
  @Override
  public void reload() {
    load();
  }
}
