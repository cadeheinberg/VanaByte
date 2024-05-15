package me.cade.vanabyte.Fighters.FighterKits;

import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.Fighters.FighterKit;
import me.cade.vanabyte.Fighters.Weapons.S0_Parachute;
import me.cade.vanabyte.Fighters.Weapons.W0_AirbenderSword;
import org.bukkit.*;

public class F0 extends FighterKit {
	static final int kitID = 0;
	static final String kitName = "Airbender";
	static final ChatColor kitChatColor = ChatColor.WHITE;
	static final Color armorColor = Color.fromRGB(255, 255, 255);

	public F0() {
		super();
		weaponHolders.add(new W0_AirbenderSword());
	}

	public F0(Fighter fighter) {
		super(fighter);
		weaponHolders.add(new W0_AirbenderSword(fighter));
		weaponHolders.add(new S0_Parachute(fighter));
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
