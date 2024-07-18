package me.cade.vanabyte.Fighters.WeaponHolders;

import me.cade.vanabyte.Fighters.Enums.WeaponType;
import me.cade.vanabyte.Fighters.Fighter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class W3_GoblinSword extends WeaponHolder {

    private final static WeaponType WEAPON_TYPE = WeaponType.GOBLIN_SWORD;

    private final double meleeDamage = fighter.getDoubleFromWeaponType(weaponType, 0);
    private final double flameAspectLevel = fighter.getDoubleFromWeaponType(weaponType, 1);
    private final double knockBackLevel = fighter.getDoubleFromWeaponType(weaponType, 2);

    public W3_GoblinSword(Fighter fighter) {
        super(WEAPON_TYPE);
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
        super.player = fighter.getPlayer();
        super.weaponAbility = new WeaponAbility(fighter, this);
        super.fighter = fighter;
        this.player = this.fighter.getPlayer();
    }

    @Override
    public void doMeleeAttack(EntityDamageByEntityEvent e, Player killer, LivingEntity victim) {
        super.trackWeaponDamage(victim, e.getFinalDamage());
    }

}
