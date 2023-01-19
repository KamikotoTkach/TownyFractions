package ru.cwcode.fractions.fractions;

import java.util.HashMap;

public class FractionStatistics {
  HashMap<String, Integer> values = new HashMap<>();
  
  public FractionStatistics() {
  }
  
  public void increment(String key) {
    Integer count = values.get(key);
    if (count == null) {
      count = 0;
    }
    count++;
    values.put(key, count);
  }
  
  public int get(String key) {
    Integer count = values.get(key);
    if (count == null) {
      count = 0;
    }
    return count;
  }
  
  public HashMap<String, Integer> getValues() {
    return values;
  }
}
