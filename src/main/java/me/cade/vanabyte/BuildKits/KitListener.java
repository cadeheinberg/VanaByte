package me.cade.vanabyte.BuildKits;

import me.cade.vanabyte.*;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
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
			return;
		}
		if (e.getItem() == null) {
			return;
		}
		if(e.getMaterial() == Material.BOW || e.getMaterial() == Material.TRIDENT) {
			return;
		}
		Player player = e.getPlayer();
		Fighter.getFighterFKit(player).doRightClick(e.getMaterial());
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
		if (!Fighter.get(e.getPlayer()).getFKit().doDrop(e.getItemDrop().getItemStack().getType(), e.getItemDrop().getItemStack().getItemMeta().getDisplayName(), Fighter.get(e.getPlayer()).getKitID())){
			//The item is not a Fighter Kit item or Special Item. Normal Item
			if(!SafeZone.inHub(e.getPlayer().getWorld())){
				//The player is trying to drop the item in the survival world
				e.setCancelled(false);
			}
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
			if (e.getEntity() instanceof Snowball && fKit.getKitID() == 2 && fKit instanceof F2) {
				((F2) Fighter.get(shooter).getFKit()).doSnowballHitGround(e.getHitBlock().getLocation(),
						(Snowball) e.getEntity());
			} else if (e.getEntity() instanceof Arrow && fKit.getKitID() == 3 && fKit instanceof F3) {
				e.getEntity().remove();
			}
			if (e.getEntity() instanceof Trident && fKit.getKitID() == 4 && fKit instanceof F4) {
				((F4) Fighter.get(shooter).getFKit()).doTridentHitGround(e.getHitBlock().getLocation(),
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
		if(fKit.getKitID() == 4 && fKit instanceof F4 && fKit.getPlayer().getItemInUse().getItemMeta().getDisplayName().equals(F4.weaponName)) {
			if (!((F4) fKit).doThrowTrident((Trident) e.getEntity())) {
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
		if (fKit.getKitID() == 3 && fKit instanceof F3 && fKit.getPlayer().getItemInUse().getItemMeta().getDisplayName().equals(F3.weaponName)) {
			if (!((F3) fKit).doArrowShoot((Arrow) e.getProjectile(), e.getForce())) {
				//Set canceled if there is a cooldown
				e.setCancelled(true);
			}
		}

	}
}
