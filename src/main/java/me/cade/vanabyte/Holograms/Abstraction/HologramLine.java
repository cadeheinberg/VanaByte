package me.cade.vanabyte.Holograms.Abstraction;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public interface HologramLine {

  String getText();

  void showTo(Player player) throws InvocationTargetException;

  void hideFrom(Player player) throws InvocationTargetException;

  void teleport(Location location);

  void setText(String text);

}
