package me.cade.vanabyte.NPCS.GUIs;

import org.bukkit.entity.Player;

public class KitUpgradeHandler {

    public static void handleKitUpgradeIncreaseMeleeDamage(Player player, int kitID){
        player.sendMessage("trying to upgrade melee for kitID " + kitID);
    }
}
