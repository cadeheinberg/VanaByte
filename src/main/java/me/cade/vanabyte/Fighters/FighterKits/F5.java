package me.cade.vanabyte.Fighters.FighterKits;

import me.cade.vanabyte.Damaging.CreateExplosion;
import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.Fighters.FighterKit;
import me.cade.vanabyte.Fighters.Weapons.IgorsTrident;
import me.cade.vanabyte.Fighters.Weapons.SumoStick;
import me.cade.vanabyte.VanaByte;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class F5 extends FighterKit {

	// General Kit Stuff
	static final int kitID = 5;
	static final String kitName = "Sumo";
	static final ChatColor kitChatColor = ChatColor.BLUE;
	static final Color armorColor = Color.fromRGB(8, 111, 255);

	public F5() {
		super();
		weaponHolders.add(new SumoStick());
	}

	public F5(Fighter fighter) {
		super(fighter);
		weaponHolders.add(new SumoStick(fighter));
	}
	@Override
	public int getKitID() {
		return kitID;
	}

	@Override
	public ChatColor getKitChatColor() {
		return kitChatColor;
	}

	@Override
	public Color getArmorColor() {
		return armorColor;
	}

	@Override
	public String getKitName() {
		return kitName;
	}


}