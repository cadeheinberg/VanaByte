package me.cade.vanabyte.NPCS.GUIs;

import me.cade.vanabyte.Damaging.DamageTracker.EntityDamageEntry;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class QuestListener implements Listener {

    public static void entityKilledByPlayer(EntityDamageEntry e) {
        Bukkit.getServer().getPlayer(e.getAttackerUUID()).sendMessage("quests: you killed " + e.getVictimType() + " with " + e.getWeaponType().getName());
    }
}
