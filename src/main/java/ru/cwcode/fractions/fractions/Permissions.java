package ru.cwcode.fractions.fractions;

public class Permissions {
  private boolean canChangeMembers = false; //изменение состава
  private boolean canChangeRank = false; //изменение ранга
  private boolean canRaid = false; //штурм
  
  public Permissions() {
  }
  
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    
    Permissions that = (Permissions) o;
    
    if (canChangeMembers != that.canChangeMembers) return false;
    if (canChangeRank != that.canChangeRank) return false;
    return canRaid == that.canRaid;
  }
  
  @Override
  public int hashCode() {
    int result = (canChangeMembers ? 1 : 0);
    result = 31 * result + (canChangeRank ? 1 : 0);
    result = 31 * result + (canRaid ? 1 : 0);
    return result;
  }
  
  public boolean canChangeMembers() {
    return canChangeMembers;
  }
  
  public boolean canChangeRank() {
    return canChangeRank;
  }
  
  public boolean canRaid() {
    return canRaid;
  }
  
  public Permissions setCanChangeMembers(boolean value) {
    this.canChangeMembers = value;
    return this;
  }
  
  public Permissions setCanChangeRank(boolean value) {
    this.canChangeRank = value;
    return this;
  }
  
  public Permissions setCanRaid(boolean value) {
    this.canRaid = value;
    return this;
  }
}
