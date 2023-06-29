package me.cade.vanabyte.Fighters.FighterKits;

import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.Fighters.FighterKit;
import me.cade.vanabyte.Fighters.Weapons.W6_GriefSword;
import org.bukkit.ChatColor;
import org.bukkit.Color;

public class F6 extends FighterKit {

	static final int kitID = 6;
	static final String kitName = "Grief";
	static final ChatColor kitChatColor = ChatColor.AQUA;
	static final Color armorColor = Color.fromRGB(0, 0, 0);

	public F6() {
		super();
		weaponHolders.add(new W6_GriefSword());
	}

	public F6(Fighter fighter) {
		super(fighter);
		weaponHolders.add(new W6_GriefSword(fighter));
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
