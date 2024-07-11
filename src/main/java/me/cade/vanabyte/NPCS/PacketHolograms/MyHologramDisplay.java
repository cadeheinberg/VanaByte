package me.cade.vanabyte.NPCS.PacketHolograms;

import me.cade.vanabyte.VanaByte;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import java.util.Objects;
import java.util.UUID;

public class MyHologramDisplay {

    private Location location;
    private String displayText;
    private UUID entityUUid;
    private int entityId;

    public MyHologramDisplay(Location location, String displayText, boolean visibleToAll) {
        this.location = location;
        this.displayText = displayText;
        TextDisplay display = (TextDisplay) location.getWorld().spawnEntity(location, EntityType.TEXT_DISPLAY);
        display.setVisibleByDefault(visibleToAll);
        display.setText(displayText);
        this.entityId = display.getEntityId();
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
    }

    public void showTo(Player player) {
        player.sendMessage("trying to show you hologram");
        player.showEntity(VanaByte.getVanaBytePlugin(), Objects.requireNonNull(Bukkit.getServer().getEntity(entityUUid)));
    }

    public void hideFrom(Player player) {

    }

    public UUID getDisplayUUID(){
        return this.entityUUid;
    }
}
