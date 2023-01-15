package ru.cwcode.fractions.criminal;

import org.bukkit.Location;

public class Prison {
  private Location location;
  
  public Prison() {
  }
  
  public Prison(Location location) {
    this.location = location;
  }
  
  public Location getLocation() {
    return this.location;
  }
}
