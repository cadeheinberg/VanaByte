package me.cade.vanabyte.Fighters.FighterKits;

import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.Fighters.FighterKit;
import me.cade.vanabyte.Fighters.FighterProjectile;
import me.cade.vanabyte.Fighters.Weapons.*;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class F3 extends FighterKit {

	static final int kitID = 3;
	static final String kitName = "Goblin";
	static final ChatColor kitChatColor = ChatColor.GREEN;
	static final Color armorColor = Color.fromRGB(77, 255, 0);

	public F3() {
		super();
		weaponHolders.add(new GoblinBow());
		weaponHolders.add(new GoblinSword());
		weaponHolders.add(new GoblinArrow());
	}

	public F3(Fighter fighter) {
		super(fighter);
		weaponHolders.add(new GoblinBow(fighter));
		weaponHolders.add(new GoblinSword(fighter));
		weaponHolders.add(new GoblinArrow(fighter));
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
