package me.cade.vanabyte.Fighters.FighterKits;

import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.Fighters.FighterKit;
import me.cade.vanabyte.Fighters.FighterProjectile;
import me.cade.vanabyte.Fighters.Weapons.AirbenderSword;
import me.cade.vanabyte.Fighters.Weapons.ShottyShotgun;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class F2 extends FighterKit {

	static final int kitID = 2;
	static final String kitName = "Scorch";
	static final ChatColor kitChatColor = ChatColor.YELLOW;
	static final Color armorColor = Color.fromRGB(255, 255, 0);

	public F2() {
		super();
		weaponHolders.add(new ShottyShotgun());
	}

	public F2(Fighter fighter) {
		super(fighter);
		weaponHolders.add(new ShottyShotgun(fighter));
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
