package me.cade.vanabyte.Damaging.DamageTracker;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EntityDamageData {
    private final UUID playerId;
    private final List<EntityDamageEntry> entries = new ArrayList<>();

    public EntityDamageData(UUID playerId) {
        this.playerId = playerId;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void addEntry(EntityDamageEntry entry) {
        entries.add(entry);
    }

    public List<EntityDamageEntry> getEntries() {
        return List.copyOf(entries);
    }

    public EntityDamageEntry getLastAttacker() {
        //using last 10 seconds
        long time = System.currentTimeMillis() - (10 * 1000L);

        for (int index = entries.size() - 1; index >= 0; index--) {
            EntityDamageEntry entry = entries.get(index);

            if (entry.getTimestamp() < time) {
                //entry is older than 10 seconds
                entries.remove(index);
                return null;
            }

            if (entry.getAttackerUUID() != null) {
                return entry;
            }
        }
        return null;
    }
}

