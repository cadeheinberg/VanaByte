package me.cade.vanabyte.Fighters;

import me.cade.vanabyte.FighterWeapons.InUseWeapons.WeaponType;
import me.cade.vanabyte.VanaByte;
import org.bukkit.entity.Entity;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.util.List;
import java.util.UUID;

public class EntityMetadata {

    public static void addWeaponTypeToEntity(Entity entity, WeaponType weaponType, UUID uuid){
        entity.setMetadata("WeaponType", new FixedMetadataValue(VanaByte.getInstance(), weaponType.name()));
        entity.setMetadata("WeaponUUID", new FixedMetadataValue(VanaByte.getInstance(), uuid.toString()));
    }

    public static WeaponType getWeaponTypeFromEntity(Entity entity){
        if (entity.hasMetadata("WeaponType")) {
            List<MetadataValue> metadataValues = entity.getMetadata("WeaponType");
            if (metadataValues != null && !metadataValues.isEmpty()) {
                MetadataValue value = metadataValues.get(0);
                return WeaponType.fromName(value.asString()); // Cast the value to string
            }
        }
        return null; // or you can return a default value
    }

    public static UUID getUUIDFromEntity(Entity entity){
        if (entity.hasMetadata("WeaponUUID")) {
            List<MetadataValue> metadataValues = entity.getMetadata("WeaponUUID");
            if (metadataValues != null && !metadataValues.isEmpty()) {
                MetadataValue value = metadataValues.get(0);
                return UUID.fromString(value.asString()); // Convert to UUID
            }
        }
        return null; // or you can return a default value
    }

}
