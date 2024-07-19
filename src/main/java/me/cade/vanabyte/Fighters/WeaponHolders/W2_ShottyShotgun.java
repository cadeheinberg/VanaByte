package me.cade.vanabyte.Fighters.WeaponHolders;

import me.cade.vanabyte.Fighters.Enums.WeaponType;
import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.Fighters.PVP.EntityMetadata;
import me.cade.vanabyte.VanaByte;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Random;

public class W2_ShottyShotgun extends WeaponHolder {

    private final static WeaponType WEAPON_TYPE = WeaponType.SHOTTY_SHOTGUN;

    private final int abilityDuration = fighter.getTickFromWeaponType(weaponType, 0);
    private final int abilityRecharge = fighter.getTickFromWeaponType(weaponType, 1);

    private final double meleeDamage = fighter.getDoubleFromWeaponType(weaponType, 2);

    private final int baseShootCooldown = fighter.getTickFromWeaponType(weaponType, 3);
    private final int abilityOnShootCooldown = fighter.getTickFromWeaponType(weaponType, 4);

    private final double baseRecoilPower = fighter.getDoubleFromWeaponType(weaponType, 5);
    private final double abilityOnRecoilPower = fighter.getDoubleFromWeaponType(weaponType, 6);

    private final double baseBulletDamage = fighter.getDoubleFromWeaponType(weaponType, 7);
    private final double abilityOnBulletDamage = fighter.getDoubleFromWeaponType(weaponType, 8);

    private final double baseNumBullets = fighter.getDoubleFromWeaponType(weaponType, 9);
    private final double abilityOnNumBullets = fighter.getDoubleFromWeaponType(weaponType, 10);

    private final double abilityOnSetBlocksOnFire = fighter.getDoubleFromWeaponType(weaponType, 11);

    public W2_ShottyShotgun(Fighter fighter) {
        super(WEAPON_TYPE, fighter);
        super.weapon = new Weapon(
                WEAPON_TYPE,
                WEAPON_TYPE.getMaterial(),
                WEAPON_TYPE.getWeaponNameColored(),
                meleeDamage,
                baseShootCooldown,
                abilityDuration,
                abilityRecharge);
    }

    @Override
    public void doMeleeAttack(EntityDamageByEntityEvent e, Player killer, LivingEntity victim) {
        super.trackWeaponDamage(victim, e.getFinalDamage());
    }

    @Override
    public void doRightClick(PlayerInteractEvent e) {
        if(!super.checkAndSetMainCooldown(baseShootCooldown, abilityOnShootCooldown)){
            return;
        }
        this.shootSnowballs();
        this.doShotgunRecoil();
    }

    @Override
    public void doDrop(PlayerDropItemEvent e) {
        if(!super.checkAndSetSpecialCooldown(abilityDuration, abilityRecharge)){
            return;
        }
        // instant reload shotgun
        player.setCooldown(weaponType.getMaterial(), 0);
        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_SCREAM, 8, 1);
    }

    @Override
    public void doProjectileHitBlock(ProjectileHitEvent e){
        //snowball hit ground
        e.getEntity().remove();
    }

    @Override
    public void doProjectileHitEntity(EntityDamageByEntityEvent e, Player shooter, LivingEntity victim, Entity damagingEntity) {
        if (damagingEntity.getFireTicks() > 0) {
            victim.setFireTicks(50);
            e.setDamage(abilityOnBulletDamage);
        }else{
            e.setDamage(baseBulletDamage);
        }
        e.getDamager().remove();
        super.trackWeaponDamage(victim, e.getFinalDamage());
    }

    private void doShotgunRecoil() {
        Vector currentDirection = player.getLocation().getDirection().normalize();
        if(abilityOnRecoilPower >= 0 && weaponAbility.isAbilityActive()){
            currentDirection = currentDirection.multiply(new Vector(-baseRecoilPower, -baseRecoilPower, -baseRecoilPower));
        }else{
            currentDirection = currentDirection.multiply(new Vector(-abilityOnRecoilPower, -abilityOnRecoilPower, -abilityOnRecoilPower));
        }
        player.setVelocity(currentDirection);
    }

    public void shootSnowballs() {
        player.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 8, 1);
        ArrayList<Snowball> snowBalls = new ArrayList<Snowball>();
        int numBullets = (int) baseNumBullets;
        if(weaponAbility.isAbilityActive()){
            numBullets = (int) Math.max(baseNumBullets, abilityOnNumBullets);
        }
        for (int i = 0; i < numBullets; i++){
            snowBalls.add(player.launchProjectile(Snowball.class));
            Random random = new Random();
            snowBalls.get(i).setVelocity(snowBalls.get(i).getVelocity().add(new Vector(random.nextDouble(-0.25, 0.25), random.nextDouble(-0.25, 0.25), random.nextDouble(-0.25, 0.25))));
            EntityMetadata.addWeaponTypeToEntity(snowBalls.get(i), weaponType, player.getUniqueId());
            snowBalls.get(i).setShooter(player);
            if (weaponAbility.isAbilityActive()) {
                snowBalls.get(i).setFireTicks(1000);
            }
        }
    }
}
