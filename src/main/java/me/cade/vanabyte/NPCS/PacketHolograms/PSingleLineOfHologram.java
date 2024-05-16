package me.cade.vanabyte.NPCS.PacketHolograms;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedDataValue;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class PSingleLineOfHologram {

    private final UUID entityUUid;
    private final int entityId;
    private Location location;
    private String displayText;

    private PSingleLineOfHologram nextLine;

    //constructor for first hologram line
    public PSingleLineOfHologram(Location location, String displayText){
        this.location = location;
        // Could be safer but this is probably fine
        this.entityId = ThreadLocalRandom.current().nextInt();
        this.entityUUid = UUID.randomUUID();
        this.displayText = displayText;
    }

    public void showTo(Player player) {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        try {
            protocolManager.sendServerPacket(player, createAddPacket());
            protocolManager.sendServerPacket(player, createDataPacket());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setLineDisplayText(String text) {
        this.displayText = text;
    }

    public void refreshDisplayText(Player player){
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        try {
            protocolManager.sendServerPacket(player, createDataPacket());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Spawn the Armor Stand in. Does not make invisible, or set displayText.
    private PacketContainer createAddPacket() {
        PacketType type = PacketType.Play.Server.SPAWN_ENTITY;
        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(type);

        StructureModifier<Integer> intMod = packet.getIntegers();
        StructureModifier<EntityType> typeMod = packet.getEntityTypeModifier();
        StructureModifier<UUID> uuidMod = packet.getUUIDs();
        StructureModifier<Double> doubleMod = packet.getDoubles();

        // Write id of entity
        intMod.write(0, this.entityId);

        // Write type of entity
        typeMod.write(0, EntityType.ARMOR_STAND);

        // Write entities UUID
        uuidMod.write(0, this.entityUUid);

        // Write position
        doubleMod.write(0, location.getX());
        doubleMod.write(1, location.getY());
        doubleMod.write(2, location.getZ());

        return packet;
    }

    //Sets the Armor Stand invisible and adds the displayText.
    private PacketContainer createDataPacket() {
        PacketType type = PacketType.Play.Server.ENTITY_METADATA;
        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(type);

        packet.getIntegers().write(0, this.entityId);

        WrappedDataWatcher.Serializer byteSerializer = WrappedDataWatcher.Registry.get(Byte.class);
        WrappedDataWatcher.Serializer chatSerializer = WrappedDataWatcher.Registry.getChatComponentSerializer(true);
        WrappedDataWatcher.Serializer boolSerializer = WrappedDataWatcher.Registry.get(Boolean.class);

        List<WrappedDataValue> dataValues = new ArrayList<>();

        Byte flags = 0x20;
        dataValues.add(new WrappedDataValue(0, byteSerializer, flags));

        Optional<?> optChat = Optional.of(WrappedChatComponent.fromChatMessage(this.displayText)[0].getHandle());
        dataValues.add(new WrappedDataValue(2, chatSerializer, optChat));

        Boolean nameVisible = true;
        dataValues.add(new WrappedDataValue(3, boolSerializer, nameVisible));

        Byte armorStandTypeFlags = 0x10;
        dataValues.add(new WrappedDataValue(15, byteSerializer, armorStandTypeFlags));

        packet.getDataValueCollectionModifier().write(0, dataValues);

        return packet;
    }

    public PSingleLineOfHologram getNextLine(){
        return this.nextLine;
    }

    public void setNextLine(PSingleLineOfHologram nextLine){
        this.nextLine = nextLine;
    }


}
