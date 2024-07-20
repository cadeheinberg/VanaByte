package me.cade.vanabyte.Fighters.PVP;

import me.cade.vanabyte.Fighters.PVP.DamageTracker.CustomDamageWrapper;
import me.cade.vanabyte.Fighters.PVP.DamageTracker.EntityDamageData;
import me.cade.vanabyte.Fighters.PVP.DamageTracker.EntityDamageEntry;
import me.cade.vanabyte.Fighters.Enums.ArmorType;
import me.cade.vanabyte.Fighters.Enums.WeaponType;
import me.cade.vanabyte.Fighters.WeaponHolders.*;
import me.cade.vanabyte.Fighters.*;
import me.cade.vanabyte.Fighters.FighterKitManager;
import me.cade.vanabyte.NPCS.GUIs.QuestListener;
import me.cade.vanabyte.Permissions.PlayerChat;
import me.cade.vanabyte.Permissions.SafeZone;
import me.cade.vanabyte.VanaByte;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public class EntityDamageListener implements Listener {

	@EventHandler
	public void onEntityDeath(EntityDeathEvent e) {
		Entity victim = e.getEntity();
		//ignore player deaths in this method
		if(victim == null || victim instanceof Player){
			return;
		}
		EntityDamageData victimDamageData = VanaByte.getEntityDamageManger().getOrCreate(victim.getUniqueId());
		EntityDamageEntry damageEntry = victimDamageData.getLastAttacker();
		if(damageEntry == null){
			//no entity responsible for this death
			return;
		}
		EntityType killerType = damageEntry.getAttackerType();
		UUID killerUUID = damageEntry.getAttackerUUID();
		WeaponType weaponType = damageEntry.getWeaponType();
		Entity killer = Bukkit.getServer().getEntity(killerUUID);
		if(killer == null){

		}else if(killer instanceof Player){
			QuestListener.entityKilledByPlayer(damageEntry);
		}else if(killer instanceof LivingEntity){
			// get the owner of this entity possibly
			// but prob just do this in damageevent instead
			// and take see the owner as the damager as well
			// killer.getMetadata("owner");
		}else{

		}
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Player victim = e.getEntity();
		Fighter fVictim = Fighter.get(victim);
		fVictim.fighterDeath();

		EntityDamageData victimDamageData = VanaByte.getEntityDamageManger().getOrCreate(victim.getUniqueId());
		EntityDamageEntry damageEntry = victimDamageData.getLastAttacker();
		if(damageEntry == null){
			//no entity responsible for this death
			return;
		}
		EntityType killerType = damageEntry.getAttackerType();
		UUID killerUUID = damageEntry.getAttackerUUID();
		WeaponType weaponType = damageEntry.getWeaponType();
		Entity killer = Bukkit.getServer().getEntity(killerUUID);
		if(SafeZone.inAnarchy(victim.getWorld())){
			List<ItemStack> drops = e.getDrops();
			//1) duplicate drops, cancel event, and drop ones you want manually?
			//2) delayed removal, after 1 second?
			//3) set them to air, working so far?
//			List<ItemStack> toRemove = List.of();
			for(int i = 0; i < drops.size(); i++){
				if((Weapon.getWeaponTypeFromItemStack(drops.get(i)) != null && Weapon.getWeaponTypeFromItemStack(drops.get(i)) != WeaponType.UNKNOWN_WEAPON)
					|| (FighterKitManager.getArmorTypeFromItemStack(drops.get(i)) != null && FighterKitManager.getArmorTypeFromItemStack(drops.get(i)) != null)){
					drops.get(i).setType(Material.AIR);
				}
			}
		}
		if(killer == null){
			e.setDeathMessage("");
		} else if(killer instanceof Player){
			Player pkiller = (Player) killer;
			if(isValidKiller(pkiller, victim)) {
				if(weaponType == null || weaponType == WeaponType.UNKNOWN_WEAPON){
					tellDeathMessage(e.getDeathMessage());
					e.setDeathMessage("");
				}else{
					tellDeathMessage(pkiller, victim, weaponType);
					e.setDeathMessage("");
				}
				Fighter fKiller = Fighter.get(pkiller);
				if(fKiller != null){
					fKiller.incKills();
				}
				QuestListener.entityKilledByPlayer(damageEntry);
			}
		} else if(killer instanceof LivingEntity){
			LivingEntity lKiller = (LivingEntity) killer;
			if(isValidKiller(lKiller, victim)) {
				if(weaponType == null || weaponType == WeaponType.UNKNOWN_WEAPON){
					tellDeathMessage(e.getDeathMessage());
					e.setDeathMessage("");
				}else{
					tellDeathMessage(lKiller, victim, weaponType);
					e.setDeathMessage("");
				}
				return;
			}
		} else{
			e.setDeathMessage("EntityDamageListener: error1");
		}
	}

	// do grief give back health special
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if((!(e.getEntity() instanceof LivingEntity)) || e.getDamager() == null){
			return;
		}
		Entity damagingEntity = e.getDamager();
		LivingEntity victim = (LivingEntity) e.getEntity();

		////
		// Hub Spawn
		////
		if (SafeZone.safeZone(victim.getLocation()) || SafeZone.safeZone(damagingEntity.getLocation())) {
			if(SafeZone.ladderZone(victim.getLocation()) && SafeZone.ladderZone(damagingEntity.getLocation())){
				e.setDamage(0); //inside ladder match
				return;
			}
			e.setCancelled(true);
			if (damagingEntity instanceof Player) {
				if (victim.getType() != EntityType.ARMOR_STAND) {
					return;
				}
				int x = victim.getLocation().getBlockX();
				NPCListener.handleKitSelection((Player) damagingEntity, x);
			}
			return;
		}

		////
		// Player Melee Sources
		////
		if (damagingEntity instanceof Player){
			if(e.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION){
				//created by CreateExplosion(), where DamageTracking is accounted for.
				e.setCancelled(true);
				return;
			}
			Player pkiller = (Player) e.getDamager();
			if(pkiller == null){
				return;
			}
			WeaponType weaponType = Weapon.getWeaponTypeFromMainHand(pkiller);
			if(weaponType == null || weaponType == WeaponType.UNKNOWN_WEAPON){
				//fists or non special weapon
				VanaByte.getEntityDamageManger().register(new CustomDamageWrapper(e, WeaponType.UNKNOWN_WEAPON));
				return;
			}
			WeaponHolder weaponHolder = Fighter.get((Player) damagingEntity).getFighterKitManager().getWeaponHolderWithType(weaponType);
			weaponHolder.doMeleeAttack(e, pkiller, victim);
			return;
		}

		////
		// Player Projectile Sources
		////
		if (!(damagingEntity instanceof Player)) {
			double damage_amount = 0;
			if(damagingEntity instanceof LivingEntity){
				//damager is some living entity
				//victim is a player or any living entity
				VanaByte.getEntityDamageManger().register(new CustomDamageWrapper(e, WeaponType.UNKNOWN_WEAPON));
			}else if (e.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
				Projectile projectile = (Projectile) e.getDamager();
				if (EntityMetadata.getWeaponTypeFromEntity(e.getDamager()) == null) {
					return;
				}

				if (projectile.getShooter() == null || (!(projectile.getShooter() instanceof Player))) {
					return;
				}
				Player pkiller = (Player) projectile.getShooter();
				WeaponType weaponType = EntityMetadata.getWeaponTypeFromEntity(damagingEntity);
				if(weaponType == null || weaponType == WeaponType.UNKNOWN_WEAPON){
					//some other projectile
					VanaByte.getEntityDamageManger().register(new CustomDamageWrapper(e, WeaponType.UNKNOWN_WEAPON));
					return;
				}
				WeaponHolder weaponHolder = Fighter.get(pkiller).getFighterKitManager().getWeaponHolderWithType(weaponType);
				if(weaponHolder == null){
					//some other projectile
					VanaByte.getEntityDamageManger().register(new CustomDamageWrapper(e, WeaponType.UNKNOWN_WEAPON));
					return;
				}
				weaponHolder.doProjectileHitEntity(e, pkiller, victim, damagingEntity);
			}
		}
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (SafeZone.safeZone(e.getEntity().getLocation())) {
			if(SafeZone.ladderZone(e.getEntity().getLocation())){
				//inside ladder match
				e.setDamage(0);
				return;
			}
			e.setCancelled(true);
		}
		if (e.getEntity().getType() == EntityType.CHICKEN) {
			if(e.getEntity().hasMetadata("parachute")){
				e.setCancelled(true);
			}
		}
	}

	public static boolean isValidKiller(LivingEntity killer, Player victim) {
		if (killer == null) {
			return false;
		}
		if (killer.getUniqueId() == victim.getUniqueId()) {
			return false;
		}
		if(killer instanceof Player){
			if (!((Player) killer).isOnline()) {
				return false;
			}
		}
		return true;
	}

	public void tellDeathMessage(String message) {
		PlayerChat.tellPlayerMessageToAll(message);
	}

	public void tellDeathMessage(LivingEntity killer, LivingEntity victim, WeaponType weaponType) {
		String weaponName = weaponType.getWeaponNameUncolored();

		PlayerChat.tellPlayerMessageToAll(ChatColor.YELLOW + "" + ChatColor.ITALIC + killer.getName() + ChatColor.RESET
				+ " killed " + ChatColor.YELLOW + "" + ChatColor.ITALIC + victim.getName() + ChatColor.RESET + " with "
				+ ChatColor.YELLOW + "" + ChatColor.ITALIC + weaponName);
	}

}
