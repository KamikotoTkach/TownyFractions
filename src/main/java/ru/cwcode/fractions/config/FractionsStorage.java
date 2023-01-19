package ru.cwcode.fractions.config;

import ru.cwcode.fractions.Fractions;
import ru.cwcode.fractions.fractions.*;
import tkachgeek.config.base.Reloadable;
import tkachgeek.config.yaml.YmlConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FractionsStorage extends YmlConfig implements Reloadable {
  static FractionsStorage instance;
  
  transient private final List<Rank> defaultPoliceRanks = new ArrayList<>() {{
    add(new Rank("Рядовой").salary(10));
    add(new Rank("Сержант").salary(15));
    add(new Rank("Старшина").salary(20));
    add(new Rank("Прапорщик").salary(25));
    add(new Rank("Лейтенант").salary(30));
    add(new Rank("Капитан").salary(35));
    add(new Rank("Майор").salary(40).permissions(new Permissions().setCanChangeMembers(true).setCanRaid(true)));
    add(new Rank("Полковник").salary(45).permissions(new Permissions().setCanChangeMembers(true).setCanRaid(true)));
    add(new Rank("Генерал майор").salary(50).permissions(new Permissions().setCanChangeMembers(true).setCanRaid(true)));
    add(new Rank("Генерал лейтенант").salary(55).permissions(new Permissions().setCanChangeMembers(true).setCanRaid(true)));
    add(new Rank("Генерал полковник").salary(60).permissions(new Permissions().setCanChangeMembers(true).setCanRaid(true)));
    add(new Rank("Министр юстици").salary(65).permissions(new Permissions().setCanChangeMembers(true).setCanRaid(true)));
  }};
  
  transient private final List<Rank> defaultArmyRanks = new ArrayList<>() {{
    add(new Rank("Рядовой").salary(20));
    add(new Rank("Ефрейтор").salary(22));
    add(new Rank("Сержант").salary(24));
    add(new Rank("Старшина").salary(26));
    add(new Rank("Прапорщик").salary(28));
    add(new Rank("Лейтенант").salary(30));
    add(new Rank("Капитан").salary(32));
    add(new Rank("Майор").salary(34).permissions(new Permissions().setCanChangeMembers(true).setCanRaid(true)));
    add(new Rank("Полковник").salary(36).permissions(new Permissions().setCanChangeMembers(true).setCanRaid(true)));
    add(new Rank("Генерал армии").salary(38).permissions(new Permissions().setCanChangeMembers(true).setCanRaid(true)));
    add(new Rank("Министр обороны").salary(40).permissions(new Permissions().setCanChangeMembers(true).setCanRaid(true)));
  }};
  
  transient private final List<Rank> defaultBanditRanks = new ArrayList<>() {{
    add(new Rank("Козёл"));
    add(new Rank("Мужик"));
    add(new Rank("Блатной"));
    add(new Rank("Зам.главы").permissions(new Permissions().setCanChangeMembers(true).setCanRaid(true)));
    add(new Rank("Глава").permissions(new Permissions().setCanChangeMembers(true).setCanRaid(true)));
  }};
  
  List<FractionInstance> fractions = new ArrayList<>() {{
    add(new PoliceFraction().setRanks(defaultPoliceRanks));
    add(new MilitaryFraction().setRanks(defaultArmyRanks));
    add(new BanditFraction().setRanks(defaultBanditRanks));
  }};
  
  public FractionsStorage() {
  }
  
  public static FractionsStorage getInstance() {
    if (instance == null) load();
    return instance;
  }
  
  public static void load() {
    instance = Fractions.yml.load("FractionsStorage", FractionsStorage.class);
  }
  
  public List<FractionInstance> getFractions() {
    return fractions;
  }
  
  public boolean hasFraction(String fraction_name) {
    for (FractionInstance fraction : fractions) {
      if (fraction.name().equals(fraction_name)) {
        return true;
      }
    }
    return false;
  }
  
  public Optional<FractionInstance> getFraction(String fraction_name) {
    for (FractionInstance fraction : fractions) {
      if (fraction.name().equals(fraction_name)) {
        return Optional.of(fraction);
      }
    }
    return Optional.empty();
  }
  
  @Override
  public void reload() {
    load();
  }
}
