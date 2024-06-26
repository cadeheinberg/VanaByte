package me.cade.vanabyte.Fighters;

import me.cade.vanabyte.Damaging.CreateExplosion;
import me.cade.vanabyte.FighterWeapons.InUseWeapons.*;
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

import java.util.UUID;

public class KitListener implements Listener {

	@EventHandler
	public void onRightClick(PlayerInteractEvent e) {
		if(e.getPlayer().getGameMode() != GameMode.SURVIVAL){
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
		if (e.getHand() == EquipmentSlot.OFF_HAND) {
			return; // off hand packet, ignore.
		}
		if(Fighter.get(e.getPlayer()) == null){
			return;
		}
		if(Fighter.get(e.getPlayer()).getFKit() == null){
			return;
		}
		if (!(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
			if (e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
				if (e.getPlayer().getPassengers() != null && e.getPlayer().getPassengers().size() > 1) {
					if(Fighter.get(e.getPlayer()).getFKit().getWeaponHolderWithType(WeaponType.SUMO_STICK) != null) {
						((W5_SumoStick) Fighter.get(e.getPlayer()).getFKit().getWeaponHolderWithType(WeaponType.SUMO_STICK)).doThrow(e.getPlayer(), (LivingEntity) e.getPlayer().getPassengers().get(0));
						return;
					}
				}
			}
			return;
		}
		if (e.getItem() == null) {
			return;
		}
		WeaponType weaponType = Weapon.getWeaponTypeFromItemStack(e.getItem());
		if(weaponType == null){
			return;
		}
		WeaponHolder weaponHolder = Fighter.get(e.getPlayer()).getFKit().getWeaponHolderWithType(weaponType);
		if(weaponHolder != null){
			weaponHolder.doRightClick();
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
		if(e.getItemDrop() == null) {
			return;
		}
		if(e.getItemDrop().getItemStack() == null){
			return;
		}
		if(Fighter.get(e.getPlayer()) == null){
			return;
		}
		if(Fighter.get(e.getPlayer()).getFKit() == null){
			return;
		}
		WeaponType weaponType = Weapon.getWeaponTypeFromItemStack(e.getItemDrop().getItemStack());
		if(weaponType == null){
			if(SafeZone.inAnarchy(e.getPlayer().getWorld())){
				//The player is trying to drop the item in the survival world
				e.setCancelled(false);
			}
			return;
		}
		WeaponHolder weaponHolder = Fighter.get(e.getPlayer()).getFKit().getWeaponHolderWithType(weaponType);
		if(weaponHolder == null){
			if(SafeZone.inAnarchy(e.getPlayer().getWorld())){
				//The player is trying to drop the item in the survival world
				e.setCancelled(false);
			}
			return;
		}
		weaponHolder.doDrop();
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
				Fighter.get(pkiller).getFKit() == null ||
				e.getHitBlock() == null){
			return;
		}
		Fighter.get(pkiller).getFKit().getWeaponHolderWithType(weaponType).doProjectileHitBlock(e);
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
		Player pkiller = (Player) e.getEntity().getShooter();
		if (e.getEntity().getType() == EntityType.TRIDENT) {
			WeaponType weaponType = Weapon.getWeaponTypeFromItemStack(((Trident) e.getEntity()).getItem());
			if(weaponType == null ||
					Fighter.get(pkiller) == null ||
					Fighter.get(pkiller).getFKit() == null){
				return;
			}
			FighterKit fKit = Fighter.get((Player) e.getEntity().getShooter()).getFKit();
			WeaponHolder weaponHolder = fKit.getWeaponHolderWithType(weaponType);
			if(weaponHolder == null){
				return;
			}
			if(weaponType == WeaponType.IGORS_TRIDENT){
				if(!((W4_IgorsTrident) weaponHolder).doThrowTrident((Trident) e.getEntity())){
					//Set canceled if there is a cooldown
					e.setCancelled(true);
				}
			}
		}
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
		Player pkiller = (Player) e.getEntity();
		if (e.getProjectile().getType() == EntityType.ARROW) {
			WeaponType weaponType = Weapon.getWeaponTypeFromItemStack(e.getBow());
			if(weaponType == null ||
					Fighter.get(pkiller) == null ||
					Fighter.get(pkiller).getFKit() == null){
				return;
			}
			FighterKit fKit = Fighter.get((Player) e.getEntity()).getFKit();
			WeaponHolder weaponHolder = fKit.getWeaponHolderWithType(weaponType);
			if(weaponHolder == null){
				return;
			}
			if(weaponHolder.getWeapon().getWeaponType() == WeaponType.GOBLIN_BOW){
				if(!((W3_GoblinBow) weaponHolder).doArrowShoot((Arrow) e.getProjectile(), e.getForce())){
					//Set canceled if there is a cooldown
					e.setCancelled(true);
				}
			}
		}
	}
	@EventHandler
	public void onDismount(EntityDismountEvent e) {
		if (e.getEntity() instanceof Player) {
			if (e.getDismounted().getType() == EntityType.CHICKEN) {
				Fighter.get((Player) e.getEntity()).fighterDismountParachute();
			}
		}
	}

	@EventHandler
	public void onExplode(EntityExplodeEvent e) {
		if(SafeZone.inHub(e.getLocation().getWorld())){
			e.blockList().clear();
			return;
		}
	}

	@EventHandler
	public void onExplodePrime(ExplosionPrimeEvent e) {
		if(!(e.getEntity() instanceof TNTPrimed)){
			return;
		}
		if (EntityMetadata.getWeaponTypeFromEntity(e.getEntity()) == null) {
			return;
		}
		if (EntityMetadata.getWeaponTypeFromEntity(e.getEntity()) == WeaponType.UNKNOWN_WEAPON) {
			return;
		}
		e.setCancelled(true);
		// Create the explosion and attach UUID
		UUID killerUUID = EntityMetadata.getUUIDFromEntity(e.getEntity());
		if(killerUUID == null){
			return;
		}
		WeaponType weaponType = EntityMetadata.getWeaponTypeFromEntity(e.getEntity());
		if(weaponType == null || weaponType == WeaponType.UNKNOWN_WEAPON){
			return;
		}
		Entity killer = Bukkit.getServer().getEntity(killerUUID);
		if((killer == null) || (!(killer instanceof Player))){
			return;
		}
		Player pKiller = (Player) killer;
		Fighter pFighter = Fighter.get(pKiller);
		if(pFighter == null){
			return;
		}
		FighterKit fKit = pFighter.getFKit();
		if(fKit == null){
			return;
		}
		WeaponHolder weaponHolder = fKit.getWeaponHolderWithType(WeaponType.THROWING_TNT);
		if(weaponHolder == null){
			return;
		}
		S1_ThrowingTNT tntItem = (S1_ThrowingTNT) weaponHolder;
		CreateExplosion.doAnExplosion(pKiller, e.getEntity().getLocation(), 1.6, tntItem.getProjectileDamage(), false, WeaponType.THROWING_TNT);
	}
}
