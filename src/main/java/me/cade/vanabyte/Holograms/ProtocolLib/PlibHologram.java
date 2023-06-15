package me.cade.vanabyte.Holograms.ProtocolLib;

import me.cade.vanabyte.Holograms.Abstraction.AbstractHologram;
import me.cade.vanabyte.Holograms.Abstraction.HologramLine;
import org.bukkit.Location;

public class PlibHologram extends AbstractHologram {
  public PlibHologram(Location location, String name) {
    super(location, name);
  }

  @Override
  protected HologramLine createLine(Location location) {
    return new PlibHologramLine(location);
  }
}
