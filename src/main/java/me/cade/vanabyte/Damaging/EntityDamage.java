package me.cade.vanabyte.Damaging;

import me.cade.vanabyte.Fighter;
import me.cade.vanabyte.NPCS.D0_NpcListener;
import me.cade.vanabyte.PlayerChat;
import me.cade.vanabyte.SafeZone;
import me.cade.vanabyte.SpecialItems.CombatTracker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

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

			if (e instanceof EntityDamageByEntityEvent) {
				Entity attacker = ((EntityDamageByEntityEvent) e).getDamager();
				if (!(attacker instanceof Player)) {
					return;
				}
				if (e.getEntity().getType() != EntityType.ARMOR_STAND) {
					return;
				}
				int x = e.getEntity().getLocation().getBlockX();
				D0_NpcListener.handleKitSelection((Player) attacker, x);

			}
			return;
		}
		if (e.getEntity().getType() == EntityType.CHICKEN) {
			e.setCancelled(true);
			return;
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
			return;
		}
		if (!(e.getDamager() instanceof Player)) {
			if (e.getDamager().getType() == EntityType.PRIMED_TNT) {
				e.setDamage(0);
			}
			return;
		}
		if (!(e.getEntity() instanceof LivingEntity)) {
			return;
		}
		if (e.getCause() != EntityDamageByEntityEvent.DamageCause.ENTITY_ATTACK) {
			e.setDamage(0);
			return;
		}
		Player killer = (Player) e.getDamager();
		Fighter fKiller = Fighter.get(killer);

		if (killer.getPassengers() != null) {
			if (killer.getPassengers().size() > 0) {
				if (killer.getPassengers().get(0).equals(e.getEntity())) {
					fKiller.getFKit().doThrow(killer, (LivingEntity) e.getEntity());
				}
			}
		}

		if (!(e.getEntity() instanceof Player)) {
			return;
		}

		Player victim = (Player) e.getEntity();

		if (victim.equals(killer)) {
			return;
		}

		Fighter fVictim = Fighter.get(victim);

		fKiller.getFKit().doStealHealth(victim);

		fVictim.setLastDamagedBy(killer);
		fKiller.setLastToDamage(victim);

		killer.setCooldown(CombatTracker.mat, 200);
		victim.setCooldown(CombatTracker.mat, 200);

		victim.setCooldown(Material.BRICK, 200);

	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		e.setDeathMessage("");
		Player victim = e.getEntity();
		Player killer = null;

//    Glowing.setGlowingOffForAll(victim);

		Fighter fVictim = Fighter.get(victim);

		fVictim.fighterDeath();

		if (victim.getCooldown(Material.BRICK) < 1) {
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
