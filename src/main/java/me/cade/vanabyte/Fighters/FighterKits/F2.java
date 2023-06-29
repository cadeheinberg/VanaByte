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

	public F2() {
		super();
		weaponHolders.add(new W2_ShottyShotgun());
		weaponHolders.add(new W4_IgorsTrident());
		weaponHolders.add(new W0_AirbenderSword());
		weaponHolders.add(new W1_BeserkerAxe());
		weaponHolders.add(new W5_SumoStick());
		weaponHolders.add(new W6_GriefSword());
		weaponHolders.add(new S1_ThrowingTNT());
		weaponHolders.add(new S0_Parachute());
	}

	public F2(Fighter fighter) {
		super(fighter);
		weaponHolders.add(new W2_ShottyShotgun(fighter));
		weaponHolders.add(new W4_IgorsTrident(fighter));
		weaponHolders.add(new W0_AirbenderSword(fighter));
		weaponHolders.add(new W1_BeserkerAxe(fighter));
		weaponHolders.add(new W5_SumoStick(fighter));
		weaponHolders.add(new W6_GriefSword(fighter));
		weaponHolders.add(new S1_ThrowingTNT(fighter));
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
