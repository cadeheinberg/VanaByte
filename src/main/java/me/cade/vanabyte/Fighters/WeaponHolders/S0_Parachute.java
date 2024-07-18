package me.cade.vanabyte.Fighters.WeaponHolders;

import me.cade.vanabyte.Fighters.Enums.WeaponType;
import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.VanaByte;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDismountEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class S0_Parachute extends WeaponHolder {

	private final static WeaponType WEAPON_TYPE = WeaponType.PARACHUTE;
	private Chicken chicken = null;
	private final double meleeDamage = fighter.getDoubleFromWeaponType(weaponType, 0);
	private final int baseCooldown = fighter.getTickFromWeaponType(weaponType, 1);
	private final int abilityOnCooldown = -1;
	private final double chickenSpeed = fighter.getDoubleFromWeaponType(weaponType, 2);

	public S0_Parachute(Fighter fighter) {
		super(WEAPON_TYPE);
		super.weapon = new Weapon(
				WEAPON_TYPE,
				WEAPON_TYPE.getMaterial(),
				WEAPON_TYPE.getWeaponNameColored(),
				meleeDamage,
				baseCooldown,
				-1,
				-1);
		super.player = fighter.getPlayer();
		super.weaponAbility = new WeaponAbility(fighter, this);
		super.fighter = fighter;
		this.player = this.fighter.getPlayer();
	}

	@Override
	public void doMeleeAttack(EntityDamageByEntityEvent e, Player killer, LivingEntity victim) {
		super.trackWeaponDamage(victim, e.getFinalDamage());
	}

	@Override
	public void doRightClick(PlayerInteractEvent e) {
		if(!super.checkAndSetMainCooldown(baseCooldown, abilityOnCooldown)){
			return;
		}
		openParachute();
	}

	@Override
	public void doDrop(PlayerDropItemEvent e) {
		if(!super.checkAndSetMainCooldown(baseCooldown, abilityOnCooldown)){
			return;
		}
		openParachute();
	}

	@Override
	public void doDismount(EntityDismountEvent e) {
		if(e.getDismounted().getType() == EntityType.CHICKEN){
			fighter.fighterDismountParachute();
		}
	}

	private void openParachute() {
		if (player.isOnGround()) {
			player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 8, 1);
		}
		if (fighter.getFighterTaskManager().getParachuteTask() != 0) {
			player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 8, 1);
		}
		if(this.chicken != null){
			player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 8, 1);
		}
		player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 8, 1);
		Chicken chicken = (Chicken) player.getWorld().spawnEntity(player.getLocation(), EntityType.CHICKEN);
		chicken.addPassenger(player);
		chicken.setMetadata("parachute", new FixedMetadataValue(VanaByte.getInstance(), true));
		this.chicken = chicken;
		doGliding(chicken, player);
	}

	private void doGliding(Chicken chicken, Player player) {
		fighter.getFighterTaskManager().setParachuteTask(new BukkitRunnable() {
			@Override
			public void run() {
//				Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Parachute Task Running");
				if (chicken.isOnGround()) {
					chicken.eject();
					getOffChicken();
					return;
				}
				Location loc = player.getEyeLocation();
				if (loc.getPitch() < 40) {
					loc.setPitch(40);
				} else if (loc.getPitch() >= 75) {
					loc.setPitch(75);
				}
				Vector vector = loc.getDirection();
				chicken.setVelocity(vector.multiply(chickenSpeed));
			}
		}.runTaskTimer(VanaByte.getInstance(), 0L, 1L).getTaskId());
	}

	public void getOffChicken() {
		fighter.getFighterTaskManager().cancelParachuteTask();
		if(chicken != null) {
			chicken.remove();
			this.chicken = null;
		}
	}
}
