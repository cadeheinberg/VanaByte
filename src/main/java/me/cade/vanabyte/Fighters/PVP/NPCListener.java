package me.cade.vanabyte.Fighters.PVP;

import me.cade.vanabyte.Fighters.Enums.KitType;
import me.cade.vanabyte.Fighters.Enums.WeaponType;
import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.Fighters.Weapons.Weapon;
import me.cade.vanabyte.Fighters.Weapons.WeaponHolder;
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
		}else{
			WeaponType weaponType = Weapon.getWeaponTypeFromMainHand(e.getPlayer());
			if(weaponType != null){
				WeaponHolder weaponHolder = Fighter.get(e.getPlayer()).getFighterKitManager().getWeaponHolderWithType(weaponType);
				if(weaponHolder != null){
					weaponHolder.doRightClickEntity(e);
				}
			}
		}
	}
	
	public static void handleKitSelection(Player player, int x){
		Fighter fighter = Fighter.fighters.get(player.getUniqueId());
		for(KitType kitType : KitType.values()){
			if(kitType.getSelectorLocation().getBlockX() == x){
				if (fighter.getFighterMYSQLManager().hasUnlockedKitType(kitType)) {
					//player owns this kit
					fighter.getFighterKitManager().setKitType(kitType);
					fighter.getFighterKitManager().giveKit();
				}
				else {
					player.sendMessage(ChatColor.GREEN + "Server in beta, you have unlocked this kit for free!");
					player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 8, 1);
					fighter.getFighterMYSQLManager().setHasUnlockedKitType(kitType);
					fighter.getFighterKitManager().setKitType(kitType);
					fighter.getFighterKitManager().giveKit();
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
			if (e.getHand() == EquipmentSlot.OFF_HAND) {
				e.setCancelled(true);
				return; // off hand packet, ignore.
			}
			WeaponType weaponType = Weapon.getWeaponTypeFromItemStack(e.getPlayer().getInventory().getItemInMainHand());
			WeaponType weaponType2 = Weapon.getWeaponTypeFromItemStack(e.getPlayer().getInventory().getItemInOffHand());
			if(weaponType != null){
				e.setCancelled(true);
				e.getPlayer().sendMessage(ChatColor.RED + "Special items can't leave your inventory!");
				e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, 8, 1);
				return;
			}
			if(weaponType2 != null){
				e.setCancelled(true);
				e.getPlayer().sendMessage(ChatColor.RED + "Special items can't leave your inventory!");
				e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, 8, 1);
				return;
			}
		}
	}

}
