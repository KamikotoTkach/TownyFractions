package ru.cwcode.fractions.config;

import ru.cwcode.fractions.Fractions;
import tkachgeek.config.yaml.YmlConfig;

import java.time.Duration;

public class Config extends YmlConfig {
  static Config instance;
  
  boolean peacefulWipe = false;
  long timeToSalary = Duration.ofDays(7).toMillis();
  
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
  
  public long getTimeToSalary() {
    return timeToSalary;
  }
}
