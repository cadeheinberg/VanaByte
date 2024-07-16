package me.cade.vanabyte.Fighters.WeaponHolders;

import me.cade.vanabyte.Fighters.Enums.WeaponType;
import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.Fighters.PVP.EntityMetadata;
import org.bukkit.*;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class W3_GoblinBow extends WeaponHolder {

    private final Player player;

    public W3_GoblinBow(Fighter fighter, WeaponType weaponType) {
        super(fighter, weaponType);
        this.player = fighter.getPlayer();
    }

    @Override
    public boolean doRightClick(PlayerInteractEvent e) {
        if(super.doRightClick(e)){
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
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 8, 1);
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
    public boolean doProjectileHitBlock(ProjectileHitEvent e) {
        if (super.doProjectileHitBlock(e)) {
            e.getEntity().remove();
            return true;
        }
        return false;
    }

    @Override
    public boolean doProjectileHitEntity(EntityDamageByEntityEvent e, Player shooter, LivingEntity victim, Entity damagingEntity) {
        if(super.doProjectileHitEntity(e, shooter, victim, damagingEntity)){
            if (victim instanceof Player) {
                Fighter.get((Player) victim).fighterDismountParachute();
            }
            if (damagingEntity.getFireTicks() > 0) {
                victim.setFireTicks(50);
            }
            e.setDamage(super.getProjectileDamage());
            e.getDamager().remove();
            return true;
        }
        return true;
    }

    @Override
    public boolean doBowShootEvent(EntityShootBowEvent e){
        if(super.doBowShootEvent(e)){
            this.doArrowShoot((Arrow) e.getProjectile(), e.getForce());
            return true;
        }
        //cooldown
        e.setCancelled(true);
        return false;
    }

    public boolean doArrowShoot(Arrow arrow, double force) {
        if (super.getWeaponAbility().isAbilityActive()) {
            arrow.setFireTicks(1000);
            doArrowBarrage(this.player, arrow, force);
        }
        return true;
    }

    public void doArrowBarrage(Player player, Arrow arrow, double force) {
        if (force > 0.75) {
            Arrow arrow1 = player.launchProjectile(Arrow.class);
            arrow1.setVelocity(arrow.getVelocity().add(new Vector(0, 0.25, 0)));
            arrow1.setFireTicks(2000);
            arrow1.setShooter(player);
            EntityMetadata.addWeaponTypeToEntity(arrow1, super.getWeaponType(), player.getUniqueId());

            Arrow arrow2 = player.launchProjectile(Arrow.class);
            arrow2.setVelocity(arrow.getVelocity().add(new Vector(0, -0.25, 0)));
            arrow2.setFireTicks(2000);
            arrow2.setShooter(player);
            EntityMetadata.addWeaponTypeToEntity(arrow2, super.getWeaponType(), player.getUniqueId());

            Arrow arrow3 = player.launchProjectile(Arrow.class);
            arrow3.setVelocity(arrow.getVelocity().add(new Vector(0.25, 0, 0)));
            arrow3.setFireTicks(2000);
            arrow3.setShooter(player);
            EntityMetadata.addWeaponTypeToEntity(arrow3, super.getWeaponType(), player.getUniqueId());

            Arrow arrow4 = player.launchProjectile(Arrow.class);
            arrow4.setVelocity(arrow.getVelocity().add(new Vector(-0.25, 0, 0)));
            arrow4.setFireTicks(2000);
            arrow4.setShooter(player);
            EntityMetadata.addWeaponTypeToEntity(arrow4, super.getWeaponType(), player.getUniqueId());

            Arrow arrow5 = player.launchProjectile(Arrow.class);
            arrow5.setVelocity(arrow.getVelocity().add(new Vector(0, 0, 0.25)));
            arrow5.setFireTicks(2000);
            arrow5.setShooter(player);
            EntityMetadata.addWeaponTypeToEntity(arrow5, super.getWeaponType(), player.getUniqueId());

            Arrow arrow6 = player.launchProjectile(Arrow.class);
            arrow6.setVelocity(arrow.getVelocity().add(new Vector(0, 0, -0.25)));
            arrow6.setFireTicks(2000);
            arrow6.setShooter(player);
            EntityMetadata.addWeaponTypeToEntity(arrow6, super.getWeaponType(), player.getUniqueId());
        }
    }
}
