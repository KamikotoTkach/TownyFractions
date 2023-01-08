package clockwork.fractions.storage.config;

import clockwork.fractions.Fractions;
import clockwork.fractions.storage.Rank;
import clockwork.fractions.storage.fractions.ArmyFraction;
import clockwork.fractions.storage.fractions.BanditFraction;
import clockwork.fractions.storage.fractions.FractionInstance;
import clockwork.fractions.storage.fractions.PoliceFraction;
import tkachgeek.config.yaml.YmlConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FractionsStorage extends YmlConfig {
  static FractionsStorage instance;
  
  transient private final List<Rank> defaultPoliceRanks = new ArrayList<Rank>() {{
    add(new Rank("Рядовой").salary(10));
    add(new Rank("Сержант").salary(15));
    add(new Rank("Старшина").salary(20));
    add(new Rank("Прапорщик").salary(25));
    add(new Rank("Лейтенант").salary(30));
    add(new Rank("Капитан").salary(35));
    add(new Rank("Майор").salary(40));
    add(new Rank("Полковник").salary(45));
    add(new Rank("Генерал майор").salary(50));
    add(new Rank("Генерал лейтенант").salary(55));
    add(new Rank("Генерал полковник").salary(60));
    add(new Rank("Министр юстици").salary(65));
  }};
  
  transient private final List<Rank> defaultArmyRanks = new ArrayList<Rank>() {{
    add(new Rank("Рядовой").salary(20));
    add(new Rank("Ефрейтор").salary(22));
    add(new Rank("Сержант").salary(24));
    add(new Rank("Старшина").salary(26));
    add(new Rank("Прапорщик").salary(28));
    add(new Rank("Лейтенант").salary(30));
    add(new Rank("Капитан").salary(32));
    add(new Rank("Майор").salary(34));
    add(new Rank("Полковник").salary(36));
    add(new Rank("Генерал армии").salary(38));
    add(new Rank("Министр обороны").salary(40));
  }};
  
  transient private final List<Rank> defaultBanditRanks = new ArrayList<Rank>() {{
    add(new Rank("Козёл"));
    add(new Rank("Мужик"));
    add(new Rank("Блатной"));
    add(new Rank("Зам.главы"));
    add(new Rank("Глава"));
  }};
  
  List<FractionInstance> fractions = new ArrayList<FractionInstance>() {{
    add(new PoliceFraction().setRanks(defaultPoliceRanks));
    add(new ArmyFraction().setRanks(defaultArmyRanks));
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
      if (fraction.getName().equals(fraction_name)) {
        return true;
      }
    }
    return false;
  }
  public Optional<FractionInstance> getFraction(String fraction_name) {
    for (FractionInstance fraction : fractions) {
      if (fraction.getName().equals(fraction_name)) {
        return Optional.of(fraction);
      }
    }
    return Optional.empty();
  }
}
