package me.cade.vanabyte.NPCS.GUIs;

import me.cade.vanabyte.Damaging.EntityDamage;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class QuestListener implements Listener {

    public static void entityKilledByPlayer(EntityDeathEvent e) {
        //e.getEntity().getKiller().sendMessage("You killed a " + e.getEntity().getType().name() + " with " + e.getDamageSource().getDamageType());
    }
}
