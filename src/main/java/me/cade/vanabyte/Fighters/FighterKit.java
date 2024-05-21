package me.cade.vanabyte.Fighters;
import me.cade.vanabyte.FighterWeapons.InUseWeapons.W5_SumoStick;
import me.cade.vanabyte.FighterWeapons.InUseWeapons.WeaponHolder;
import me.cade.vanabyte.FighterWeapons.InUseWeapons.WeaponType;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public abstract class FighterKit {

	Player player = null;
	Fighter fighter = null;
	FighterKitManager fighterKitManager = null;
	protected ArrayList<WeaponHolder> weaponHolders = new ArrayList<WeaponHolder>();

	public FighterKit() {
//		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Creating FighterKit without player");
	}

	public FighterKit(Fighter fighter) {
//		Bukkit.getConsoleSender()
//				.sendMessage(ChatColor.GREEN + "Creating FighterKit with player + " + player.getDisplayName());
		this.player = fighter.getPlayer();
		this.fighter = fighter;
		this.fighterKitManager = fighter.getFighterKitManager();
	}

	public Color getArmorColor() {
		return null;
	}

	public ArrayList<WeaponHolder> getWeaponHolders() {
		return weaponHolders;
	}

	public WeaponHolder getWeaponHolderWithType(WeaponType weaponType) {
		for (WeaponHolder weaponHolder : this.weaponHolders) {
			if (weaponHolder == null || weaponHolder.getWeapon() == null || weaponHolder.getWeapon().getWeaponItem() == null) {
				continue;
			}
			if (weaponHolder.getWeapon().getWeaponType() == weaponType) {
				return weaponHolder;
			}
		}
		return null;
	}

	public int getKitID() {
		return -1;
	}

	public Player getPlayer() {
		return this.player;
	}

	public String getKitName() {
		return null;
	}

	public ChatColor getKitChatColor() {
		// TODO Auto-generated method stub
		return null;
	}

}
