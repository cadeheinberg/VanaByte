package me.cade.vanabyte.Fighters.WeaponHolders;

import me.cade.vanabyte.Fighters.Enums.WeaponType;
import me.cade.vanabyte.Fighters.Fighter;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class W3_GoblinArrow extends WeaponHolder {

    private final static WeaponType WEAPON_TYPE = WeaponType.GOBLIN_ARROW;

    private final double meleeDamage = fighter.getDoubleFromWeaponType(weaponType, 0);

    public W3_GoblinArrow(Fighter fighter) {
        super(WEAPON_TYPE, fighter);
        super.weapon = new Weapon(
                WEAPON_TYPE,
                WEAPON_TYPE.getMaterial(),
                WEAPON_TYPE.getWeaponNameColored(),
                meleeDamage,
                -1,
                -1,
                -1);
    }

    @Override
    public void doMeleeAttack(EntityDamageByEntityEvent e, Player killer, LivingEntity victim) {
        super.trackWeaponDamage(victim, e.getFinalDamage());
    }

}
