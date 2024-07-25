package me.cade.vanabyte.Fighters.Weapons.WeaponHolders;

import me.cade.vanabyte.Fighters.Enums.WeaponType;
import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.Fighters.Weapons.Weapon;
import me.cade.vanabyte.Fighters.Weapons.WeaponHolder;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class GoblinSword extends WeaponHolder {

    private final static WeaponType WEAPON_TYPE = WeaponType.GOBLIN_SWORD;

    private final double meleeDamage = fighter.getDoubleFromWeaponType(weaponType, 0);
    private final double flameAspectLevel = fighter.getDoubleFromWeaponType(weaponType, 1);
    private final double knockBackLevel = fighter.getDoubleFromWeaponType(weaponType, 2);

    public GoblinSword(Fighter fighter) {
        super(WEAPON_TYPE, fighter);
        super.weapon = new Weapon(
                WEAPON_TYPE,
                WEAPON_TYPE.getMaterial(),
                WEAPON_TYPE.getWeaponNameColored(),
                meleeDamage,
                -1,
                -1,
                -1);
        if((int) flameAspectLevel >= 0){
            weapon.applyWeaponUnsafeEnchantment(Enchantment.FIRE_ASPECT, (int) flameAspectLevel);
        }
        if((int) knockBackLevel >= 0){
            weapon.applyWeaponUnsafeEnchantment(Enchantment.KNOCKBACK, (int) knockBackLevel);
        }
    }

    @Override
    public void doMeleeAttack(EntityDamageByEntityEvent e, Player killer, LivingEntity victim) {
        super.trackWeaponDamage(victim, e.getFinalDamage());
    }

}
