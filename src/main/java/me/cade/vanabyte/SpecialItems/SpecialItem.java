package me.cade.vanabyte.SpecialItems;

import me.cade.vanabyte.Weapon;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SpecialItem {

	protected Player player;

	public SpecialItem(Player player) {
		this.player = player;
	}

	public boolean doRightClick() {
		return this.checkAndSetCooldown();
	}
	
	public boolean checkAndSetCooldown() {
		if (player.getCooldown(this.getMaterial()) > 0) {
			this.player.playSound(this.player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 8, 1);
			return false;
		}
		player.setCooldown(getMaterial(), this.getCooldown());
		return true;
	}

	public void doDrop() {
		// see override methods
	}

	public void resetCooldown() {
		player.setCooldown(getMaterial(), 0);
	}

	public Player getPlayer() {
		return this.player;
	}

	public Weapon getWeapon() {
		// see override methods
		return null;
	}

	public Material getMaterial() {
		// see override methods
		return null;
	}

	public int getCooldown() {
		// see override methods
		return -1;
	}

	public void doDoubleJump() {
		// TODO Auto-generated method stub
		
	}

}
