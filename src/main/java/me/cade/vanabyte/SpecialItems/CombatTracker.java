package me.cade.vanabyte.SpecialItems;

import me.cade.vanabyte.VanaByte;
import me.cade.vanabyte.Fighters.Weapons.Weapon;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class CombatTracker extends SpecialItem {

	public static Material mat = Material.DRAGON_HEAD;
	private static Weapon weapon = new Weapon(mat, ChatColor.LIGHT_PURPLE + "Spawn Teleporter",
			ChatColor.WHITE + "Right Click to go to /spawn",
			ChatColor.WHITE + "Don't leave game while PvP cooldown is on!");

	public CombatTracker(Player player) {
		super(player);
		player.getInventory().setItem(8, getWeapon().getWeaponItem());
	}

	@Override
	public boolean doRightClick() {
		if (player.getWorld() == VanaByte.hub) {
			if (player.getCooldown(this.getMaterial()) > 0) {
				player.sendMessage(ChatColor.RED + "Can't" + ChatColor.AQUA + "" + ChatColor.BOLD + " /spawn"
						+ ChatColor.RED + ". You have a" + ChatColor.AQUA + "" + ChatColor.BOLD + " PVP Cooldown "
						+ ChatColor.RED + "on");
				return false;
			}
			player.teleport(VanaByte.hubSpawn);
			return true;
		}
		return false;
	}

	@Override
	public void doDrop() {
		this.doRightClick();
	}

	@Override
	public Weapon getWeapon() {
		return weapon;
	}

	@Override
	public Material getMaterial() {
		return mat;
	}

}
