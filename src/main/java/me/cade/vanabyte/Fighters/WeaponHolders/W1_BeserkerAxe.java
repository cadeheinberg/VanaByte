package me.cade.vanabyte.Fighters.WeaponHolders;

import me.cade.vanabyte.Fighters.Enums.WeaponType;
import me.cade.vanabyte.Fighters.Fighter;
import org.bukkit.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class W1_BeserkerAxe extends WeaponHolder {

    public W1_BeserkerAxe(Fighter fighter, WeaponType weaponType) {
        super(fighter, weaponType);
    }

    @Override
    public boolean doRightClick() {
        if (super.doRightClick()) {
            this.doBoosterJump();
            return true;
        }
        return false;
    }
    @Override
    public boolean doDrop() {
        if (!super.doDrop()){
            return false;
        }
        this.activateSpecial();
        return true;
    }
    @Override
    public void activateSpecial() {
        super.activateSpecial();
        super.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, super.getAbilityDurationTicks(), 0));
        super.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.HASTE, super.getAbilityDurationTicks(), 3));
        super.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, super.getAbilityDurationTicks(), 0));
        super.getPlayer().playSound(super.getPlayer().getLocation(), Sound.ENTITY_GHAST_SCREAM, 8, 1);
    }

    @Override
    public void deActivateSpecial() {
        super.deActivateSpecial();
    }

    private void doBoosterJump() {
        super.getPlayer().playSound(super.getPlayer().getLocation(), Sound.ENTITY_GHAST_SHOOT, 8, 1);
        Vector currentDirection = super.getPlayer().getLocation().getDirection().normalize();
        currentDirection = currentDirection.multiply(new Vector(1.7, 1.7, 1.7));
        super.getPlayer().setVelocity(currentDirection);
    }
}
