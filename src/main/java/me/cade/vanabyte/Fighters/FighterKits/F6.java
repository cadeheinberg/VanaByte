package me.cade.vanabyte.Fighters.FighterKits;

import dev.esophose.playerparticles.particles.ParticleEffect;
import dev.esophose.playerparticles.styles.ParticleStyle;
import me.cade.vanabyte.SpecialItems.SetInvisible;
import me.cade.vanabyte.VanaByte;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class F6 extends FighterKit {

	// General Kit Stuff
	static final int kitID = 6;
	static final String kitName = "Grief";
	static final String kitDrop = "Invisibility & Health Steal";
	static final String kitRightClick = "Use Shield";
	static final ChatColor kitChatColor = ChatColor.AQUA;
	static final Color armorColor = Color.fromRGB(0, 0, 0);
	private int durationTicks;
	private int rechargeTicks;
	private double specialDamage;

	// Primary
	static final String weaponName = kitChatColor + "Grief Sword";
	private double meleeDamage;
	private double projectileDamage;
	private int cooldownTicks;
	private Material material;
	private EnchantmentPair primaryEnchantment;

	// Secondary
	static final String secondaryWeaponName = kitChatColor + "none";
	private double sceondaryMeleeDamage;
	private double secondaryProjectileDamage;
	private int secondaryCooldownTicks;
	private Material secondaryMaterial;
	private EnchantmentPair secondaryEnchantment;

	@Override
	public void setUpPrivateKitVariables() {
		if(this.fighterKitManager != null){
			this.meleeDamage = 8 + this.fighterKitManager.getKitUpgradesConvertedDamage(6, 0);;
			this.projectileDamage = 4 + this.fighterKitManager.getKitUpgradesConvertedDamage(6, 1);;
			this.specialDamage = 4 + this.fighterKitManager.getKitUpgradesConvertedDamage(6, 2);
			this.durationTicks = 200 + this.fighterKitManager.getKitUpgradesConvertedTicks(6, 3);
			this.rechargeTicks = 50 - this.fighterKitManager.getKitUpgradesConvertedTicks(6, 4);
			this.cooldownTicks = 0 - this.fighterKitManager.getKitUpgradesConvertedTicks(6, 5);
		}else{
			this.meleeDamage = 8;
			this.projectileDamage = 4;
			this.specialDamage = 4;
			this.durationTicks = 200;
			this.rechargeTicks = 50;
			this.cooldownTicks = 0;
		}
		this.material = Material.NETHERITE_SWORD;
		this.primaryEnchantment = null;
		this.sceondaryMeleeDamage = 0;
		this.secondaryProjectileDamage = 0;
		this.secondaryCooldownTicks = 0;
		this.secondaryMaterial = null;
		secondaryEnchantment = null;
	}
	
	public F6() {
		super();
	}
	
	public F6(Player player) {
		super(player);
	}
	
	@Override
	public void loadSecondaryWeapon() {
		//pass
	}

	@Override
	public boolean doRightClick(ItemStack item) {
		return super.doRightClick(item);
	}

	@Override
	public boolean doDrop(ItemStack item) {
		return super.doDrop(item);
	}

	@Override
	void activateSpecial() {
		super.player.setInvisible(true);
		SetInvisible.makeInvisible(super.player);
		super.player.playSound(super.player.getLocation(), Sound.ENTITY_GHAST_SCREAM, 8, 1);
		VanaByte.getPpAPI().addActivePlayerParticle(player, ParticleEffect.HEART, ParticleStyle.fromName("swords"));
		super.activateSpecial();
	}

	@Override
	public void deActivateSpecial() {
		super.player.setInvisible(false);
		SetInvisible.makeVisible(super.player);
		VanaByte.getPpAPI().removeActivePlayerParticles(player, ParticleEffect.HEART);
		super.deActivateSpecial();
	}

	public void doStealHealth(Player victim) {
		if (super.fighter.isAbilityActive()) {
			double combined = super.player.getHealth() + 1.5;
			if (combined > 20) {
				super.player.setHealth(20);
			} else {
				super.player.setHealth(combined);
			}
			super.player.playSound(super.player.getLocation(), Sound.ENTITY_ENDERMAN_HURT, 16, 1);
		}
	}

	/*
	 * Get Methods Get Methods Get Methods Get Methods Get Methods Get Methods Get
	 * Methods Get Methods Get Methods Get Methods Get Methods Get Methods Get
	 * Methods Get Methods Get Methods
	 */

	@Override
	public Material getMaterial() {
		return material;
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

	@Override
	public String getKitDrop() {
		return kitDrop;
	}

	@Override
	public String getKitRightClick() {
		return kitRightClick;
	}

	@Override
	public String getWeaponName() {
		return weaponName;
	}

	@Override
	public int getDurationTicks() {
		return durationTicks;
	}

	@Override
	public int getRechargeTicks() {
		if(rechargeTicks < 0){
			return 0;
		}
		return rechargeTicks;
	}

	@Override
	public double getProjectileDamage() {
		return projectileDamage;
	}

	@Override
	public double getMeleeDamage() {
		return meleeDamage;
	}

	@Override
	public double getSpecialDamage() {
		return specialDamage;
	}

	@Override
	public EnchantmentPair getPrimaryEnchantment() {
		return primaryEnchantment;
	}

	@Override
	public int getCooldownTicks() {
		if(cooldownTicks < 0){
			return 0;
		}
		return cooldownTicks;
	}
	
	public String getSecondaryWeaponName() {
		return secondaryWeaponName;
	}

	public double getSceondaryMeleeDamage() {
		return sceondaryMeleeDamage;
	}

	public double getSecondaryProjectileDamage() {
		return secondaryProjectileDamage;
	}

	public int getSecondaryCooldownTicks() {
		return secondaryCooldownTicks;
	}

	public EnchantmentPair getSecondaryEnchantment() {
		return secondaryEnchantment;
	}

	public Material getSecondaryMaterial() {
		return secondaryMaterial;
	}
}
