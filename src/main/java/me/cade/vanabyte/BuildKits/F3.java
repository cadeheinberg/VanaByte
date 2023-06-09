package me.cade.vanabyte.BuildKits;

import me.cade.vanabyte.Damaging.DealDamage;
import me.cade.vanabyte.Fighter;
import me.cade.vanabyte.Weapon;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class F3 extends FighterKit {

	static final int kitID = 3;
	static final String kitName = "Goblin";
	static final String kitDrop = "Barrage of Poison";
	static final String kitRightClick = "Shoot Bow";
	static final ChatColor kitChatColor = ChatColor.GREEN;
	static final Color armorColor = Color.fromRGB(77, 255, 0);
	private int durationTicks;
	private int rechargeTicks;
	private double specialDamage;

	// Primary
	static final String weaponName = kitChatColor + "Goblin Bow";
	private double meleeDamage;
	private double projectileDamage;
	private int cooldownTicks;
	private Material material;
	private EnchantmentPair primaryEnchantment;

	// Secondary
	static final String secondaryWeaponName = kitChatColor + "Goblin Sword";
	private double sceondaryMeleeDamage;
	private double secondaryProjectileDamage;
	private int secondaryCooldownTicks;
	private Material secondaryMaterial;
	private EnchantmentPair secondaryEnchantment;

	@Override
	public void setUpPrivateKitVariables() {
		this.durationTicks = 200;
		this.rechargeTicks = 50;
		this.meleeDamage = 0;
		this.projectileDamage = 2.5;
		this.specialDamage = 0.5;
		this.cooldownTicks = 5;
		this.material = Material.BOW;
		this.primaryEnchantment = new EnchantmentPair(Enchantment.ARROW_INFINITE, 1);
		this.sceondaryMeleeDamage = 6;
		this.secondaryProjectileDamage = 0;
		this.secondaryCooldownTicks = 0;
		this.secondaryMaterial = Material.WOODEN_SWORD;
		secondaryEnchantment = new EnchantmentPair(Enchantment.FIRE_ASPECT, 2);
	}
	
	public F3() {
		super();
	}
	
	public F3(Player player) {
		super(player);
	}

	@Override
	public void loadSecondaryWeapon() {
		super.weapons[1] = new Weapon(this.secondaryMaterial, this.getSecondaryWeaponName(), 
				this.getSceondaryMeleeDamage(), this.getSecondaryProjectileDamage(), 
				this.getSpecialDamage(), this.getSecondaryCooldownTicks(),
				this.getDurationTicks(), this.getRechargeTicks(),
				this.getSecondaryEnchantment(), super.perkEnchantment1);
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

	public void doArrorwHitEntity(LivingEntity victim, Arrow arrow) {
		// create your own form of knockback
		if (victim instanceof Player) {
			Fighter.get(player).fighterDismountParachute();
			victim.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 120, 2));
		}
		if(Fighter.get(player).isAbilityActive()){
			DealDamage.dealAmount(player, victim, this.getSpecialDamage());
		}else {
			DealDamage.dealAmount(player, victim, this.getProjectileDamage());
		}
		if (arrow.getFireTicks() > 0) {
			victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 120, 2));
		}
	}

	public boolean doArrowShoot(Arrow arrow, double force) {
		    if (super.player.getCooldown(this.getMaterial()) > 0) {
		      return false;
		    }
		    super.player.setCooldown(this.getMaterial(), this.getCooldownTicks());
		    if (Fighter.get(super.player).isAbilityActive()) {
		      arrow.setFireTicks(1000);
		      doArrowBarrage(super.player, arrow, force);
		    }
		    return true;
		  }

	public static void doArrowBarrage(Player player, Arrow arrow, double force) {
		if (force > 0.75) {
			Arrow arrow1 = player.launchProjectile(Arrow.class);
			arrow1.setVelocity(arrow.getVelocity().add(new Vector(0, 0.25, 0)));
			arrow1.setFireTicks(2000);
			arrow1.setShooter(player);
			FighterProjectile.addMetadataToProjectile(arrow1);

			Arrow arrow2 = player.launchProjectile(Arrow.class);
			arrow2.setVelocity(arrow.getVelocity().add(new Vector(0, -0.25, 0)));
			arrow2.setFireTicks(2000);
			arrow2.setShooter(player);
			FighterProjectile.addMetadataToProjectile(arrow2);

			Arrow arrow3 = player.launchProjectile(Arrow.class);
			arrow3.setVelocity(arrow.getVelocity().add(new Vector(0.25, 0, 0)));
			arrow3.setFireTicks(2000);
			arrow3.setShooter(player);
			FighterProjectile.addMetadataToProjectile(arrow3);

			Arrow arrow4 = player.launchProjectile(Arrow.class);
			arrow4.setVelocity(arrow.getVelocity().add(new Vector(-0.25, 0, 0)));
			arrow4.setFireTicks(2000);
			arrow4.setShooter(player);
			FighterProjectile.addMetadataToProjectile(arrow4);

			Arrow arrow5 = player.launchProjectile(Arrow.class);
			arrow5.setVelocity(arrow.getVelocity().add(new Vector(0, 0, 0.25)));
			arrow5.setFireTicks(2000);
			arrow5.setShooter(player);
			FighterProjectile.addMetadataToProjectile(arrow5);

			Arrow arrow6 = player.launchProjectile(Arrow.class);
			arrow6.setVelocity(arrow.getVelocity().add(new Vector(0, 0, -0.25)));
			arrow6.setFireTicks(2000);
			arrow6.setShooter(player);
			FighterProjectile.addMetadataToProjectile(arrow6);
			return;
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
	
	@Override
	public Material getSecondaryMaterial() {
		return secondaryMaterial;
	}
	

}
