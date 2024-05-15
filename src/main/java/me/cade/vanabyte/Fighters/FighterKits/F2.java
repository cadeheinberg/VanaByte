package me.cade.vanabyte.Fighters.FighterKits;

import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.Fighters.FighterKit;
import me.cade.vanabyte.Fighters.Weapons.*;
import org.bukkit.*;

public class F2 extends FighterKit {

	static final int kitID = 2;
	static final String kitName = "Scorch";
	static final ChatColor kitChatColor = ChatColor.YELLOW;
	static final Color armorColor = Color.fromRGB(255, 255, 0);

	//default, used for stats about general kit and armor stands
	public F2() {
		super();
		weaponHolders.add(new W2_ShottyShotgun());
	}

	public F2(Fighter fighter) {
		super(fighter);
		weaponHolders.add(new W2_ShottyShotgun(fighter));
		weaponHolders.add(new S1_ThrowingTNT(fighter));
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
