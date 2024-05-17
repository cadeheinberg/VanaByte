package me.cade.vanabyte.NPCS.PacketHolograms;

import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class MyHologramLine {
    private Location location;
    private String displayText;
    private MyHologramLine nextLine;
    private final UUID entityUUid;
    private final int entityId;

    public MyHologramLine(Location location, String displayText) {
        this.location = location;
        this.displayText = displayText;
        this.entityId = ThreadLocalRandom.current().nextInt();
        this.entityUUid = UUID.randomUUID();
    }

    public Location getLocation() {
        return location;
    }

    public String getDisplayText() {
        return displayText;
    }

    public MyHologramLine getNextLine() {
        return nextLine;
    }

    public void setNextLine(MyHologramLine nextLine) {
        this.nextLine = nextLine;
    }

    public void setLineDisplayText(String displayText) {
        this.displayText = displayText;
    }

    public void showTo(Player player) {
        ServerPlayer serverPlayer = ((CraftPlayer) player).getHandle();
        ServerLevel worldServer = serverPlayer.serverLevel();

        // Create the armor stand
        ArmorStand armorStand = new ArmorStand(EntityType.ARMOR_STAND, worldServer);
        armorStand.setUUID(this.entityUUid);
        armorStand.setId(this.entityId);
        armorStand.setPos(location.getX(), location.getY(), location.getZ());
        armorStand.setCustomName(Component.literal(displayText));
        armorStand.setCustomNameVisible(true);
        armorStand.setInvisible(true);
        armorStand.setNoGravity(true);
        armorStand.setMarker(true);
        armorStand.setInvulnerable(true);

        SynchedEntityData dataWatcher = armorStand.getEntityData();
        dataWatcher.set(new EntityDataAccessor<>(0, EntityDataSerializers.BYTE), (byte) 0x20); // invisible

        // Send packets to the player
        ClientboundAddEntityPacket spawnPacket = new ClientboundAddEntityPacket(armorStand);
        List<SynchedEntityData.DataValue<?>> dataValues = dataWatcher.getNonDefaultValues();
        ClientboundSetEntityDataPacket dataPacket = new ClientboundSetEntityDataPacket(armorStand.getId(), dataValues);

        serverPlayer.connection.send(spawnPacket);
        serverPlayer.connection.send(dataPacket);
    }

    public void hideFrom(Player player) {
        ServerPlayer serverPlayer = ((CraftPlayer) player).getHandle();
        ClientboundRemoveEntitiesPacket packet = new ClientboundRemoveEntitiesPacket(entityId);
        serverPlayer.connection.send(packet);
    }
}
