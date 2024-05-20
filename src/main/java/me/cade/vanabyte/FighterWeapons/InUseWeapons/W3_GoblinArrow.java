package me.cade.vanabyte.FighterWeapons.InUseWeapons;

import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.Fighters.FighterKitManager;
import org.bukkit.*;
import org.bukkit.entity.Player;

public class W3_GoblinArrow extends WeaponHolder {
    final String weaponDrop = "None";
    final String weaponRightClick = "None";
    final ChatColor weaponNameColor = ChatColor.GREEN;
    final String weaponName = weaponNameColor + "Goblin Sword";
    private Material material = null;
    private int abilityDurationTicks, abilityRechargeTicks, rightClickCooldownTicks = -1;
    private double specialDamage, meleeDamage, projectileDamage = -1;
    private FighterKitManager fighterKitManager = null;
    private Fighter fighter = null;
    private Player player = null;
    private Weapon weapon = null;
    public W3_GoblinArrow(Fighter fighter) {
        super(fighter);
        this.fighter = fighter;
        this.player = this.fighter.getPlayer();
        this.fighterKitManager = this.fighter.getFighterKitManager();
        this.meleeDamage = 0 + this.fighterKitManager.getKitUpgradesConvertedDamage(0, 0);;
        this.projectileDamage = 0 + this.fighterKitManager.getKitUpgradesConvertedDamage(0, 1);;
        this.specialDamage = 0 + this.fighterKitManager.getKitUpgradesConvertedDamage(0, 2);
        this.abilityDurationTicks = 0 + this.fighterKitManager.getKitUpgradesConvertedTicks(0, 3);
        this.abilityRechargeTicks = 0 - this.fighterKitManager.getKitUpgradesConvertedTicks(0, 4);
        this.rightClickCooldownTicks = 0 - this.fighterKitManager.getKitUpgradesConvertedTicks(0, 5);
        this.material = Material.ARROW;
        this.weapon = new Weapon(WeaponType.GOBLIN_ARROW, this.getMaterial(), this.weaponName, this.meleeDamage,
                this.getProjectileDamage(), this.getSpecialDamage(), this.getRightClickCooldownTicks(), this.getAbilityDurationTicks(),
                this.getAbilityRechargeTicks());
    }
    public W3_GoblinArrow(){
        super();
        this.meleeDamage = 0;
        this.projectileDamage = 0;
        this.specialDamage = 0;
        this.abilityDurationTicks = 0;
        this.abilityRechargeTicks = 0;
        this.rightClickCooldownTicks = 0;
        this.material = Material.ARROW;
        this.weapon = new Weapon(WeaponType.GOBLIN_ARROW, this.getMaterial(), this.weaponName, this.meleeDamage,
                this.getProjectileDamage(), this.getSpecialDamage(), this.getRightClickCooldownTicks(), this.getAbilityDurationTicks(),
                this.getAbilityRechargeTicks());
    }
    @Override
    public boolean doRightClick() {
        return false;
    }
    @Override
    public boolean doDrop() {
        return false;
    }
    @Override
    public void activateSpecial() {
        //pass
    }
    @Override
    public void deActivateSpecial() {
        //pass
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
    public Weapon getWeapon() {
        return weapon;
    }

    @Override
    public ChatColor getWeaponNameColor(){return weaponNameColor;}
}
