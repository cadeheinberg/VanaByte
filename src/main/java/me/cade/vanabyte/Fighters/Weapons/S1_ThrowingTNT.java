package me.cade.vanabyte.Fighters.Weapons;

import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.Fighters.FighterKitManager;
import me.cade.vanabyte.VanaByte;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

public class S1_ThrowingTNT extends WeaponHolder {
	final String weaponDrop = "Throw TNT";
	final String weaponRightClick = "Throw TNT";
	final ChatColor weaponNameColor = ChatColor.YELLOW;
	final String weaponName = weaponNameColor + "Throwing TNT";
	private Material material = null;
	private int abilityDurationTicks, abilityRechargeTicks, rightClickCooldownTicks = -1;
	private double specialDamage, meleeDamage, projectileDamage = -1;
	private FighterKitManager fighterKitManager = null;
	private Fighter fighter = null;
	private Player player = null;
	private Weapon weapon = null;
	public S1_ThrowingTNT(Fighter fighter) {
		super(fighter);
		this.fighter = fighter;
		this.player = this.fighter.getPlayer();
		this.fighterKitManager = this.fighter.getFighterKitManager();
		this.meleeDamage = 5 + this.fighterKitManager.getKitUpgradesConvertedDamage(0, 0);;
		this.projectileDamage = 10 + this.fighterKitManager.getKitUpgradesConvertedDamage(0, 1);;
		this.specialDamage = 12 + this.fighterKitManager.getKitUpgradesConvertedDamage(0, 2);
		this.abilityDurationTicks = 200 + this.fighterKitManager.getKitUpgradesConvertedTicks(0, 3);
		this.abilityRechargeTicks = 50 - this.fighterKitManager.getKitUpgradesConvertedTicks(0, 4);
		this.rightClickCooldownTicks = 5 - this.fighterKitManager.getKitUpgradesConvertedTicks(0, 5);
		this.material = Material.COAL;
		this.weapon = new Weapon(this.getMaterial(), this.weaponName, this.meleeDamage,
				this.getProjectileDamage(), this.getSpecialDamage(), this.getRightClickCooldownTicks(), this.getAbilityDurationTicks(),
				this.getAbilityRechargeTicks());
	}
	public S1_ThrowingTNT(){
		super();
		this.meleeDamage = 6;
		this.projectileDamage = 0;
		this.specialDamage = 7;
		this.abilityDurationTicks = 200;
		this.abilityRechargeTicks = 50;
		this.rightClickCooldownTicks = 0;
		this.material = Material.IRON_SWORD;
		this.weapon = new Weapon(this.getMaterial(), this.weaponName, this.meleeDamage,
				this.getProjectileDamage(), this.getSpecialDamage(), this.getRightClickCooldownTicks(), this.getAbilityDurationTicks(),
				this.getAbilityRechargeTicks());
	}
	@Override
	public boolean doRightClick() {
		if(!super.doRightClick()) {
			return false;
		}
		this.doThrowingTNT();
		return true;
	}
	@Override
	public boolean doDrop() {
		if(!super.doDrop()){
			return false;
		}
		return this.doRightClick();
	}
	@Override
	public void activateSpecial() {
		super.activateSpecial();
	}
	@Override
	public void deActivateSpecial() {
		super.deActivateSpecial();
	}
	private void doThrowingTNT() {
		Entity tnt = player.getWorld().spawnEntity(player.getEyeLocation(), EntityType.PRIMED_TNT);
		TNTPrimed fuse = (TNTPrimed) tnt;
		tnt.setCustomName(this.player.getName());
		tnt.setCustomNameVisible(false);
		tnt.setMetadata("thrower", new FixedMetadataValue(VanaByte.getInstance(), this.player.getName()));
		fuse.setFuseTicks(15);
		Vector currentDirection4 = player.getLocation().getDirection().normalize();
		currentDirection4 = currentDirection4.multiply(new Vector(1, 1, 1));
		tnt.setVelocity(currentDirection4);
	}
	@Override
	public String getWeaponDrop() {
		return weaponDrop;
	}

	@Override
	public String getWeaponRightClick() {
		return weaponRightClick;
	}

	@Override
	public String getWeaponName() {return weaponName;}

	@Override
	public Material getMaterial() {
		return material;
	}

	@Override
	public int getRightClickCooldownTicks() {
		return rightClickCooldownTicks;
	}

	@Override
	public int getAbilityDurationTicks() {
		return abilityDurationTicks;
	}

	@Override
	public int getAbilityRechargeTicks() {
		return abilityRechargeTicks;
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
	public ChatColor getWeaponNameColor(){return weaponNameColor;}

	@Override
	public Weapon getWeapon(){
		return weapon;
	}
}
