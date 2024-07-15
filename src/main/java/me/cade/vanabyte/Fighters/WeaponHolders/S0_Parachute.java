package me.cade.vanabyte.Fighters.WeaponHolders;

import me.cade.vanabyte.Fighters.Enums.WeaponType;
import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.VanaByte;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDismountEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class S0_Parachute extends WeaponHolder {

	private Chicken chicken = null;

	public S0_Parachute(Fighter fighter, WeaponType weaponType) {
		super(fighter, weaponType);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean doRightClick() {
		if (super.getPlayer().isOnGround()) {
			super.getPlayer().playSound(super.getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 8, 1);
			return false;
		}
		if (super.getFighter().getFighterTaskManager().getParachuteTask() != 0) {
			super.getPlayer().playSound(super.getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 8, 1);
			return false;
		}
		if(this.chicken != null){
			super.getPlayer().playSound(super.getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 8, 1);
			return false;
		}
		if(!super.doRightClick()){
			return false;
		}
		this.doParachute(super.getPlayer());
		return true;
	}
	@Override
	public boolean doDrop() {
		return this.doRightClick();
	}

	@Override
	public void activateSpecial() {
		super.activateSpecial();
	}

	@Override
	public void deActivateSpecial() {
		super.deActivateSpecial();
	}

	@Override
	public boolean doDismount(EntityDismountEvent e) {
		if(e.getDismounted().getType() == EntityType.CHICKEN){
			super.getFighter().fighterDismountParachute();
		}
		return true;
	}

	private void doParachute(Player player) {
		player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 8, 1);
		Chicken chicken = (Chicken) player.getWorld().spawnEntity(player.getLocation(), EntityType.CHICKEN);
		chicken.addPassenger(player);
		chicken.setMetadata("parachute", new FixedMetadataValue(VanaByte.getInstance(), true));
		this.chicken = chicken;
		doGliding(chicken, player);
	}

	private void doGliding(Chicken chicken, Player player) {
		super.getFighter().getFighterTaskManager().setParachuteTask(new BukkitRunnable() {
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
				chicken.setVelocity(vector.multiply(0.6));
			}
		}.runTaskTimer(VanaByte.getInstance(), 0L, 1L).getTaskId());
	}

	public void getOffChicken() {
		super.getFighter().getFighterTaskManager().cancelParachuteTask();
		if(chicken != null) {
			chicken.remove();
			this.chicken = null;
		}
	}
}
