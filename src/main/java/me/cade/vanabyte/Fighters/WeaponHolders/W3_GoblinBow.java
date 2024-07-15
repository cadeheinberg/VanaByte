package me.cade.vanabyte.Fighters.WeaponHolders;

import me.cade.vanabyte.Fighters.Enums.WeaponType;
import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.Fighters.PVP.EntityMetadata;
import org.bukkit.*;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class W3_GoblinBow extends WeaponHolder {

    public W3_GoblinBow(Fighter fighter, WeaponType weaponType) {
        super(fighter, weaponType);
    }

    @Override
    public boolean doRightClick() {
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
        this.player.playSound(this.player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 8, 1);
    }
    @Override
    public void deActivateSpecial() {
        super.deActivateSpecial();
    }

    @Override
    public boolean doProjectileHitBlock(ProjectileHitEvent e) {
        if (!super.doProjectileHitBlock(e)) {
            return false;
        }
        //arrow hit ground
        e.getEntity().remove();
        return true;
    }

    @Override
    public boolean doProjectileHitEntity(EntityDamageByEntityEvent e){
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

    public double doArrowHitEntity(LivingEntity victim, Arrow arrow) {
        // create your own form of knockback
        if (victim instanceof Player) {
            Fighter.get(player).fighterDismountParachute();
        }
        if (arrow.getFireTicks() > 0) {
            victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 120, 2));
            victim.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 120, 2));
            return this.getSpecialDamage();
        }
        return projectileDamage;
    }

    public boolean doArrowShoot(Arrow arrow, double force) {
        EntityMetadata.addWeaponTypeToEntity(arrow, this.weaponType, this.player.getUniqueId());
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
            EntityMetadata.addWeaponTypeToEntity(arrow1, this.weaponType, this.player.getUniqueId());

            Arrow arrow2 = player.launchProjectile(Arrow.class);
            arrow2.setVelocity(arrow.getVelocity().add(new Vector(0, -0.25, 0)));
            arrow2.setFireTicks(2000);
            arrow2.setShooter(player);
            EntityMetadata.addWeaponTypeToEntity(arrow2, this.weaponType, this.player.getUniqueId());

            Arrow arrow3 = player.launchProjectile(Arrow.class);
            arrow3.setVelocity(arrow.getVelocity().add(new Vector(0.25, 0, 0)));
            arrow3.setFireTicks(2000);
            arrow3.setShooter(player);
            EntityMetadata.addWeaponTypeToEntity(arrow3, this.weaponType, this.player.getUniqueId());

            Arrow arrow4 = player.launchProjectile(Arrow.class);
            arrow4.setVelocity(arrow.getVelocity().add(new Vector(-0.25, 0, 0)));
            arrow4.setFireTicks(2000);
            arrow4.setShooter(player);
            EntityMetadata.addWeaponTypeToEntity(arrow4, this.weaponType, this.player.getUniqueId());

            Arrow arrow5 = player.launchProjectile(Arrow.class);
            arrow5.setVelocity(arrow.getVelocity().add(new Vector(0, 0, 0.25)));
            arrow5.setFireTicks(2000);
            arrow5.setShooter(player);
            EntityMetadata.addWeaponTypeToEntity(arrow5, this.weaponType, this.player.getUniqueId());

            Arrow arrow6 = player.launchProjectile(Arrow.class);
            arrow6.setVelocity(arrow.getVelocity().add(new Vector(0, 0, -0.25)));
            arrow6.setFireTicks(2000);
            arrow6.setShooter(player);
            EntityMetadata.addWeaponTypeToEntity(arrow6, this.weaponType, this.player.getUniqueId());
            return;
        }
    }
}
