package me.cade.vanabyte.Holograms.Abstraction;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public interface Hologram {

  // We are using String instead of UUIDs for user experience
  String getId();

  int size();

  void addLine(String line) throws InvocationTargetException;

  void setLine(int index, String line);

  String getLine(int index);

  Location getLocation();

  void teleport(Location target);

  void showTo(Player player) throws InvocationTargetException;

  void hideFrom(Player player) throws InvocationTargetException;

  void removeLine(int index) throws InvocationTargetException;
}
