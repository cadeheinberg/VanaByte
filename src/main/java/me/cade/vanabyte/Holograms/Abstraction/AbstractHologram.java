package me.cade.vanabyte.Holograms.Abstraction;

import com.comphenix.protocol.PacketType;
import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractHologram implements Hologram {

  private static final double LINE_SPACE = 0.25;

  private final List<HologramLine> hologramLines = new ArrayList<>();
  private final String name;
  private Location location;

  public AbstractHologram(Location location, String name) {
    this.name = name;
    this.location = location;
  }

  private Location getRelativeLocationForIndex(int index) {
    return location.clone().add(0, -LINE_SPACE * index, 0);
  }

  @Override
  public String getId() {
    return name;
  }

  @Override
  public int size() {
    return hologramLines.size();
  }

  @Override
  public void addLine(String line) throws InvocationTargetException {
    int nextIndex = size();
    Location lineLocation = getRelativeLocationForIndex(nextIndex);
    HologramLine hologramLine = createLine(lineLocation);
    hologramLine.setText(line);
    hologramLines.add(hologramLine);
//    for (Player p : Bukkit.getOnlinePlayers()){
//      hologramLine.showTo(p);
//    }
  }

  @Override
  public void setLine(int index, String line) {
    Preconditions.checkArgument(index < size());
    HologramLine hologramLine = hologramLines.get(index);
    hologramLine.setText(line);
  }

  @Override
  public String getLine(int index) {
    if (index >= size()){
      return null;
    }
    return hologramLines.get(index).getText();
  }

  @Override
  public Location getLocation() {
    return location;
  }

  @Override
  public void teleport(Location target) {
    this.location = target;
    for(int index = 0; index < size(); index++) {
      Location lineLoc = getRelativeLocationForIndex(index);
      HologramLine hologramLine = hologramLines.get(index);
      hologramLine.teleport(lineLoc);
    }
  }

  @Override
  public void showTo(Player player) throws InvocationTargetException {
    for (HologramLine line : hologramLines) {
      line.showTo(player);
    }
  }

  @Override
  public void hideFrom(Player player) throws InvocationTargetException {
    for (HologramLine line : hologramLines) {
      line.hideFrom(player);
    }
  }

  @Override
  public void removeLine(int index) throws InvocationTargetException {
    Preconditions.checkArgument(index < size());
    HologramLine line = hologramLines.remove(index);
    for (Player p : Bukkit.getOnlinePlayers()){
      line.hideFrom(p);
    }
    teleport(this.location);
  }

  protected abstract HologramLine createLine(Location location);
}
