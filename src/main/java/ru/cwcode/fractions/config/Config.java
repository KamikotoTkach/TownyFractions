package ru.cwcode.fractions.config;

import ru.cwcode.fractions.Fractions;
import tkachgeek.config.yaml.YmlConfig;

public class Config extends YmlConfig {
  static Config instance;
  
  public boolean peacefulWipe = false;
  
  public Config() {
  }
  
  public static Config getInstance() {
    if (instance == null) load();
    return instance;
  }
  
  public static void load() {
    instance = Fractions.yml.load("Config", Config.class);
  }
  
  public boolean isPeacefulWipe() {
    return peacefulWipe;
  }
}
