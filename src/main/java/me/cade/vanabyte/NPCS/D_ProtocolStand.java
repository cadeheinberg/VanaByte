package me.cade.vanabyte.NPCS;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedDataWatcher.WrappedDataWatcherObject;
import me.cade.vanabyte.VanaByte;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

public class D_ProtocolStand {

	private static int entityIDCounter = -2;

	private int entityID;

	private Player owner;

	private String name;

	public D_ProtocolStand(String name, Location location, Player player) {
		ProtocolManager manager = VanaByte.getProtocolManager();
		PacketContainer packet = manager.createPacket(PacketType.Play.Server.SPAWN_ENTITY);
		// Entity ID
		int entityID = entityIDCounter;
		packet.getIntegers().write(0, entityID);
		// Entity Type
		// packet.getIntegers().write(6, 78);
		packet.getEntityTypeModifier().write(0, EntityType.ARMOR_STAND);
		// Set optional velocity (/8000)
		packet.getIntegers().write(1, 1);
		// Set yaw pitch
		packet.getIntegers().write(4, 0);
		packet.getIntegers().write(5, 0);
		// Set location
		packet.getDoubles().write(0, location.getX());
		packet.getDoubles().write(1, location.getY());
		packet.getDoubles().write(2, location.getZ());
		// Set UUID
		UUID randomUUID = UUID.randomUUID();
		packet.getUUIDs().write(0, randomUUID);
		if (player != null) {
			try {
				manager.sendServerPacket(player, packet);
			} catch (Exception e) {
				throw new RuntimeException("Cannot send packet " + packet, e);
			}
		} else {
			try {
				manager.broadcastServerPacket(packet);
			} catch (Exception e) {
				throw new RuntimeException("Cannot send packet " + packet, e);
			}
		}
		this.owner = player;
		this.entityID = entityID;
		entityIDCounter--;
		this.setName(name);
	}

	private void setName(String name) {
		ProtocolManager manager = VanaByte.getProtocolManager();
		PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);
		WrappedDataWatcher metadata = new WrappedDataWatcher();

		Optional<?> opt = Optional.of(WrappedChatComponent.fromChatMessage(name)[0].getHandle());
		metadata.setObject(
				new WrappedDataWatcherObject(2, WrappedDataWatcher.Registry.getChatComponentSerializer(true)), opt); // custom
																														// name
		metadata.setObject(new WrappedDataWatcherObject(3, WrappedDataWatcher.Registry.get(Boolean.class)), true); // custom
																													// name
																													// visible
		metadata.setObject(new WrappedDataWatcherObject(0, WrappedDataWatcher.Registry.get(Byte.class)), (byte) 0x20); // invis
		metadata.setObject(new WrappedDataWatcherObject(5, WrappedDataWatcher.Registry.get(Boolean.class)), true); // no
																													// gravity
//          metadata.setObject(new WrappedDataWatcherObject(11, WrappedDataWatcher.Registry.get(Byte.class)), (byte) (0x01 | 0x08 | 0x10)); //isSmall, noBasePlate, set Marker

		packet.getIntegers().write(0, this.entityID);
		packet.getWatchableCollectionModifier().write(0, metadata.getWatchableObjects());
		if (this.owner != null) {
			try {
				manager.sendServerPacket(this.owner, packet);
			} catch (Exception e) {
				throw new RuntimeException("Cannot send packet " + packet, e);
			}
		} else {
			try {
				VanaByte.getProtocolManager().broadcastServerPacket(packet);
			} catch (Exception e) {
				throw new RuntimeException("Cannot send packet " + packet, e);
			}
		}
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

}
