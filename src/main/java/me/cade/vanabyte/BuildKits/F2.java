package me.cade.vanabyte.BuildKits;

import me.cade.vanabyte.Fighter;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.util.Vector;

public class F2 extends FighterKit {

	// General Kit Stuff
	static final int kitID = 2;
	static final String kitName = "Scorch";
	static final String kitDrop = "Flaming Bullets";
	static final String kitRightClick = "Shoot Shotgun";
	static final ChatColor kitChatColor = ChatColor.YELLOW;
	static final Color armorColor = Color.fromRGB(255, 255, 0);
	private int durationTicks;
	private int rechargeTicks;
	private double specialDamage;

	// Primary
	static final String weaponName = kitChatColor + "Scorch Shotgun";
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
		if(this.pFight != null){
			this.meleeDamage = 6 + this.pFight.getKitUpgradesConvertedDamage(2, 0);;
			this.projectileDamage = 12 + this.pFight.getKitUpgradesConvertedDamage(2, 1);;
			this.specialDamage = 12 + this.pFight.getKitUpgradesConvertedDamage(2, 2);
			this.durationTicks = 200 + this.pFight.getKitUpgradesConvertedTicks(2, 3);
			this.rechargeTicks = 50 - this.pFight.getKitUpgradesConvertedTicks(2, 4);
			this.cooldownTicks = 30 - this.pFight.getKitUpgradesConvertedTicks(2, 5);
		}else{
			this.meleeDamage = 6;
			this.projectileDamage = 12;
			this.specialDamage = 12;
			this.durationTicks = 200;
			this.rechargeTicks = 50;
			this.cooldownTicks = 30;
		}
		this.material = Material.IRON_SHOVEL;
		this.primaryEnchantment = null;
		this.sceondaryMeleeDamage = 0;
		this.secondaryProjectileDamage = 0;
		this.secondaryCooldownTicks = 0;
		this.secondaryMaterial = null;
		secondaryEnchantment = null;
	}
	
	public F2() {
		super();
	}
	
	public F2(Player player) {
		super(player);
	}

	@Override
	public void loadSecondaryWeapon() {
		// pass
	}


	@Override
	public boolean doRightClick(Material material) {
		if (super.doRightClick(material)) {
			shootSnowballs(player);
			launchPlayer(player, -0.6);
			return true;
		}
		return false;
	}

	@Override
	public boolean doDrop(Material material, String displayName, int kitID) {
		// do special conditions before (right here)
		return super.doDrop(material, displayName, kitID);
	}

	@Override
	void activateSpecial() {
		// reload weapon on usage of ability
		super.player.setCooldown(this.getMaterial(), 0);
		super.activateSpecial();
		super.player.playSound(super.player.getLocation(), Sound.ENTITY_ENDERMAN_SCREAM, 8, 1);
	}

	@Override
	public void deActivateSpecial() {
		super.deActivateSpecial();
	}

	public double doSnowballHitEntity(LivingEntity victim, Snowball snowball) {
		if (snowball.getFireTicks() > 0) {
			victim.setFireTicks(50);
			return this.getSpecialDamage();
		}
		return this.getProjectileDamage();
	}

	public void doSnowballHitGround(Location location, Snowball snowball) {
		if (snowball.getFireTicks() > 0) {

		}
	}

	public static void launchPlayer(Player player, Double power) {
		Vector currentDirection = player.getLocation().getDirection().normalize();
		currentDirection = currentDirection.multiply(new Vector(power, power, power));
		player.setVelocity(currentDirection);
	}

	public static void shootSnowballs(Player player) {
		player.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 8, 1);
		
		Snowball ball = player.launchProjectile(Snowball.class);
		ball.setVelocity(ball.getVelocity().add(new Vector(0, 0.25, 0)));
		FighterProjectile.addMetadataToProjectile(ball);
		ball.setShooter(player);

		Snowball ball2 = player.launchProjectile(Snowball.class);
		ball2.setVelocity(ball2.getVelocity().add(new Vector(0, -0.25, 0)));
		FighterProjectile.addMetadataToProjectile(ball2);
		ball2.setShooter(player);

		Snowball ball3 = player.launchProjectile(Snowball.class);
		ball3.setVelocity(ball3.getVelocity().add(new Vector(0.25, 0, 0)));
		FighterProjectile.addMetadataToProjectile(ball3);
		ball3.setShooter(player);

		Snowball ball4 = player.launchProjectile(Snowball.class);
		ball4.setVelocity(ball4.getVelocity().add(new Vector(-0.25, 0, 0)));
		FighterProjectile.addMetadataToProjectile(ball4);
		ball4.setShooter(player);

		Snowball ball5 = player.launchProjectile(Snowball.class);
		ball5.setVelocity(ball5.getVelocity().add(new Vector(0, 0, 0.25)));
		FighterProjectile.addMetadataToProjectile(ball5);
		ball5.setShooter(player);

		Snowball ball6 = player.launchProjectile(Snowball.class);
		ball6.setVelocity(ball6.getVelocity().add(new Vector(0, 0, -0.25)));
		FighterProjectile.addMetadataToProjectile(ball6);
		ball6.setShooter(player);
		
	    if (Fighter.fighters.get(player.getUniqueId()).isAbilityActive()) {
			ball.setFireTicks(1000);
			ball2.setFireTicks(1000);
			ball3.setFireTicks(1000);
			ball4.setFireTicks(1000);
			ball5.setFireTicks(1000);
			ball6.setFireTicks(1000);
	      }
	    
		return;
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
