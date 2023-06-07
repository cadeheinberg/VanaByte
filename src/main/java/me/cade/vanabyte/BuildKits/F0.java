package me.cade.vanabyte.BuildKits;

import me.cade.vanabyte.Damaging.DealDamage;
import org.bukkit.*;
import org.bukkit.Particle.DustOptions;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class F0 extends FighterKit {

	static final int kitID = 0;
	static final String kitName = "Airbender";
	static final String kitDrop = "Gust of Wind";
	static final String kitRightClick = "Use Shield";
	static final ChatColor kitChatColor = ChatColor.WHITE;
	static final Color armorColor = Color.fromRGB(255, 255, 255);
	private int durationTicks;
	private int rechargeTicks;
	private double specialDamage;

	// Primary
	static final String weaponName = kitChatColor + "Airbender Sword";
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
		// for when you add enhancements, make sure you check if the player is null or
		// not
		// because the kit armor stands use this
		this.durationTicks = 200;
		this.rechargeTicks = 50;
		this.meleeDamage = 6;
		this.projectileDamage = 0;
		this.specialDamage = 1;
		this.cooldownTicks = 0;
		this.material = Material.IRON_SWORD;
		this.primaryEnchantment = null;
		this.sceondaryMeleeDamage = 0;
		this.secondaryProjectileDamage = 0;
		this.secondaryCooldownTicks = 0;
		this.secondaryMaterial = null;
		secondaryEnchantment = null;
	}

	public F0() {
		super();
	}

	public F0(Player player) {
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
		super.activateSpecial();
		this.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, durationTicks, 1));
		this.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, durationTicks, 1));
		gustLaunch(this.getPlayer());
		super.player.playSound(super.player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 8, 1);
	}

	@Override
	public void deActivateSpecial() {
		super.deActivateSpecial();
	}

	public static void gustLaunch(Player killer) {
		Location playerLocation = killer.getLocation();
		if (playerLocation.getPitch() > 49) {
			launchPlayer(killer, -1.5);
			return;
		}
		Location origin = killer.getEyeLocation();
		Vector direction = killer.getLocation().getDirection();
		double dX = direction.getX();
		double dY = direction.getY();
		double dZ = direction.getZ();
		playerLocation.setPitch((float) -30.0);
		int range = 13;
		double power = 2.8;
		ArrayList<Integer> hitList = new ArrayList<Integer>();
		for (int j = 2; j < range; j++) {
			origin = origin.add(dX * j, dY * j, dZ * j);
			killer.spawnParticle(Particle.REDSTONE, origin, 100, 0.5, 0.75, 0.5, new DustOptions(Color.fromRGB(255, 255, 255), 1.0F));
			ArrayList<Entity> entityList = (ArrayList<Entity>) killer.getWorld().getNearbyEntities(origin, 2.5, 2.5,
					2.5);
			for (Entity entity : entityList) {
				//Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "1 Living Entity Found");
				if (!(entity instanceof LivingEntity)) {
					continue;
				} else if (hitList.contains(((LivingEntity) entity).getEntityId())) {
					continue;
				} else if (killer.getName().equals(((LivingEntity) entity).getName())) {
					continue;
				}
				if (!(entity instanceof Player)) {
					if(((Player) ((LivingEntity) entity)).isSneaking()){
						continue;
					}
				}
				hitList.add(((LivingEntity) entity).getEntityId());
				DealDamage.dealAmount(killer, (LivingEntity) entity, 0);
				//killer.sendMessage("doing gust one 1 entity");
				Vector currentDirection = playerLocation.getDirection().normalize();
				currentDirection = currentDirection.multiply(new Vector(power, power, power));
				entity.setVelocity(currentDirection);

			}
			origin = origin.subtract(dX * j, dY * j, dZ * j);
		}
	}

	public static void launchPlayer(Player player, Double power) {
		player.spawnParticle(Particle.REDSTONE, player.getLocation(), 100, 0.5, 0.5, 0.5, new DustOptions(Color.fromRGB(255, 255, 255), 1.0F));
		Vector currentDirection = player.getLocation().getDirection().normalize();
		currentDirection = currentDirection.multiply(new Vector(power, power, power));
		player.setVelocity(currentDirection);
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
