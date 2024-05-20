package me.cade.vanabyte.Damaging.DamageTracker;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class EntityDamageManager {
    private final Map<UUID, EntityDamageData> data = new ConcurrentHashMap<>();

    public EntityDamageData getOrCreate(UUID entityUUID) {
        return data.computeIfAbsent(entityUUID, EntityDamageData::new);
    }

    public EntityDamageData get(UUID entityUUID) {
        return data.get(entityUUID);
    }

//    public EntityDamageData getOrDummy(UUID entityUUID) {
//        return data.getOrDefault(entityUUID, new EntityDamageData(entityUUID));
//    }

    public void remove(UUID entityUUID) {
        data.remove(entityUUID);
    }

    public void clear() {
        data.clear();
    }

    public Map<UUID, EntityDamageData> getData() {
        return Map.copyOf(data);
    }

    public void register(CustomDamageWrapper event) {
        Entity entity = event.getEvent().getEntity();

        if (!(entity instanceof LivingEntity livingEntity)) {
            return;
        }

        EntityDamageData damageData = getOrCreate(livingEntity.getUniqueId());
        damageData.addEntry(new EntityDamageEntry(event));
    }
}
