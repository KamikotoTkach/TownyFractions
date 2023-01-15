package ru.cwcode.fractions.criminal;

import org.bukkit.Location;

public class Prison {
  private String name;
  private Location location;
  
  public Prison() {
  }
  
  public Prison(Location location) {
    this.location = location;
  }
  
  public String getName() {
    return this.name;
  }
  
  public Location getLocation() {
    return this.location;
  }
}
