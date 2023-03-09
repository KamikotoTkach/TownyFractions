package ru.cwcode.fractions.criminal;

import org.bukkit.entity.Player;

import java.util.UUID;

public class Prisoner {
  private UUID uuid;
  private String name;
  private long time;
  
  public Prisoner() {
  
  }
  
  public Prisoner(Player player, long time) {
    this.uuid = player.getUniqueId();
    this.name = player.getName();
    this.time = time;
  }
  
  public boolean timeExpired() {
    return System.currentTimeMillis() / 1000L > this.time;
  }
  
  public UUID getUUID() {
    return this.uuid;
  }
  
  public String getName() {
    return this.name;
  }
}
