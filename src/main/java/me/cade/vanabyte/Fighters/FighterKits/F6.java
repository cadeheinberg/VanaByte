package me.cade.vanabyte.Fighters.FighterKits;

import dev.esophose.playerparticles.particles.ParticleEffect;
import dev.esophose.playerparticles.styles.ParticleStyle;
import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.Fighters.FighterKit;
import me.cade.vanabyte.Fighters.Weapons.GriefSword;
import me.cade.vanabyte.Fighters.Weapons.SumoStick;
import me.cade.vanabyte.VanaByte;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class F6 extends FighterKit {

	static final int kitID = 6;
	static final String kitName = "Grief";
	static final ChatColor kitChatColor = ChatColor.AQUA;
	static final Color armorColor = Color.fromRGB(0, 0, 0);

	public F6() {
		super();
		weaponHolders.add(new GriefSword());
	}

	public F6(Fighter fighter) {
		super(fighter);
		weaponHolders.add(new GriefSword(fighter));
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
