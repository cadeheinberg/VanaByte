package me.cade.vanabyte.Fighters.PVP;

import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.Fighters.Enums.WeaponType;
import me.cade.vanabyte.Fighters.WeaponHolders.*;
import me.cade.vanabyte.Permissions.SafeZone;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import java.util.UUID;

public class KitListener implements Listener {

	@EventHandler
	public void onRightClick(PlayerInteractEvent e) {
		if (e.getHand() == EquipmentSlot.OFF_HAND) {
			return; // off hand packet, ignore.
		}
		if(e.getPlayer().getGameMode() != GameMode.SURVIVAL){
			return;
		}
		if(Fighter.get(e.getPlayer()) == null){
			return;
		}
		if (SafeZone.safeZone(e.getPlayer().getLocation())) {
			if(e.getClickedBlock() != null){
				if(e.getClickedBlock().getType() == Material.ENCHANTING_TABLE){
					e.setCancelled(true);
					Fighter.get(e.getPlayer()).getGUIManager().openKitLecternGUI(e.getClickedBlock().getLocation());
				}
			}
			return;
		}
		if(e.getItem() == null){
			return;
		}
		WeaponType weaponType = Weapon.getWeaponTypeFromItemStack(e.getItem());
		if(weaponType != null && weaponType != WeaponType.UNKNOWN_WEAPON){
			WeaponHolder weaponHolder = Fighter.get(e.getPlayer()).getFighterKitManager().getWeaponHolderWithType(weaponType);
			if(weaponHolder != null){
				if (e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
					weaponHolder.doLeftClick(e);
				}else if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
					weaponHolder.doRightClick(e);
				}
			}
		}
	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		if (e.getPlayer().getGameMode() != GameMode.SURVIVAL) {
			return;
		}
		e.setCancelled(true);
		if(SafeZone.safeZone(e.getPlayer().getLocation())){
			return;
		}
		WeaponType weaponType = Weapon.getWeaponTypeFromItemStack(e.getItemDrop().getItemStack());
		if(weaponType == null || weaponType == WeaponType.UNKNOWN_WEAPON){
			if(SafeZone.inAnarchy(e.getPlayer().getWorld())){
				e.setCancelled(false);
			}
			return;
		}
		WeaponHolder weaponHolder = Fighter.get(e.getPlayer()).getFighterKitManager().getWeaponHolderWithType(weaponType);
		if(weaponHolder == null){
			if(SafeZone.inAnarchy(e.getPlayer().getWorld())){
				e.setCancelled(false);
			}
			return;
		}
		weaponHolder.doDrop(e);
	}

	//used for getting block hit
	//EntityDamageByEntityEvent is used for getting player hit
	@EventHandler
	public void onProjectileHit(ProjectileHitEvent e) {
		if(e.getHitBlock() == null){
			return;
		}
		if (!(e.getEntity().getShooter() instanceof Player)) {
			return;
		}
		if(SafeZone.safeZone(e.getHitBlock().getLocation())){
			e.setCancelled(true);
			return;
		}
		Player pkiller = (Player) e.getEntity().getShooter();
		WeaponType weaponType = EntityMetadata.getWeaponTypeFromEntity(e.getEntity());
		if(weaponType == null ||
				Fighter.get(pkiller) == null ||
				Fighter.get(pkiller).getFighterKitManager() == null ||
				e.getHitBlock() == null){
			return;
		}
		Fighter.get(pkiller).getFighterKitManager().getWeaponHolderWithType(weaponType).doProjectileHitBlock(e);
	}

	@EventHandler
	public void onProjectileLaunch(ProjectileLaunchEvent e) {
		if (SafeZone.safeZone(e.getLocation())) {
			e.setCancelled(true);
			return;
		}
		if (!(e.getEntity().getShooter() instanceof Player)) {
			return;
		}
		if(!(e.getEntity() instanceof Item)){
			return;
		}
		Player shooter = (Player) e.getEntity().getShooter();
		ItemStack itemStack = ((Item) e.getEntity()).getItemStack();
		WeaponType weaponType = Weapon.getWeaponTypeFromItemStack(itemStack);
		if(weaponType == null || weaponType == WeaponType.UNKNOWN_WEAPON){
			return;
		}
		WeaponHolder weaponHolder = Fighter.get(shooter).getFighterKitManager().getWeaponHolderWithType(weaponType);
		if(weaponHolder == null){
			return;
		}
		weaponHolder.doProjectileLaunch(e);
	}

	@EventHandler
	public void onBowShot(EntityShootBowEvent e) {
		if (SafeZone.safeZone(e.getEntity().getLocation())) {
			e.setCancelled(true);
			return;
		}
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		Player shooter = (Player) e.getEntity();
		if(!(e.getBow() instanceof Item)){
			return;
		}
		ItemStack itemStack = ((Item) e.getEntity()).getItemStack();
		WeaponType weaponType = Weapon.getWeaponTypeFromItemStack(itemStack);
		if(weaponType == null || weaponType == WeaponType.UNKNOWN_WEAPON){
			return;
		}
		WeaponHolder weaponHolder = Fighter.get(shooter).getFighterKitManager().getWeaponHolderWithType(weaponType);
		if(weaponHolder == null){
			return;
		}
		weaponHolder.doBowShootEvent(e);
	}
	@EventHandler
	public void onDismount(EntityDismountEvent e) {
		if (e.getEntity() instanceof Player) {
			for (WeaponHolder weaponHolder : Fighter.get((Player) e.getEntity()).getFighterKitManager().getWeaponHolders()) {
				//try to dismount for every weaponholder being used
				//should only affect those with doDismount overridden like the parachute
				weaponHolder.doDismount(e);
			}
		}
	}

	@EventHandler
	public void onExplodePrime(ExplosionPrimeEvent e) {
		WeaponType weaponType = EntityMetadata.getWeaponTypeFromEntity(e.getEntity());
		if (weaponType == null || weaponType == WeaponType.UNKNOWN_WEAPON) {
			return;
		}
		e.setCancelled(true);
		// Create the explosion and attach UUID
		UUID killerUUID = EntityMetadata.getUUIDFromEntity(e.getEntity());
		if(killerUUID == null){
			return;
		}
		Entity killer = Bukkit.getServer().getEntity(killerUUID);
		if((killer == null) || (!(killer instanceof Player))){
			return;
		}
		Player pKiller = (Player) killer;
		WeaponHolder weaponHolder = Fighter.get(pKiller).getFighterKitManager().getWeaponHolderWithType(weaponType);
		if(weaponHolder == null){
			return;
		}
		weaponHolder.doExplosion(e, pKiller);
	}
}
