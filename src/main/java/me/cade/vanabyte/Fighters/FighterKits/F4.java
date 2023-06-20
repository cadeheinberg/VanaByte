package me.cade.vanabyte.Fighters.FighterKits;

import me.cade.vanabyte.Damaging.CreateExplosion;
import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.Fighters.FighterKit;
import me.cade.vanabyte.Fighters.FighterProjectile;
import me.cade.vanabyte.Fighters.Weapons.BeserkerAxe;
import me.cade.vanabyte.Fighters.Weapons.IgorsTrident;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.inventory.ItemStack;

public class F4 extends FighterKit {

	// General Kit Stuff
	static final int kitID = 4;
	static final String kitName = "Igor";
	static final ChatColor kitChatColor = ChatColor.RED;
	static final Color armorColor = Color.fromRGB(255, 15, 99);

	public F4() {
		super();
		weaponHolders.add(new IgorsTrident());
	}

	public F4(Fighter fighter) {
		super(fighter);
		weaponHolders.add(new IgorsTrident(fighter));
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
