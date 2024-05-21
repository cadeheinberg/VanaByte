package me.cade.vanabyte.Damaging;

import me.cade.vanabyte.Damaging.DamageTracker.CustomDamageWrapper;
import me.cade.vanabyte.Damaging.DamageTracker.EntityDamageData;
import me.cade.vanabyte.Damaging.DamageTracker.EntityDamageEntry;
import me.cade.vanabyte.FighterWeapons.InUseWeapons.*;
import me.cade.vanabyte.Fighters.*;
import me.cade.vanabyte.NPCS.GUIs.QuestListener;
import me.cade.vanabyte.NPCS.RealEntities.NPCListener;
import me.cade.vanabyte.Permissions.PlayerChat;
import me.cade.vanabyte.Permissions.SafeZone;
import me.cade.vanabyte.VanaByte;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
//			List<ItemStack> drops = e.getDrops();
//			for(ItemStack drop : drops){
//				if(Weapon.getWeaponType(drop) != null || Weapon.getWeaponType(drop) != WeaponType.UNKNOWN_WEAPON){
//					e.getDrops().remove(drop);
//				}
//				if(FighterKitManager.getArmorType(drop) != null || FighterKitManager.getArmorType(drop) != ArmorType.UNKOWN_ARMOR){
//					e.getDrops().remove(drop);
//				}
//			}
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
		LivingEntity victim = null;
		if(e.getEntity() instanceof LivingEntity){
			victim = (LivingEntity) e.getEntity();
		}else{
			return;
		}
		Entity damagingEntity = e.getDamager();
		if (SafeZone.safeZone(e.getEntity().getLocation())) {
			if(SafeZone.ladderZone(e.getEntity().getLocation()) && SafeZone.ladderZone(e.getDamager().getLocation())){
				//inside ladder match
				e.setDamage(0);
				return;
			}
			e.setCancelled(true);
			if (e.getDamager() instanceof Player) {
				if (e.getEntity().getType() != EntityType.ARMOR_STAND) {
					return;
				}
				int x = e.getEntity().getLocation().getBlockX();
				NPCListener.handleKitSelection((Player) e.getDamager(), x);
			}
			return;
		}
		if (damagingEntity == null) {
			Bukkit.getServer().broadcastMessage("EntityDamageEntity 5");
			e.getEntity().sendMessage("EntityDamage 7");
			//ToDo
		} else if (damagingEntity instanceof Player){
			Player pkiller = (Player) e.getDamager();
			if(pkiller == null){
				return;
			}
			Fighter fKiller = Fighter.get(pkiller);
			if(fKiller == null){
				return;
			}
			FighterKit fKitKiller = fKiller.getFKit();
			if(fKitKiller == null){
				return;
			}
			ItemStack killerItem = pkiller.getEquipment().getItemInMainHand();
			WeaponType weaponType = null;
			if(killerItem != null){
				weaponType = Weapon.getWeaponType(killerItem);
			}
			////
			//// Melee Damage
			////
			if(weaponType == null || weaponType == WeaponType.UNKNOWN_WEAPON){
				//fists or non special weapon
				VanaByte.getEntityDamageManger().register(new CustomDamageWrapper(e, WeaponType.UNKNOWN_WEAPON));
			} else if(weaponType == WeaponType.SUMO_STICK){
				VanaByte.getEntityDamageManger().register(new CustomDamageWrapper(e, weaponType));
				if (pkiller.getPassengers() == null) {
					return;
				}
				if (pkiller.getPassengers().size() < 1) {
					return;
				}
				if (!(pkiller.getPassengers().get(0).equals(victim))) {
					return;
				}
				if(fKitKiller.getWeaponHolderWithType(weaponType) != null){
					((W5_SumoStick) fKitKiller.getWeaponHolderWithType(weaponType)).doThrow(pkiller, victim);
				}
			} else if(weaponType == WeaponType.GRIEF_SWORD){
				VanaByte.getEntityDamageManger().register(new CustomDamageWrapper(e, weaponType));
				if(fKitKiller.getWeaponHolderWithType(weaponType) != null){
					((W6_GriefSword) fKitKiller.getWeaponHolderWithType(weaponType)).doStealHealth(e.getFinalDamage());
				}
			} else{
				// Other special weapons with no interesting melee effects
				VanaByte.getEntityDamageManger().register(new CustomDamageWrapper(e, weaponType));
			}
		}else if (!(damagingEntity instanceof Player)) {
			double damage_amount = 0;
			if(damagingEntity instanceof LivingEntity){
				//damager is some living entity
				//victim is a player or any living entity
				VanaByte.getEntityDamageManger().register(new CustomDamageWrapper(e, WeaponType.UNKNOWN_WEAPON));
			}else if (e.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
				Projectile p = (Projectile) e.getDamager();
				if (EntityMetadata.getWeaponTypeFromEntity(e.getDamager()) == null) {
					return;
				}
				if (!(p.getShooter() instanceof Player)) {
					return;
				}
				Player pkiller = (Player) p.getShooter();
				if(pkiller == null){
					return;
				}
				Fighter fKiller = Fighter.get(pkiller);
				if(fKiller == null){
					return;
				}
				FighterKit fKitKiller = fKiller.getFKit();
				if(fKitKiller == null){
					return;
				}
				//damager is a player
				//victim is player or any living entity
				if (damagingEntity instanceof Snowball){
					//eventually just use the metadata to store weapon type
					//cause you might add multiple weapons that shoot snowballs
					WeaponType weaponType = WeaponType.SHOTTY_SHOTGUN;
					if(fKitKiller.getWeaponHolderWithType(weaponType) != null){
						damage_amount = ((W2_ShottyShotgun) fKitKiller.getWeaponHolderWithType(weaponType)).doSnowballHitEntity((LivingEntity) e.getEntity(), (Snowball) e.getDamager());
						VanaByte.getEntityDamageManger().register(new CustomDamageWrapper(new EntityDamageByEntityEvent((Entity) pkiller, victim, EntityDamageEvent.DamageCause.ENTITY_ATTACK, DamageSource.builder(DamageType.EXPLOSION).build(), damage_amount), weaponType));
						e.setDamage(damage_amount);
						e.getDamager().remove();
					}
				} else if (damagingEntity instanceof Arrow){
					WeaponType weaponType = WeaponType.GOBLIN_BOW;
					if(fKitKiller.getWeaponHolderWithType(weaponType) != null){
						damage_amount = ((W3_GoblinBow) fKitKiller.getWeaponHolderWithType(weaponType)).doArrowHitEntity((LivingEntity) e.getEntity(), (Arrow) e.getDamager());
						VanaByte.getEntityDamageManger().register(new CustomDamageWrapper(new EntityDamageByEntityEvent((Entity) pkiller, victim, EntityDamageEvent.DamageCause.ENTITY_ATTACK, DamageSource.builder(DamageType.EXPLOSION).build(), damage_amount), weaponType));
						e.setDamage(damage_amount);
						e.getDamager().remove();
					}
				}else if (damagingEntity instanceof Trident) {
					WeaponType weaponType = WeaponType.IGORS_TRIDENT;
					if(fKitKiller.getWeaponHolderWithType(weaponType) != null){
						damage_amount = ((W4_IgorsTrident) fKitKiller.getWeaponHolderWithType(weaponType)).doTridentHitEntity((LivingEntity) e.getEntity(), (Trident) e.getDamager());
						VanaByte.getEntityDamageManger().register(new CustomDamageWrapper(new EntityDamageByEntityEvent((Entity) pkiller, victim, EntityDamageEvent.DamageCause.ENTITY_ATTACK, DamageSource.builder(DamageType.EXPLOSION).build(), damage_amount), weaponType));
						e.setDamage(damage_amount);
						e.getDamager().remove();
					}
				} else {
						//some other projectile
						VanaByte.getEntityDamageManger().register(new CustomDamageWrapper(e, WeaponType.UNKNOWN_WEAPON));
						return;
				}
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
		String weaponName = weaponType.getName();

		PlayerChat.tellPlayerMessageToAll(ChatColor.YELLOW + "" + ChatColor.ITALIC + killer.getName() + ChatColor.RESET
				+ " killed " + ChatColor.YELLOW + "" + ChatColor.ITALIC + victim.getName() + ChatColor.RESET + " using " + ""
				+ "[" + weaponName + ChatColor.RESET + "" + ChatColor.WHITE + "]");
	}

}
