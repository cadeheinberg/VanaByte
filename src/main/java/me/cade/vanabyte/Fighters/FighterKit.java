package me.cade.vanabyte.Fighters;
import me.cade.vanabyte.Fighters.Weapons.WeaponHolder;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

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

	public WeaponHolder getSpecificWeaponHolderIfItExists(Class<?> specificWeaponHolder) {
		for(WeaponHolder weaponHolder : weaponHolders){
			if(weaponHolder  == null){
				break;
			}
			if (specificWeaponHolder.isInstance(weaponHolder)){
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
