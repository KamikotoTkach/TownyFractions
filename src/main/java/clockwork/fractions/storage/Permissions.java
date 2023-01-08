package clockwork.fractions.storage;

public class Permissions {
  private boolean canChangeMembers = false; //изменение состава
  private boolean canChangeRank = false; //изменение ранга
  private boolean canRaid = false; //штурм
  
  public Permissions() {
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
