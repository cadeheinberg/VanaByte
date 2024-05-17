package me.cade.vanabyte.Damaging;

import me.cade.vanabyte.FighterWeapons.InUseWeapons.*;
import me.cade.vanabyte.Fighters.*;
import me.cade.vanabyte.NPCS.NPCListener;
import me.cade.vanabyte.Permissions.PlayerChat;
import me.cade.vanabyte.Permissions.SafeZone;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class EntityDamage implements Listener {

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

	// do grief give back health special
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
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
		Player killer = null;
		if (e.getDamager() instanceof TNTPrimed) {
			//ToDo
		}
		if (!(e.getDamager() instanceof Player)) {
			double damage_amount = 0;
			if (e.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
				Projectile p = (Projectile) e.getDamager();
				if (!FighterProjectile.projectileHasMetadata(e.getDamager())) {
					//If the projectile is not from a player using a Fighter kit
					//then ignore it
					return;
				}
				if (p.getShooter() instanceof Player) {
					killer = (Player) p.getShooter();
					Fighter fighter = Fighter.get(killer);
					FighterKit fKit = fighter.getFKit();
					if (e.getDamager() instanceof Snowball && fKit.getSpecificWeaponHolderIfItExists(W2_ShottyShotgun.class) != null) {
						damage_amount = ((W2_ShottyShotgun) fKit.getSpecificWeaponHolderIfItExists(W2_ShottyShotgun.class)).doSnowballHitEntity((LivingEntity) e.getEntity(), (Snowball) e.getDamager());
						e.getDamager().remove();
						e.setDamage(damage_amount);
					} else if (e.getDamager() instanceof Arrow && fKit.getSpecificWeaponHolderIfItExists(W3_GoblinBow.class) != null) {
						damage_amount = ((W3_GoblinBow) fKit.getSpecificWeaponHolderIfItExists(W3_GoblinBow.class)).doArrowHitEntity((LivingEntity) e.getEntity(), (Arrow) e.getDamager());
						e.getDamager().remove();
						e.setDamage(damage_amount);
					} else if (e.getDamager() instanceof Trident && fKit.getSpecificWeaponHolderIfItExists(W4_IgorsTrident.class) != null) {
						damage_amount = ((W4_IgorsTrident) fKit.getSpecificWeaponHolderIfItExists(W4_IgorsTrident.class)).doTridentHitEntity((LivingEntity) e.getEntity(), (Trident) e.getDamager());
						e.getDamager().remove();
						e.setDamage(damage_amount);
					}
				}
			}
			//dont do the below for non players
			return;
		}
		//players only below
		killer = (Player) e.getDamager();
		Fighter fKiller = null;
		if(Fighter.get(killer).getFKit().getSpecificSimilarWeaponHolderInHands(W6_GriefSword.class) != null) {
			((W6_GriefSword) Fighter.get(killer).getFKit().getSpecificSimilarWeaponHolderInHands(W6_GriefSword.class)).doStealHealth();
		}
		if(killer != null){
			fKiller = Fighter.get(killer);
			if (killer.getPassengers() != null) {
				if (killer.getPassengers().size() > 0) {
					if (killer.getPassengers().get(0).equals(e.getEntity())) {
						if(Fighter.get(killer).getFKit().getSpecificSimilarWeaponHolderInHands(W5_SumoStick.class) != null) {
							((W5_SumoStick) Fighter.get(killer).getFKit().getSpecificSimilarWeaponHolderInHands(W5_SumoStick.class)).doThrow(killer, (LivingEntity) e.getEntity());
						}
					}
				}
			}
			if(e.getEntity() instanceof Player){
				Player victim = (Player) e.getEntity();
				if (victim.equals(killer)) {
					return;
				}
				Fighter fVictim = Fighter.get(victim);
				fVictim.setLastDamagedBy(killer);
				fKiller.setLastToDamage(victim);
				killer.setCooldown(FighterKitManager.getCombatTrackerMaterial(), 200);
				victim.setCooldown(FighterKitManager.getCombatTrackerMaterial(), 200);
			}
		}
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		e.setDeathMessage("");
		Player victim = e.getEntity();
		Player killer = null;

//    Glowing.setGlowingOffForAll(victim);

		Fighter fVictim = Fighter.get(victim);

		fVictim.fighterDeath();

		if(!SafeZone.inHub(victim.getLocation().getWorld())){
			List<ItemStack> drops = List.copyOf(e.getDrops());
			for(ItemStack drop : drops){
				if (FighterKitManager.hasNameOfWeapon(drop)) {
					e.getDrops().remove(drop);
				}
			}
			ItemStack helmet = victim.getEquipment().getHelmet();
			ItemStack chest = victim.getEquipment().getChestplate();
			ItemStack leggings = victim.getEquipment().getLeggings();
			ItemStack boots = victim.getEquipment().getBoots();

			if(helmet != null){
				e.getDrops().remove(helmet);
			}
			if(chest != null){
				e.getDrops().remove(chest);
			}
			if(leggings != null){
				e.getDrops().remove(leggings);
			}
			if(boots != null){
				e.getDrops().remove(boots);
			}
			fVictim.dropFighterKitSoul();
		}

		if (victim.getCooldown(FighterKitManager.getCombatTrackerMaterial()) < 1) {
			// After 10 seconds don't count the kill for the killer
			return;
		}

		killer = Bukkit.getPlayer(fVictim.getLastDamagedBy());
		if (!checkKillerStatus(killer, victim, fVictim)) {
			return;
		}
		Fighter fKiller = Fighter.get(killer);
		fKiller.incKills();

		if (fKiller.getLastDamagedBy() != null) {
			if (fKiller.getLastDamagedBy().equals(victim.getUniqueId())) {
				fKiller.setLastDamagedBy(null);
			}
		}
		fVictim.setLastDamagedBy(null);
		fVictim.setLastToDamage(null);

		tellDeathMessage(killer, killer.getName(), victim.getName(), fKiller.getKitID());

	}

	public static boolean checkKillerStatus(Player killer, Player victim, Fighter fVictim) {
		if (killer == null) {
			return false;
		}
		if (killer.equals(victim)) {
			return false;
		}
		if (!killer.isOnline()) {
			return false;
		}
		return true;
	}

	public void tellDeathMessage(Player killer, String killerName, String victimName, int kitID) {
		String weaponName = Fighter.get(killer).getFKit().getKitName();

		PlayerChat.tellPlayerMessageToAll(ChatColor.YELLOW + "" + ChatColor.ITALIC + killerName + ChatColor.RESET
				+ " killed " + ChatColor.YELLOW + "" + ChatColor.ITALIC + victimName + ChatColor.RESET + " using " + ""
				+ "[" + weaponName + ChatColor.RESET + "" + ChatColor.WHITE + "]");
	}

}
