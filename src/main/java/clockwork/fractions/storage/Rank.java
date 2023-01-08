package clockwork.fractions.storage;

public class Rank {
  private String name;
  private int salary = 0;
  private int priority = 0;
  private Permissions permissions = new Permissions();
  
  public Rank() {
  }
  
  public Rank(String name) {
    this.name = name;
  }
  
  public Rank salary(int salary) {
    this.salary = salary;
    return this;
  }
  
  public Permissions permissions() {
    return permissions;
  }
  
  public String name() {
    return name;
  }
  
  public void name(String name) {
    this.name = name;
  }
  
  public int salary() {
    return salary;
  }
  
  public int priority() {
    return priority;
  }
  
  public void priority(int priority) {
    this.priority = priority;
  }
  
  public void permissions(Permissions permissions) {
    this.permissions = permissions;
  }
}
