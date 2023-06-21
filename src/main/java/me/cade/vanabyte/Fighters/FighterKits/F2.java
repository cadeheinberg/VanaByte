package me.cade.vanabyte.Fighters.FighterKits;

import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.Fighters.FighterKit;
import me.cade.vanabyte.Fighters.FighterProjectile;
import me.cade.vanabyte.Fighters.Weapons.*;
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
		weaponHolders.add(new IgorsTrident());
		weaponHolders.add(new AirbenderSword());
		weaponHolders.add(new BeserkerAxe());
		weaponHolders.add(new SumoStick());
		weaponHolders.add(new GriefSword());
		weaponHolders.add(new ThrowingTNTItem());
		weaponHolders.add(new ParachuteItem());
	}

	public F2(Fighter fighter) {
		super(fighter);
		weaponHolders.add(new ShottyShotgun(fighter));
		weaponHolders.add(new IgorsTrident(fighter));
		weaponHolders.add(new AirbenderSword(fighter));
		weaponHolders.add(new BeserkerAxe(fighter));
		weaponHolders.add(new SumoStick(fighter));
		weaponHolders.add(new GriefSword(fighter));
		weaponHolders.add(new ThrowingTNTItem(fighter));
		weaponHolders.add(new ParachuteItem(fighter));
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
