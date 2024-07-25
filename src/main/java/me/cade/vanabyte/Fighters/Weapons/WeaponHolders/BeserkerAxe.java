package me.cade.vanabyte.Fighters.Weapons.WeaponHolders;

import me.cade.vanabyte.Fighters.Enums.WeaponType;
import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.Fighters.Weapons.Weapon;
import me.cade.vanabyte.Fighters.Weapons.WeaponHolder;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class BeserkerAxe extends WeaponHolder {

    private final static WeaponType WEAPON_TYPE = WeaponType.BESERKER_AXE;

    private final int abilityDuration = fighter.getTickFromWeaponType(weaponType, 0);
    private final int abilityRecharge = fighter.getTickFromWeaponType(weaponType, 1);

    private final double meleeDamage = fighter.getDoubleFromWeaponType(weaponType, 2);

    private final int baseLeapCooldown = fighter.getTickFromWeaponType(weaponType, 3);
    private final int abilityOnLeapCooldown = fighter.getTickFromWeaponType(weaponType, 4);

    private final double baseLeapPower = fighter.getDoubleFromWeaponType(weaponType, 5);
    private final double abilityOnLeapPower = fighter.getDoubleFromWeaponType(weaponType, 6);

    private final double abilityOnSpeedLevel = fighter.getDoubleFromWeaponType(weaponType, 7);
    private final double abilityOnJumpLevel = fighter.getDoubleFromWeaponType(weaponType, 8);
    private final double abilityOnHasteLevel = fighter.getDoubleFromWeaponType(weaponType, 9);

    public BeserkerAxe(Fighter fighter) {
        super(WEAPON_TYPE, fighter);
        super.weapon = new Weapon(
                WEAPON_TYPE,
                WEAPON_TYPE.getMaterial(),
                WEAPON_TYPE.getWeaponNameColored(),
                meleeDamage,
                baseLeapCooldown,
                abilityDuration,
                abilityRecharge);
    }

    @Override
    public void doMeleeAttack(EntityDamageByEntityEvent e, Player killer, LivingEntity victim) {
        super.trackWeaponDamage(victim, e.getFinalDamage());
    }

    @Override
    public void doRightClick(PlayerInteractEvent e) {
        if(!super.checkAndSetMainCooldown(baseLeapCooldown, abilityOnLeapCooldown)){
            return;
        }
        this.doBoosterJump();
    }

    @Override
    public void doDrop(PlayerDropItemEvent e) {
        if(!super.checkAndSetSpecialCooldown(abilityDuration, abilityRecharge)){
            return;
        }
        fighter.addPotionIfStrengthIsNonNegative(PotionEffectType.SPEED, abilityDuration, abilityOnSpeedLevel);
        fighter.addPotionIfStrengthIsNonNegative(PotionEffectType.HASTE, abilityDuration, abilityOnHasteLevel);
        fighter.addPotionIfStrengthIsNonNegative(PotionEffectType.JUMP_BOOST, abilityDuration, abilityOnJumpLevel);
        player.playSound(player.getLocation(), Sound.ENTITY_GHAST_SCREAM, 8, 1);
    }

    private void doBoosterJump() {
        player.playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 8, 1);
        Vector currentDirection = player.getLocation().getDirection().normalize();
        if(abilityOnLeapPower >= 0 && weaponAbility.isAbilityActive()){
            currentDirection = currentDirection.multiply(new Vector(abilityOnLeapPower, abilityOnLeapPower, abilityOnLeapPower));
        }else{
            currentDirection = currentDirection.multiply(new Vector(baseLeapPower, baseLeapPower, baseLeapPower));
        }
        player.setVelocity(currentDirection);
    }
}
