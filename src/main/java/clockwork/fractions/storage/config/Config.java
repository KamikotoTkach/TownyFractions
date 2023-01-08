package clockwork.fractions.storage.config;

import clockwork.fractions.Fractions;
import tkachgeek.config.yaml.YmlConfig;

public class Config extends YmlConfig {
  static Config instance;
  
  public Config() {
  }
  
  public static Config getInstance() {
    if (instance == null) load();
    return instance;
  }
  
  public static void load() {
    instance = Fractions.yml.load("Config", Config.class);
  }
}
