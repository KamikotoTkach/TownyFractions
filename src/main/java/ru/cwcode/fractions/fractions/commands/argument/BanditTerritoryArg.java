package ru.cwcode.fractions.fractions.commands.argument;

import org.bukkit.command.CommandSender;
import tkachgeek.commands.command.Argument;
import tkachgeek.townyterritory.TerrAPI;
import tkachgeek.townyterritory.territory.Territory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BanditTerritoryArg extends Argument {
  
  @Override
  public boolean valid(String s) {
    Optional<Territory> territoryByName = TerrAPI.getTerritoryBy(s);
    return territoryByName.isPresent() && territoryByName.get().isBandit();
  }
  
  @Override
  public List<String> completions(CommandSender commandSender) {
    List<String> list = new ArrayList<>();
    for (Territory x : TerrAPI.getTerritories()) {
      if (x.isBandit()) {
        list.add(x.getName());
      }
    }
    return list;
  }
  
  @Override
  public String argumentName() {
    return "бандитская территорий";
  }
}
