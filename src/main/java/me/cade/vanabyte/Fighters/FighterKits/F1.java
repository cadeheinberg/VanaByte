package me.cade.vanabyte.Fighters.FighterKits;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class F1 extends FighterKit {
	
	//general kit stuff
	static final int kitID = 1;
	static final String kitName = "Beserker";
	static final String kitDrop = "Speed/Jump Boost";
	static final String kitRightClick = "Super Leap";
	static final ChatColor kitChatColor = ChatColor.LIGHT_PURPLE;
	static final Color armorColor = Color.fromRGB(150, 0, 255);
	private int durationTicks;
	private int rechargeTicks;
	private double specialDamage;

	// Primary
	static final String weaponName = kitChatColor + "Beserker Axe";
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
			this.meleeDamage = 6 + this.fighterKitManager.getKitUpgradesConvertedDamage(1, 0);;
			this.projectileDamage = 0 + this.fighterKitManager.getKitUpgradesConvertedDamage(1, 1);;
			this.specialDamage = 0 + this.fighterKitManager.getKitUpgradesConvertedDamage(1, 2);
			this.durationTicks = 200 + this.fighterKitManager.getKitUpgradesConvertedTicks(1, 3);
			this.rechargeTicks = 50 - this.fighterKitManager.getKitUpgradesConvertedTicks(1, 4);
			this.cooldownTicks = 140 - this.fighterKitManager.getKitUpgradesConvertedTicks(1, 5);
		}else{
			this.meleeDamage = 6;
			this.projectileDamage = 0;
			this.specialDamage = 0;
			this.durationTicks = 200;
			this.rechargeTicks = 50;
			this.cooldownTicks = 140;
		}
		this.material = Material.IRON_AXE;
		this.primaryEnchantment = null;
		this.sceondaryMeleeDamage = 0;
		this.secondaryProjectileDamage = 0;
		this.secondaryCooldownTicks = 0;
		this.secondaryMaterial = null;
		secondaryEnchantment = null;
	}

	public F1() {
		super();
	}
	
	public F1(Player player) {
		super(player);
	}

	@Override
	public void loadSecondaryWeapon() {
		// pass
	}

	@Override
	public boolean doRightClick(ItemStack item) {
		if (super.doRightClick(item)) {
			this.doBoosterJump();
			return true;
		}
		return false;
	}

	@Override
	public boolean doDrop(ItemStack item) {
		// do special conditions before (right here)
		return super.doDrop(item);
	}

	@Override
	void activateSpecial() {
		super.activateSpecial();
		super.player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, durationTicks, 0));
		super.player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, durationTicks, 3));
		super.player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, durationTicks, 0));
		super.player.playSound(super.player.getLocation(), Sound.ENTITY_GHAST_SCREAM, 8, 1);
	}

	@Override
	public void deActivateSpecial() {
		super.deActivateSpecial();
	}

	private void doBoosterJump() {
		this.player.playSound(this.player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 8, 1);
		Vector currentDirection = this.player.getLocation().getDirection().normalize();
		currentDirection = currentDirection.multiply(new Vector(1.7, 1.7, 1.7));
		this.player.setVelocity(currentDirection);
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
