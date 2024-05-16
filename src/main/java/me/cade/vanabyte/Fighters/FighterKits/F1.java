package me.cade.vanabyte.Fighters.FighterKits;

import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.Fighters.FighterKit;
import me.cade.vanabyte.FighterWeapons.InUseWeapons.W1_BeserkerAxe;
import org.bukkit.ChatColor;
import org.bukkit.Color;

public class F1 extends FighterKit {
	
	//general kit stuff
	static final int kitID = 1;
	static final String kitName = "Beserker";
	static final ChatColor kitChatColor = ChatColor.LIGHT_PURPLE;
	static final Color armorColor = Color.fromRGB(150, 0, 255);
	public F1() {
		super();
		weaponHolders.add(new W1_BeserkerAxe());
	}

	public F1(Fighter fighter) {
		super(fighter);
		weaponHolders.add(new W1_BeserkerAxe(fighter));
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
