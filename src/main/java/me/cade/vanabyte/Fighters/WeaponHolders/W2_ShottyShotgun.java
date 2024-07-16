package me.cade.vanabyte.Fighters.WeaponHolders;

import me.cade.vanabyte.Fighters.Enums.WeaponType;
import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.Fighters.PVP.EntityMetadata;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Random;

public class W2_ShottyShotgun extends WeaponHolder {

    private final Player player;

    public W2_ShottyShotgun(Fighter fighter, WeaponType weaponType) {
        super(fighter, weaponType);
        this.player = fighter.getPlayer();
    }

    @Override
    public boolean doRightClick(PlayerInteractEvent e) {
        if (super.doRightClick(e)) {
            this.shootSnowballs();
            this.doShotgunRecoil(-0.42);
            return true;
        }
        return false;
    }

    @Override
    public boolean doDrop(PlayerDropItemEvent e) {
        if (super.doDrop(e)){
            this.activateSpecial();
            return true;
        }
        return true;
    }


    @Override
    public boolean activateSpecial() {
        if(super.activateSpecial()){
            // instant reload shotgun
            player.setCooldown(super.getWeaponType().getMaterial(), 0);
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_SCREAM, 8, 1);
            return true;
        }
        return false;
    }
    @Override
    public boolean deActivateSpecial() {
        if(super.deActivateSpecial()){
            return true;
        }
        return false;
    }

    @Override
    public boolean doProjectileHitBlock(ProjectileHitEvent e){
        if(super.doProjectileHitBlock(e)){
            //snowball hit ground
            return true;
        }
        return false;
    }

    @Override
    public boolean doProjectileHitEntity(EntityDamageByEntityEvent e, Player shooter, LivingEntity victim, Entity damagingEntity) {
        if(super.doProjectileHitEntity(e, shooter, victim, damagingEntity)){
            if (damagingEntity.getFireTicks() > 0) {
                victim.setFireTicks(50);
            }
            e.setDamage(super.getProjectileDamage());
            e.getDamager().remove();
            return true;
        }
        return true;
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
