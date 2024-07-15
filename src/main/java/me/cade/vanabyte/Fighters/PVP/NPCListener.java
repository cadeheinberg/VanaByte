package me.cade.vanabyte.Fighters.PVP;

import me.cade.vanabyte.Fighters.Enums.WeaponType;
import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.Fighters.FighterKitManager;
import me.cade.vanabyte.Fighters.WeaponHolders.Weapon;
import me.cade.vanabyte.Fighters.WeaponHolders.WeaponHolder;
import me.cade.vanabyte.NPCS.RealEntities.MyArmorStand;
import me.cade.vanabyte.Permissions.SafeZone;
import me.cade.vanabyte.VanaByte;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

public class NPCListener implements Listener {

	@EventHandler
	public void onClick(PlayerInteractEntityEvent e) {
		if (e.getHand() == EquipmentSlot.OFF_HAND) {
			return; // off hand packet, ignore.
		}
		WeaponType weaponType = Weapon.getWeaponTypeFromMainHand(e.getPlayer());
		if(weaponType != null && weaponType != WeaponType.UNKNOWN_WEAPON){
			WeaponHolder weaponHolder = Fighter.get(e.getPlayer()).getFighterKitManager().getWeaponHolderWithType(weaponType);
			if(weaponHolder != null){
				weaponHolder.doRightClickEntity(e);
			}
		}
		if(SafeZone.safeZone(e.getRightClicked().getLocation())) {
			if (e.getRightClicked().getType() == EntityType.ARMOR_STAND) {
				handleKitSelection(e.getPlayer(), e.getRightClicked().getLocation().getBlockX());
			} else if (e.getRightClicked().getType() == EntityType.CAT) {
				e.getPlayer().sendMessage(ChatColor.GREEN + "Coming soon!");
				e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_CAT_PURREOW, 8, 1);
			}else if (e.getRightClicked().getType() == EntityType.PIGLIN) {
				e.getPlayer().teleport(VanaByte.anarchyWorldSpawn);
				e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_WOLF_HOWL, 8, 1);
			}
		}
	}
	
	public static void handleKitSelection(Player player, int x){
		for (int i = 0; i < Fighter.getNumberOfKits(); i++) {
			if (MyArmorStand.getLocationOfSelector(i).getBlockX() == x) {
				Fighter fighter = Fighter.fighters.get(player.getUniqueId());
				if (fighter.getFighterKitManager().getUnlockedKit(i) > 0) {
					fighter.getFighterKitManager().giveKitWithID(i);
				} else {
					player.sendMessage(ChatColor.GREEN + "Server in beta, you have unlocked this kit for free!");
					player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 8, 1);
					fighter.getFighterKitManager().setUnlockedKit(i, 1);
					fighter.getFighterPacketHologramsManager().fighterPurchasedKit();
					fighter.getFighterKitManager().giveKitWithID(i);
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
