package me.cade.vanabyte.Fighters;
import me.cade.vanabyte.FighterWeapons.InUseWeapons.WeaponHolder;
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

	public WeaponHolder getSimilarWeaponHolderFighterHas(ItemStack item){
		if(item == null || !item.hasItemMeta() || item.getItemMeta().getDisplayName() == null){
			return null;
		}
		for (WeaponHolder weaponHolder : this.weaponHolders){
			if(weaponHolder == null || weaponHolder.getWeapon() == null || weaponHolder.getWeapon().getWeaponItem() == null){
				continue;
			}
			if(weaponHolder.getWeapon().isSimilarToItem(item)){
				return weaponHolder;
			}
		}
		return null;
	}

	public WeaponHolder getSpecificWeaponHolderIfItExists(Class<?> specificWeaponHolder) {
		for(WeaponHolder weaponHolder : weaponHolders){
			if(weaponHolder  == null){
				continue;
			}
			if (specificWeaponHolder.isInstance(weaponHolder)){
				return weaponHolder;
			}
		}
		return null;
	}
	public WeaponHolder getSpecificSimilarWeaponHolderInHands(Class<?> specificWeaponHolder) {
		if(player.getEquipment().getItemInMainHand() == null || !player.getEquipment().getItemInMainHand().hasItemMeta() || player.getEquipment().getItemInMainHand().getItemMeta().getDisplayName() == null){
			return null;
		}
		for(WeaponHolder weaponHolder : weaponHolders){
			if(weaponHolder == null || weaponHolder.getWeapon() == null || weaponHolder.getWeapon().getWeaponItem() == null){
				continue;
			}
			if(!specificWeaponHolder.isInstance(weaponHolder)){
				continue;
			}
			if(weaponHolder.getWeapon().isSimilarToItem(player.getEquipment().getItemInMainHand())){
				return weaponHolder;
			}
		}
		if(player.getEquipment().getItemInOffHand() == null || !player.getEquipment().getItemInOffHand().hasItemMeta() || player.getEquipment().getItemInOffHand().getItemMeta().getDisplayName() == null){
			return null;
		}
		for(WeaponHolder weaponHolder : weaponHolders){
			if(weaponHolder == null || weaponHolder.getWeapon() == null || weaponHolder.getWeapon().getWeaponItem() == null){
				continue;
			}
			if(!specificWeaponHolder.isInstance(weaponHolder)){
				continue;
			}
			if(weaponHolder.getWeapon().isSimilarToItem(player.getEquipment().getItemInOffHand())){
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
