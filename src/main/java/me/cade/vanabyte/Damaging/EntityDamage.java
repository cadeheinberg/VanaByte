package me.cade.vanabyte.Damaging;

import me.cade.vanabyte.Fighters.*;
import me.cade.vanabyte.Fighters.FighterKits.F2;
import me.cade.vanabyte.Fighters.FighterKits.F3;
import me.cade.vanabyte.Fighters.FighterKits.F4;
import me.cade.vanabyte.Fighters.Weapons.GoblinBow;
import me.cade.vanabyte.Fighters.Weapons.IgorsTrident;
import me.cade.vanabyte.Fighters.Weapons.ShottyShotgun;
import me.cade.vanabyte.NPCS.D0_NpcListener;
import me.cade.vanabyte.Permissions.PlayerChat;
import me.cade.vanabyte.Permissions.SafeZone;
import me.cade.vanabyte.Fighters.Weapons.Weapon;
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
				D0_NpcListener.handleKitSelection((Player) e.getDamager(), x);
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
				if(!FighterProjectile.projectileHasMetadata(e.getEntity())){
					//If the projectile is not from a player using a Fighter kit
					//then ignore it
					return;
				}
				if (p.getShooter() instanceof Player) {
					killer = (Player) p.getShooter();
					Fighter fighter = Fighter.get(killer);
					FighterKit fKit = fighter.getFKit();
					if (e.getDamager() instanceof Snowball && fKit.getSpecificWeaponHolderIfItExists(ShottyShotgun.class) != null) {
						damage_amount = ((ShottyShotgun) fKit.getSpecificWeaponHolderIfItExists(ShottyShotgun.class)).doSnowballHitEntity((LivingEntity) e.getEntity(), (Snowball) e.getDamager());
						e.getDamager().remove();
						e.setDamage(damage_amount);
					} else if (e.getDamager() instanceof Arrow && fKit.getSpecificWeaponHolderIfItExists(GoblinBow.class) != null) {
						damage_amount = ((GoblinBow) fKit.getSpecificWeaponHolderIfItExists(GoblinBow.class)).doArrowHitEntity((LivingEntity) e.getEntity(), (Arrow) e.getDamager());
						e.getDamager().remove();
						e.setDamage(damage_amount);
					} else if (e.getDamager() instanceof Trident && fKit.getSpecificWeaponHolderIfItExists(IgorsTrident.class) != null) {
						damage_amount = ((IgorsTrident) fKit.getSpecificWeaponHolderIfItExists(IgorsTrident.class)).doTridentHitEntity((LivingEntity) e.getEntity(), (Trident) e.getDamager());
						e.getDamager().remove();
						e.setDamage(damage_amount);
					}
				}
			}
		} else {
			//(e.getDamager() instanceof Player)
			killer = (Player) e.getDamager();
		}
		Fighter fKiller = null;
		if(killer != null){
			fKiller = Fighter.get(killer);
			if (killer.getPassengers() != null) {
				if (killer.getPassengers().size() > 0) {
					if (killer.getPassengers().get(0).equals(e.getEntity())) {
						fKiller.getFKit().doThrow(killer, (LivingEntity) e.getEntity());
					}
				}
			}
			if(e.getEntity() instanceof Player){
				Player victim = (Player) e.getEntity();
				if (victim.equals(killer)) {
					return;
				}
				Fighter fVictim = Fighter.get(victim);
				fKiller.getFKit().doStealHealth(victim);
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
			List<ItemStack> drops = e.getDrops();
			for (ItemStack drop : drops){
				if (FighterKitManager.hasNameOfWeapon(drop)) {
					drops.remove(drop);
				}
			}
			ItemStack helmet = victim.getEquipment().getHelmet();
			ItemStack chest = victim.getEquipment().getChestplate();
			ItemStack leggings = victim.getEquipment().getLeggings();
			ItemStack boots = victim.getEquipment().getBoots();

			if(helmet != null){
				drops.remove(fVictim.getPlayer().getEquipment().getHelmet());
			}
			if(chest != null){
				drops.remove(fVictim.getPlayer().getEquipment().getChestplate());
			}
			if(leggings != null){
				drops.remove(fVictim.getPlayer().getEquipment().getLeggings());
			}
			if(boots != null){
				drops.remove(fVictim.getPlayer().getEquipment().getBoots());
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
