package me.cade.vanabyte.Fighters;

import me.cade.vanabyte.Damaging.CreateExplosion;
import me.cade.vanabyte.FighterWeapons.InUseWeapons.*;
import me.cade.vanabyte.Permissions.SafeZone;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class KitListener implements Listener {

	@EventHandler
	public void onRightClick(PlayerInteractEvent e) {
		if (SafeZone.safeZone(e.getPlayer().getLocation())) {
			return;
		}
		if (e.getHand() == EquipmentSlot.OFF_HAND) {
			return; // off hand packet, ignore.
		}
		if (!(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
			if (e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
				if (e.getPlayer().getPassengers() != null) {
					if (e.getPlayer().getPassengers().size() > 0) {
						if(Fighter.get(e.getPlayer()).getFKit().getSpecificSimilarWeaponHolderInHands(W5_SumoStick.class) != null) {
							((W5_SumoStick) Fighter.get(e.getPlayer()).getFKit().getSpecificSimilarWeaponHolderInHands(W5_SumoStick.class)).doThrow(e.getPlayer(), (LivingEntity) e.getPlayer().getPassengers().get(0));
							return;
						}
					}
				}
			}
			return;
		}
		if (e.getItem() == null) {
			return;
		}
		if(e.getMaterial() == Material.BOW || e.getMaterial() == Material.TRIDENT) {
			return;
		}
		WeaponHolder weaponHolder = Fighter.get(e.getPlayer()).getFKit().getSimilarWeaponHolderFighterHas(e.getItem());
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
		WeaponHolder weaponHolder = Fighter.get(e.getPlayer()).getFKit().getSimilarWeaponHolderFighterHas(e.getItemDrop().getItemStack());
		if(weaponHolder != null){
			weaponHolder.doDrop();
			return;
		}
		if(!SafeZone.inHub(e.getPlayer().getWorld())){
				//The player is trying to drop the item in the survival world
				e.setCancelled(false);
		}
	}

	@EventHandler
	public void onProjectileHit(ProjectileHitEvent e) {
		if (!(e.getEntity().getShooter() instanceof Player)) {
			return;
		}
		Player shooter = (Player) e.getEntity().getShooter();
		if(!FighterProjectile.projectileHasMetadata(e.getEntity())){
			//If the projectile is not from a player using a Fighter kit
			//then ignore it
			return;
		}
		// Hit a block
		if (e.getHitBlock() != null) {
			if (SafeZone.safeZone(e.getHitBlock().getLocation())) {
				return;
			}
			FighterKit fKit = Fighter.get(shooter).getFKit();
			if (e.getEntity() instanceof Snowball && fKit.getSpecificWeaponHolderIfItExists(W2_ShottyShotgun.class) != null) {
				((W2_ShottyShotgun) fKit.getSpecificWeaponHolderIfItExists(W2_ShottyShotgun.class)).doSnowballHitGround(e.getHitBlock().getLocation(),
						(Snowball) e.getEntity());
			} else if (e.getEntity() instanceof Arrow && fKit.getSpecificWeaponHolderIfItExists(W3_GoblinBow.class) != null) {
				e.getEntity().remove();
			} else if (e.getEntity() instanceof Trident && fKit.getSpecificWeaponHolderIfItExists(W4_IgorsTrident.class) != null) {
				((W4_IgorsTrident) fKit.getSpecificWeaponHolderIfItExists(W4_IgorsTrident.class)).doTridentHitGround(e.getHitBlock().getLocation(),
						(Trident) e.getEntity());
			}
		}
		// Hit an entity
		if (e.getHitEntity() != null) {
			//moved to EntityDamage listener
		}
	}

	@EventHandler
	public void onProjectileLaunch(ProjectileLaunchEvent e) {
		if (SafeZone.safeZone(e.getLocation())) {
			e.setCancelled(true);
			return;
		}
		if (e.getEntityType() != EntityType.TRIDENT) {
			return;
		}
		if (!(e.getEntity().getShooter() instanceof Player)) {
			return;
		}
		FighterKit fKit = Fighter.get((Player) e.getEntity().getShooter()).getFKit();
		if(fKit.getSpecificSimilarWeaponHolderInHands(W4_IgorsTrident.class) != null) {
			if (!((W4_IgorsTrident) fKit.getSpecificSimilarWeaponHolderInHands(W4_IgorsTrident.class)).doThrowTrident((Trident) e.getEntity())) {
				//Set canceled if there is a cooldown
				e.setCancelled(true);
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
		FighterKit fKit = Fighter.get((Player) e.getEntity()).getFKit();
		if (fKit.getSpecificSimilarWeaponHolderInHands(W3_GoblinBow.class) != null) {
			if (!((W3_GoblinBow) fKit.getSpecificSimilarWeaponHolderInHands(W3_GoblinBow.class)).doArrowShoot((Arrow) e.getProjectile(), e.getForce())) {
				//Set canceled if there is a cooldown
				e.setCancelled(true);
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
		if (e.getEntity() instanceof TNTPrimed) {
			// If its a creeper or something natural ignore it
			return;
		}
		if(!e.getEntity().hasMetadata("thrower")){
			// If its a normal TNT and not Fighter produced ignore it
			return;
		}
		e.setCancelled(true);
		for (Block b : e.blockList()) {
			if (b.getType() == Material.PACKED_ICE) {
				b.breakNaturally();
			}
		}
		e.blockList().clear();
	}

	@EventHandler
	public void onExplodePrime(ExplosionPrimeEvent e) {
		if(!(e.getEntity() instanceof TNTPrimed)){
			return;
		}
		if(!e.getEntity().hasMetadata("thrower")){
			// If its a normal TNT and not Fighter produced ignore it
			return;
		}
		e.setCancelled(true);
		// Create the explosion
		Player killer = (Player) Bukkit.getPlayer(e.getEntity().getMetadata("thrower").get(0).asString());
		if (killer != null) {
			if(Fighter.get(killer).getFKit().getSpecificWeaponHolderIfItExists(S1_ThrowingTNT.class) != null){
				S1_ThrowingTNT tntItem = (S1_ThrowingTNT) (Fighter.get(killer).getFKit().getSpecificWeaponHolderIfItExists(S1_ThrowingTNT.class));
				CreateExplosion.doAnExplosion(killer, e.getEntity().getLocation(), 1.6, tntItem.getProjectileDamage(), false);
			}

		}
	}
}
