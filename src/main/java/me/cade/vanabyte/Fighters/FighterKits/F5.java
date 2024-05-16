package me.cade.vanabyte.Fighters.FighterKits;

import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.Fighters.FighterKit;
import me.cade.vanabyte.FighterWeapons.InUseWeapons.W5_SumoStick;
import org.bukkit.*;

public class F5 extends FighterKit {

	// General Kit Stuff
	static final int kitID = 5;
	static final String kitName = "Sumo";
	static final ChatColor kitChatColor = ChatColor.BLUE;
	static final Color armorColor = Color.fromRGB(8, 111, 255);

	public F5() {
		super();
		weaponHolders.add(new W5_SumoStick());
	}

	public F5(Fighter fighter) {
		super(fighter);
		weaponHolders.add(new W5_SumoStick(fighter));
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