package me.cade.vanabyte.Fighters.FighterKits;

import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.Fighters.FighterKit;
import me.cade.vanabyte.Fighters.Weapons.*;
import org.bukkit.ChatColor;
import org.bukkit.Color;

public class F3 extends FighterKit {

	static final int kitID = 3;
	static final String kitName = "Goblin";
	static final ChatColor kitChatColor = ChatColor.GREEN;
	static final Color armorColor = Color.fromRGB(77, 255, 0);

	public F3() {
		super();
		weaponHolders.add(new W3_GoblinBow());
		weaponHolders.add(new W3_GoblinSword());
		weaponHolders.add(new W3_GoblinArrow());
	}

	public F3(Fighter fighter) {
		super(fighter);
		weaponHolders.add(new W3_GoblinBow(fighter));
		weaponHolders.add(new W3_GoblinSword(fighter));
		weaponHolders.add(new W3_GoblinArrow(fighter));
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
