package me.cade.vanabyte.Fighters.FighterKits;

import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.Fighters.FighterKit;
import me.cade.vanabyte.Fighters.Weapons.W4_IgorsTrident;
import org.bukkit.*;

public class F4 extends FighterKit {

	// General Kit Stuff
	static final int kitID = 4;
	static final String kitName = "Igor";
	static final ChatColor kitChatColor = ChatColor.RED;
	static final Color armorColor = Color.fromRGB(255, 15, 99);

	public F4() {
		super();
		weaponHolders.add(new W4_IgorsTrident());
	}

	public F4(Fighter fighter) {
		super(fighter);
		weaponHolders.add(new W4_IgorsTrident(fighter));
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
