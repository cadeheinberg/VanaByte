package me.cade.vanabyte.BuildKits;

import me.cade.vanabyte.Damaging.CreateExplosion;
import me.cade.vanabyte.Damaging.DealDamage;
import me.cade.vanabyte.Fighter;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;

public class F4 extends FighterKit {

	// General Kit Stuff
	static final int kitID = 4;
	static final String kitName = "Igor";
	static final String kitDrop = "Explosive Tridents";
	static final String kitRightClick = "Throw Trident";
	static final ChatColor kitChatColor = ChatColor.RED;
	static final Color armorColor = Color.fromRGB(255, 15, 99);
	private int durationTicks;
	private int rechargeTicks;
	private double specialDamage;

	// Primary
	static final String weaponName = ChatColor.RED + "Igors Trident";
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
		this.durationTicks = 200;
		this.rechargeTicks = 50;
		this.meleeDamage = 6;
		this.projectileDamage = 3.5;
		this.specialDamage = 4;
		this.cooldownTicks = 5;
		this.material = Material.TRIDENT;
		this.primaryEnchantment = null;
		this.sceondaryMeleeDamage = 0;
		this.secondaryProjectileDamage = 0;
		this.secondaryCooldownTicks = 0;
		this.secondaryMaterial = null;
		secondaryEnchantment = null;
	}
	
	public F4() {
		super();
	}
	
	public F4(Player player) {
		super(player);
	}

	@Override
	public void loadSecondaryWeapon() {
		// pass
	}

	@Override
	public boolean doRightClick(Material material) {
		return super.doRightClick(material);
	}

	@Override
	public boolean doDrop(Material material, String displayName, int kitID) {
		// do special conditions before (right here)
		return super.doDrop(material, displayName, kitID);
	}

	@Override
	void activateSpecial() {
		super.player.playSound(super.player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 8, 1);
		super.activateSpecial();
	}

	@Override
	public void deActivateSpecial() {
		super.deActivateSpecial();
	}

	public boolean doThrowTrident(Trident trident) {
		if (player.getCooldown(this.getMaterial()) > 0) {
			return false;
		}
		player.setCooldown(this.getMaterial(), this.getCooldownTicks());
		if (Fighter.fighters.get((player).getUniqueId()).isAbilityActive()) {
			trident.setFireTicks(1000);
		}
		player.getInventory().remove(Material.TRIDENT);
		player.getInventory().addItem(this.getWeapons()[0].getWeaponItem());
		return true;
	}

	public void doTridentHitEntity(LivingEntity victim, Trident trident) {
		if (trident.getFireTicks() > 0) {
			Location local = victim.getLocation();
			local.setY(local.getY() - 0.5);
			CreateExplosion.doAnExplosion(super.player, local, 0.7, 6.5, true);
			trident.remove();
		} else {
			DealDamage.dealAmount(super.player, victim, this.getProjectileDamage());
		}
	}

	public void doTridentHitGround(Location location, Trident trident) {
		if (trident.getFireTicks() > 0) {
			CreateExplosion.doAnExplosion(super.player, location, 0.7, 6.5, true);
		}
		trident.remove();
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
