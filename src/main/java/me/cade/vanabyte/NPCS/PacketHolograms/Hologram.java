package me.cade.vanabyte.NPCS.PacketHolograms;

import me.cade.vanabyte.VanaByte;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import java.util.Objects;
import java.util.UUID;

public class Hologram {

    private final UUID entityUUid;
    private final Location location;
    private String displayText;

    public Hologram(Location location, boolean visibleToAll) {
        this.location = location;
        TextDisplay display = (TextDisplay) location.getWorld().spawnEntity(location, EntityType.TEXT_DISPLAY);
        display.setVisibleByDefault(true);
        display.setSeeThrough(false);
        display.setShadowed(false);
        //display.setBackgroundColor(Color.WHITE);
        display.setBillboard(Display.Billboard.CENTER);
        this.entityUUid = display.getUniqueId();
    }

    public Location getLocation() {
        return location;
    }

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
        //int max = 7 * Arrays.stream(displayText.split("\n")).mapToInt(String::length).max().orElse(0);
        //((TextDisplay) Objects.requireNonNull(Bukkit.getServer().getEntity(entityUUid))).setLineWidth(max);
        ((TextDisplay) Objects.requireNonNull(Bukkit.getServer().getEntity(entityUUid))).setText(displayText);
    }

    public void showTo(Player player) {
        player.showEntity(VanaByte.getVanaBytePlugin(), Objects.requireNonNull(Bukkit.getServer().getEntity(entityUUid)));
    }

    public void hideFrom(Player player) {

    }

    public UUID getDisplayUUID(){
        return this.entityUUid;
    }

    public void removeFromServer(){
        ((TextDisplay) Objects.requireNonNull(Bukkit.getServer().getEntity(entityUUid))).remove();
    }
}
