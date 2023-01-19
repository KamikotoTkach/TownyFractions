package ru.cwcode.fractions.fractions;

import java.util.Objects;

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
  
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    
    Rank rank = (Rank) o;
    
    if (salary != rank.salary) return false;
    if (priority != rank.priority) return false;
    if (!Objects.equals(name, rank.name)) return false;
    return Objects.equals(permissions, rank.permissions);
  }
  
  @Override
  public int hashCode() {
    int result = name != null ? name.hashCode() : 0;
    result = 31 * result + salary;
    result = 31 * result + priority;
    result = 31 * result + (permissions != null ? permissions.hashCode() : 0);
    return result;
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
  
  public Rank permissions(Permissions permissions) {
    this.permissions = permissions;
    return this;
  }
}
