package me.cade.vanabyte.NPCS;

import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.Fighters.FighterKitManager;
import me.cade.vanabyte.FighterWeapons.InUseWeapons.W5_SumoStick;
import me.cade.vanabyte.NPCS.RealEntities.ArmorStand;
import me.cade.vanabyte.Permissions.SafeZone;
import me.cade.vanabyte.VanaByte;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

public class NPCListener implements Listener {

	@EventHandler
	public void onClick(PlayerInteractEntityEvent e) {
		if(e.getRightClicked().getType() == EntityType.ITEM_FRAME || e.getRightClicked().getType() == EntityType.GLOW_ITEM_FRAME || e.getRightClicked().getType() == EntityType.ITEM_DISPLAY){
			if(e.getPlayer().getInventory().getItemInMainHand() != null && FighterKitManager.hasNameOfWeapon(e.getPlayer().getInventory().getItemInMainHand())){
				e.setCancelled(true);
				e.getPlayer().sendMessage(ChatColor.RED + "Special items can't leave your inventory!");
				((Player) e.getPlayer()).playSound(e.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, 8, 1);
			}else if(e.getPlayer().getInventory().getItemInOffHand() != null && FighterKitManager.hasNameOfWeapon(e.getPlayer().getInventory().getItemInOffHand())){
				e.setCancelled(true);
				e.getPlayer().sendMessage(ChatColor.RED + "Special items can't leave your inventory!");
				((Player) e.getPlayer()).playSound(e.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, 8, 1);
			}
		}
		if (e.getHand() == EquipmentSlot.OFF_HAND) {
			return; // off hand packet, ignore.
		}
		if(e.getPlayer() == null){
			return;
		}
		if(SafeZone.safeZone(e.getRightClicked().getLocation())) {
			if (e.getRightClicked().getType() == EntityType.ARMOR_STAND) {
				handleKitSelection(e.getPlayer(), e.getRightClicked().getLocation().getBlockX());
				return;
			} else if (e.getRightClicked().getType() == EntityType.CAT) {
				e.getPlayer().sendMessage(ChatColor.GREEN + "Coming soon!");
				e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_CAT_PURREOW, 8, 1);
				return;
			}else if (e.getRightClicked().getType() == EntityType.IRON_GOLEM) {
				e.getPlayer().teleport(VanaByte.secondWorldSpawn);
				return;
			} else if (e.getRightClicked().getType() == EntityType.SNOWMAN) {
				//if you are here, you are trying to set upgrades
				//keep in mind, a really high cooldown upgrade will be 100 - 99999 and turn negative
				//this will cause errors
//				Fighter.get(e.getPlayer()).getFighterKitManager().setKitUpgradesUsingIDAndOffset(Fighter.get(e.getPlayer()).getKitID(), 0, Fighter.get(e.getPlayer()).getFighterKitManager().getKitUpgradesUsingIDAndOffset(Fighter.get(e.getPlayer()).getKitID(), 0) + 10);
//				Fighter.get(e.getPlayer()).getFighterKitManager().setKitUpgradesUsingIDAndOffset(Fighter.get(e.getPlayer()).getKitID(), 1, Fighter.get(e.getPlayer()).getFighterKitManager().getKitUpgradesUsingIDAndOffset(Fighter.get(e.getPlayer()).getKitID(), 1) + 10);
//				Fighter.get(e.getPlayer()).getFighterKitManager().setKitUpgradesUsingIDAndOffset(Fighter.get(e.getPlayer()).getKitID(), 2, Fighter.get(e.getPlayer()).getFighterKitManager().getKitUpgradesUsingIDAndOffset(Fighter.get(e.getPlayer()).getKitID(), 2) + 10);
//				Fighter.get(e.getPlayer()).getFighterKitManager().setKitUpgradesUsingIDAndOffset(Fighter.get(e.getPlayer()).getKitID(), 3, Fighter.get(e.getPlayer()).getFighterKitManager().getKitUpgradesUsingIDAndOffset(Fighter.get(e.getPlayer()).getKitID(), 3) + 40);
//				Fighter.get(e.getPlayer()).getFighterKitManager().setKitUpgradesUsingIDAndOffset(Fighter.get(e.getPlayer()).getKitID(), 4, Fighter.get(e.getPlayer()).getFighterKitManager().getKitUpgradesUsingIDAndOffset(Fighter.get(e.getPlayer()).getKitID(), 4) + 1);
//				Fighter.get(e.getPlayer()).getFighterKitManager().setKitUpgradesUsingIDAndOffset(Fighter.get(e.getPlayer()).getKitID(), 5, Fighter.get(e.getPlayer()).getFighterKitManager().getKitUpgradesUsingIDAndOffset(Fighter.get(e.getPlayer()).getKitID(), 5) + 1);
//				e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 8, 1);
//				Fighter.get(e.getPlayer()).giveKit();
				return;
			}
			return;
		}
	    if (e.getRightClicked() instanceof LivingEntity) {
			if(Fighter.get(e.getPlayer()).getFKit().getSpecificSimilarWeaponHolderInHands(W5_SumoStick.class) != null){
				((W5_SumoStick)Fighter.get(e.getPlayer()).getFKit().getSpecificSimilarWeaponHolderInHands(W5_SumoStick.class)).doPickUp((LivingEntity) e.getRightClicked());
			}
	    }
	}
	
	public static void handleKitSelection(Player player, int x){
		for (int i = 0; i < Fighter.getNumberOfKits(); i++) {
			if (ArmorStand.getLocationOfSelector(i).getBlockX() == x) {
				Fighter fighter = Fighter.fighters.get(player.getUniqueId());
				if (fighter.getFighterKitManager().getUnlockedKit(i) > 0) {
					fighter.getFighterKitManager().giveKitWithID(i);
				} else {
					player.sendMessage(ChatColor.GREEN + "Server in beta, you have unlocked this kit for free!");
					player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 8, 1);
					fighter.getFighterKitManager().setUnlockedKit(i, 1);
					fighter.getFighterHologramManager().refreshKitHolograms(i);
				}
			}
		}
	}

	@EventHandler
	public void onGrabArmorStand(PlayerArmorStandManipulateEvent e) {
		if (e.getPlayer().getGameMode() == GameMode.CREATIVE) {
			return;
		}
		if (SafeZone.inHub(e.getPlayer().getWorld())) {
			e.setCancelled(true);
			if (e.getRightClicked().getType() == EntityType.ARMOR_STAND) {
				handleKitSelection(e.getPlayer(), e.getRightClicked().getLocation().getBlockX());
			}
		} else{
			if(e.getPlayer().getInventory().getItemInMainHand() != null && FighterKitManager.hasNameOfWeapon(e.getPlayer().getInventory().getItemInMainHand())){
				e.setCancelled(true);
				e.getPlayer().sendMessage(ChatColor.RED + "Special items can't leave your inventory!");
				((Player) e.getPlayer()).playSound(e.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, 8, 1);
			}else if(e.getPlayer().getInventory().getItemInOffHand() != null && FighterKitManager.hasNameOfWeapon(e.getPlayer().getInventory().getItemInOffHand())){
				e.setCancelled(true);
				e.getPlayer().sendMessage(ChatColor.RED + "Special items can't leave your inventory!");
				((Player) e.getPlayer()).playSound(e.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, 8, 1);
			}
		}
	}

}
