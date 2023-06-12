package me.cade.vanabyte.BuildKits;

import me.cade.vanabyte.Damaging.CreateExplosion;
import me.cade.vanabyte.Fighter;
import me.cade.vanabyte.VanaByte;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class F5 extends FighterKit {

	// General Kit Stuff
	static final int kitID = 5;
	static final String kitName = "Sumo";
	static final String kitDrop = "Ground Pound";
	static final String kitRightClick = "Toss Enemy";
	static final ChatColor kitChatColor = ChatColor.BLUE;
	static final Color armorColor = Color.fromRGB(8, 111, 255);
	private int durationTicks;
	private int rechargeTicks;
	private double specialDamage;

	// Primary
	static final String weaponName = ChatColor.BLUE + "Sumo Stick";
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
			this.meleeDamage = 5 + this.pFight.getKitUpgradesConvertedDamage(5, 0);;
			this.projectileDamage = 4 + this.pFight.getKitUpgradesConvertedDamage(5, 1);;
			this.specialDamage = 10 + this.pFight.getKitUpgradesConvertedDamage(5, 2);
			this.durationTicks = 200 + this.pFight.getKitUpgradesConvertedTicks(5, 3);
			this.rechargeTicks = 50 - this.pFight.getKitUpgradesConvertedTicks(5, 4);
			this.cooldownTicks = 180 - this.pFight.getKitUpgradesConvertedTicks(5, 5);
		}else{
			this.meleeDamage = 5;
			this.projectileDamage = 4;
			this.specialDamage = 10;
			this.durationTicks = 200;
			this.rechargeTicks = 50;
			this.cooldownTicks = 180;
		}
		this.material = Material.BLAZE_ROD;
		this.primaryEnchantment = new EnchantmentPair(Enchantment.KNOCKBACK, 3);
		this.sceondaryMeleeDamage = 0;
		this.secondaryProjectileDamage = 0;
		this.secondaryCooldownTicks = 0;
		this.secondaryMaterial = null;
		secondaryEnchantment = null;
	}

	public F5() {
		super();
	}

	public F5(Player player) {
		super(player);
	}

	@Override
	public void loadSecondaryWeapon() {
		// pass
	}

	@Override
	public boolean doRightClick(Material material) {
		if (material == this.material) {
			// see pickUp method, we don't do this normally
			return true;
		}
		return super.doRightClick(material);
	}

	@Override
	public boolean doDrop(Material material, String displayName, int kitID) {
		// do special conditions before (right here)
		return super.doDrop(material, displayName, kitID);
	}

	@Override
	void activateSpecial() {
		super.activateSpecial();
		super.player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, durationTicks, 0));
		super.player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, durationTicks, 1));
		doJump(super.player, Fighter.get(super.player));
		super.player.playSound(super.player.getLocation(), Sound.ENTITY_GHAST_SCREAM, 8, 1);
	}

	@Override
	public void deActivateSpecial() {
		super.deActivateSpecial();
	}

	public void doJump(Player player, Fighter pFight) {
		player.playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 8, 1);
		launchPlayer(player, 1.4, pFight);
	}

	public void launchPlayer(Player player, Double power, Fighter pFight) {
		Location local = player.getLocation();
		local.setPitch(-60);
		Vector currentDirection = local.getDirection().normalize();
		currentDirection = currentDirection.multiply(new Vector(power, power, power));
		player.setVelocity(currentDirection);
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(VanaByte.getInstance(), new Runnable() {
			@Override
			public void run() {
				listenForFall(player, pFight);
			}
		}, 5);
	}

	public void listenForFall(Player player, Fighter pFight) {
		pFight.setGroundPoundTask(new BukkitRunnable() {
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				if (player == null) {
					stopListening(pFight);
					return;
				}
				if (!player.isOnline()) {
					stopListening(pFight);
					return;
				}
				if (player.isDead()) {
					stopListening(pFight);
					return;
				}
				if (player.isOnGround()) {
					stopListening(pFight);
					doGroundHit(player, player.getLocation(), 0.3);
					return;
				}
				if (player.isSneaking()) {
					launchPlayerDown(player, 1.5, pFight);
				}
			}
		}.runTaskTimer(VanaByte.getInstance(), 0L, 1L).getTaskId());
	}

	public static void launchPlayerDown(Player player, Double power, Fighter pFight) {
		Location local = player.getLocation();
		local.setPitch(80);
		Vector currentDirection = local.getDirection().normalize();
		currentDirection = currentDirection.multiply(new Vector(power, power, power));
		player.setVelocity(currentDirection);
	}

	public static void stopListening(Fighter pFight) {
		Bukkit.getScheduler().cancelTask(pFight.getGroundPoundTask());
		pFight.setGroundPoundTask(-1);
	}

	// make this freeze players also
	public void doGroundHit(Player shooter, Location location, double power) {
		CreateExplosion.doAnExplosion(shooter, location, 0.7, this.getSpecialDamage(), true);
	}

	@Override
	public void doPickUp(LivingEntity rightClicked) {
		if (player.getPassengers() == null) {
			return;
		}
		if (player.getPassengers().size() >= 1) {
			return;
		}
		if (rightClicked instanceof Player) {
			if (((Player) rightClicked).isSneaking()) {
				return;
			}
		}
		if(player.getInventory().getItemInMainHand().getType() != this.material) {
			return;
		}
		if (this.getCooldownTicks() > 0) {
			if (player.getCooldown(this.getMaterial()) > 0) {
				return;
			}
			player.setCooldown(this.getMaterial(), this.getCooldownTicks());
			player.addPassenger(rightClicked);
			return;
		}
	}

	@Override
	public void doThrow(Player killer, LivingEntity victim) {
		killer.eject();
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(VanaByte.getInstance(), new Runnable() {
			@Override
			public void run() {
				Location playerLocation = killer.getLocation();
				if (playerLocation.getPitch() < -60) {
					playerLocation.setPitch((float) -60.0);
				}
				Vector currentDirection = playerLocation.getDirection().normalize();
				currentDirection = currentDirection.multiply(new Vector(2, 2, 2));
				victim.setVelocity(currentDirection);
			}
		}, 1);
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