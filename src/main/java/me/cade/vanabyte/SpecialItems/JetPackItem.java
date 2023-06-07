package me.cade.vanabyte.SpecialItems;

import me.cade.vanabyte.Fighter;
import me.cade.vanabyte.VanaByte;
import me.cade.vanabyte.Weapon;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class JetPackItem extends SpecialItem {

	private static int cooldown = 150;
	public static Material matCooldown = Material.GLASS_BOTTLE;
	private static Weapon weaponCooldown = new Weapon(matCooldown, ChatColor.YELLOW + "Jetpack", ChatColor.WHITE + "Double Jump!",
			ChatColor.WHITE + "Coolwdown: " + Math.floor((cooldown / 20) * 100) / 100);
	
	public static Material matRecharged = Material.HONEY_BOTTLE;
	private static Weapon weaponRecharged = new Weapon(matRecharged, ChatColor.YELLOW + "Jetpack", ChatColor.WHITE + "Double Jump!",
			ChatColor.WHITE + "Coolwdown: " + Math.floor((cooldown / 20) * 100) / 100);

	public JetPackItem(Player player) {
		super(player);
		player.getInventory().setItem(7, weaponRecharged.getWeaponItem());
		JetPackItem.changeFlightStatus(player, true);
	}

	@Override
	public boolean doRightClick() {
		return true;
		//pass
	}

	@Override
	public void doDrop() {
		// pass
	}

	@Override
	public void doDoubleJump() {
		if (Fighter.get(player).getDoubleJumpTask() != -1) {
			return;
		}
		if (!this.checkAndSetCooldown()) {
			return;
		}
		doBoosterJump(player);
		JetPackItem.changeFlightStatus(player, false);
		JetPackItem.editInventory(player, false);
		doCooldownSuccess(player);
	}

	private void doBoosterJump(Player player) {
		player.playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 8, 1);
		Vector currentDirection = player.getLocation().getDirection().normalize();
		currentDirection = currentDirection.multiply(new Vector(1.6, 1.6, 1.6));
		player.setVelocity(currentDirection);
	}

	public void doCooldownSuccess(Player player) {
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(VanaByte.getInstance(), new Runnable() {
			@Override
			public void run() {
				if (player != null) {
					if (!player.isOnline()) {
						return;
					}
					if(player.getAllowFlight()) {
						return;
					}
					JetPackItem.changeFlightStatus(player, true);
					JetPackItem.editInventory(player, true);
					player.playSound(player.getLocation(), Sound.AMBIENT_UNDERWATER_ENTER, 8, 1);
				}
			}
		}, this.getCooldown());
	}

//	public void listenForFall(Player player, Fighter pFight) {
//		pFight.setDoubleJumpTask(new BukkitRunnable() {
//			@SuppressWarnings("deprecation")
//			@Override
//			public void run() {
//				if (player == null) {
//					stopListeningForDoubleJump(player, pFight);
//					return;
//				}
//				if (!player.isOnline()) {
//					stopListeningForDoubleJump(player, pFight);
//					return;
//				}
//				if (player.isDead()) {
//					stopListeningForDoubleJump(player, pFight);
//					return;
//				}
//				if (player.isOnGround()) {
//					stopListeningForDoubleJump(player, pFight);
//					return;
//				}
//			}
//		}.runTaskTimer(Main.getInstance(), 0L, 1L).getTaskId());
//	}
//
//	public static void stopListeningForDoubleJump(Player player, Fighter pFight) {
//		Bukkit.getScheduler().cancelTask(pFight.getDoubleJumpTask());
//		pFight.setDoubleJumpTask(-1);
//		JetPackItem.changeFlightStatus(player, true);
//	}

	@Override
	public Weapon getWeapon() {
		return weaponCooldown;
	}

	@Override
	public Material getMaterial() {
		return matCooldown;
	}

	@Override
	public int getCooldown() {
		return cooldown;
	}
	
	@Override
	public void resetCooldown() {
		super.resetCooldown();
		JetPackItem.changeFlightStatus(player, true);
		JetPackItem.editInventory(player, true);
	}

	public static void changeFlightStatus(Player player, boolean allowFlight) {
		player.setAllowFlight(allowFlight);
	}
	
	public static void editInventory(Player player, boolean allowFlight) {
		if (allowFlight) {
			if(player.getInventory().contains(Material.HONEY_BOTTLE)) {
				return;
			}
			player.getInventory().remove(matCooldown);
			player.getInventory().setItem(7, weaponRecharged.getWeaponItem());
		} else {
			if(player.getInventory().contains(Material.GLASS_BOTTLE)) {
				return;
			}
			player.getInventory().remove(matRecharged);
			player.getInventory().setItem(7, weaponCooldown.getWeaponItem());
		}
	}

}
