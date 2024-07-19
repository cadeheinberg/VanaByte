package me.cade.vanabyte.Fighters.WeaponHolders;

import me.cade.vanabyte.Fighters.Enums.WeaponType;
import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.Fighters.PVP.EntityMetadata;
import me.cade.vanabyte.VanaByte;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class W4_IgorsTrident extends WeaponHolder {

    private final static WeaponType WEAPON_TYPE = WeaponType.IGORS_TRIDENT;
    private final int TRIDENT_SPAM_COOLDOWN_TICKS = 3;

    private final int abilityDuration = fighter.getTickFromWeaponType(weaponType, 0);
    private final int abilityRecharge = fighter.getTickFromWeaponType(weaponType, 1);

    private final double meleeDamage = fighter.getDoubleFromWeaponType(weaponType, 2);

    private final double baseTridentProjectileDamage = fighter.getDoubleFromWeaponType(weaponType, 3);
    private final double abilityOnTridentProjectileDamage = fighter.getDoubleFromWeaponType(weaponType, 4);

    private final double baseTridentExplosionDamage = fighter.getDoubleFromWeaponType(weaponType, 5);

    private final double baseTridentExplosionPower = fighter.getDoubleFromWeaponType(weaponType, 6);

    public W4_IgorsTrident(Fighter fighter) {
        super(WEAPON_TYPE, fighter);
        super.weapon = new Weapon(
                WEAPON_TYPE,
                WEAPON_TYPE.getMaterial(),
                WEAPON_TYPE.getWeaponNameColored(),
                meleeDamage,
                TRIDENT_SPAM_COOLDOWN_TICKS,
                abilityDuration,
                abilityRecharge);
    }
    @Override
    public void doDrop(PlayerDropItemEvent e) {
        if(!super.checkAndSetSpecialCooldown(abilityDuration, abilityRecharge)){
            return;
        }
        player.playSound(player.getLocation(), Sound.ENTITY_WARDEN_DEATH, 8, 1);
    }

    @Override
    public void doProjectileHitBlock(ProjectileHitEvent e) {
        if (e.getEntity().getFireTicks() > 0) {
            createAnExplosion(e.getHitBlock().getLocation(), baseTridentExplosionDamage, baseTridentExplosionPower);
        }
        e.getEntity().remove();
    }

    @Override
    public void doProjectileHitEntity(EntityDamageByEntityEvent e, Player shooter, LivingEntity victim, Entity damagingEntity) {
        if (damagingEntity.getFireTicks() > 0) {
            Location local = victim.getLocation();
            local.setY(local.getY() - 0.5);
            e.setDamage(abilityOnTridentProjectileDamage);
            createAnExplosion(local, baseTridentExplosionDamage, baseTridentExplosionPower);
        }else{
            e.setDamage(baseTridentProjectileDamage);
        }
        e.getDamager().remove();
        super.trackWeaponDamage(victim, e.getFinalDamage());
    }

    @Override
    public void doProjectileLaunch(ProjectileLaunchEvent e) {
        if (!checkAndSetMainCooldown(TRIDENT_SPAM_COOLDOWN_TICKS, -1)) {
            e.setCancelled(true);
            return;
        }
        if (weaponAbility.isAbilityActive()) {
            e.getEntity().setFireTicks(10000);
        }
        player.getInventory().remove(weapon.getWeaponItem());
        player.getInventory().setItemInMainHand(weapon.getWeaponItem());
        EntityMetadata.addWeaponTypeToEntity(e.getEntity(), this.weapon.getWeaponType(), this.player.getUniqueId());
        e.getEntity().setShooter(player);
    }

}
