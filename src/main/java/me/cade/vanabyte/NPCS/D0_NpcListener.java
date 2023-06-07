package me.cade.vanabyte.NPCS;

import me.cade.vanabyte.Fighter;
import me.cade.vanabyte.SafeZone;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

public class D0_NpcListener implements Listener {

	@EventHandler
	public void onClick(PlayerInteractAtEntityEvent e) {
		if (e.getHand() == EquipmentSlot.OFF_HAND) {
			return; // off hand packet, ignore.
		}
		if(SafeZone.safeZone(e.getRightClicked().getLocation())) {
			if (e.getRightClicked().getType() == EntityType.ARMOR_STAND) {
				handleKitSelection(e.getPlayer(), e.getRightClicked().getLocation().getBlockX());
				return;
			}
			return;
		}
	    if (e.getRightClicked() instanceof LivingEntity) {
	    	Fighter.getFighterFKit(e.getPlayer()).doPickUp((LivingEntity) e.getRightClicked());
	    	return;
	    }
		return;
	}
	
	public static void handleKitSelection(Player player, int x){
		for (int i = 0; i < Fighter.getNumberOfKits(); i++) {
			if (D_SpawnKitSelectors.getLocationOfSelector(i).getBlockX() == x) {
				Fighter fighter = Fighter.fighters.get(player.getUniqueId());
				if (fighter.getUnlockedKit(i) > 0) {
					fighter.giveKitWithID(i);
				} else {
					player.sendMessage(ChatColor.RED + "You do not own this kit");
					player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 8, 1);
				}
			}
		}
	}

	@EventHandler
	public void onGrabArmorStand(PlayerArmorStandManipulateEvent e) {
		if (e.getPlayer().getGameMode() == GameMode.CREATIVE) {
			return;
		}
		e.setCancelled(true);
	}

}
