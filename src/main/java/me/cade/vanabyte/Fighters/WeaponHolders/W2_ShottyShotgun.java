package me.cade.vanabyte.Fighters.WeaponHolders;

import me.cade.vanabyte.Fighters.Enums.WeaponType;
import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.Damaging.EntityMetadata;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Random;

public class W2_ShottyShotgun extends WeaponHolder {


    public W2_ShottyShotgun(Fighter fighter, WeaponType weaponType) {
        super(fighter, weaponType);
    }

    @Override
    public boolean doRightClick() {
        if (!super.doRightClick()) {
            return false;
        }
        this.shootSnowballs();
        this.doShotgunRecoil(-0.42);
        return true;
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
        super.getPlayer().setCooldown(this.getMaterial(), 0);
        super.getPlayer().playSound(super.getPlayer().getLocation(), Sound.ENTITY_ENDERMAN_SCREAM, 8, 1);
    }
    @Override
    public void deActivateSpecial() {
        super.deActivateSpecial();
    }

    @Override
    public boolean doProjectileHitBlock(ProjectileHitEvent e){
        if(!super.doProjectileHitBlock(e)){
            return false;
        }
        //snowball hit ground
        return true;
    }

    @Override
    public boolean doProjectileHitEntity(EntityDamageByEntityEvent e){
        return true;
    }

    public double doSnowballHitEntity(LivingEntity victim, Snowball snowball) {
        if (snowball.getFireTicks() > 0) {
            victim.setFireTicks(50);
            return this.getSpecialDamage();
        }
        return this.getProjectileDamage();
    }

    private void doShotgunRecoil(Double power) {
        Vector currentDirection = super.getPlayer().getLocation().getDirection().normalize();
        currentDirection = currentDirection.multiply(new Vector(power, power, power));
        super.getPlayer().setVelocity(currentDirection);
    }

    public void shootSnowballs() {
        super.getPlayer().playSound(super.getPlayer().getLocation(), Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 8, 1);
        ArrayList<Snowball> snowBalls = new ArrayList<Snowball>();
        for (int i = 0; i < 6; i++){
            snowBalls.add(super.getPlayer().launchProjectile(Snowball.class));
            Random random = new Random();
            snowBalls.get(i).setVelocity(snowBalls.get(i).getVelocity().add(new Vector(random.nextDouble(-0.25, 0.25), random.nextDouble(-0.25, 0.25), random.nextDouble(-0.25, 0.25))));
            EntityMetadata.addWeaponTypeToEntity(snowBalls.get(i), super.getWeaponType(), super.getPlayer().getUniqueId());
            snowBalls.get(i).setShooter(super.getPlayer());
            if (super.getWeaponAbility().isAbilityActive()) {
                snowBalls.get(i).setFireTicks(1000);
            }
        }
    }
}
