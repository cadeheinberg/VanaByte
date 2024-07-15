package me.cade.vanabyte.Fighters.WeaponHolders;

import me.cade.vanabyte.Damaging.CreateExplosion;
import me.cade.vanabyte.Fighters.Enums.WeaponType;
import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.Damaging.EntityMetadata;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Trident;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class W4_IgorsTrident extends WeaponHolder {

    public W4_IgorsTrident(Fighter fighter, WeaponType weaponType) {
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
        //trident hit ground
        if (e.getEntity().getFireTicks() > 0) {
            CreateExplosion.doAnExplosion(this.player, e.getHitBlock().getLocation(), 0.7, this.specialDamage, true, this.weapon.getWeaponType());
        }
        e.getEntity().remove();
        return true;
    }

    @Override
    public boolean doProjectileHitEntity(EntityDamageByEntityEvent e) {
        return true;
    }

    @Override
    public boolean doProjectileLaunch(ProjectileLaunchEvent e) {
        return true;
    }

    public boolean doThrowTrident(Trident trident) {
        if (player.getCooldown(this.getMaterial()) > 0) {
            return false;
        }
        if(this.getRightClickCooldownTicks() > 0){
            player.setCooldown(this.getMaterial(), this.getRightClickCooldownTicks());
        }
        if (super.getWeaponAbility().isAbilityActive()) {
            trident.setFireTicks(10000);
        }
        EntityMetadata.addWeaponTypeToEntity(trident, this.weapon.getWeaponType(), this.player.getUniqueId());
        this.player.getInventory().remove(this.getWeapon().getWeaponItem());
        player.getInventory().setItemInMainHand(this.getWeapon().getWeaponItem());
        trident.setShooter(player);
        return true;
    }

    //Returns the amount of damage to do to the player
    public double doTridentHitEntity(LivingEntity victim, Trident trident) {
        if (trident.getFireTicks() > 0) {
            Location local = victim.getLocation();
            local.setY(local.getY() - 0.5);
            CreateExplosion.doAnExplosion(this.player, local, 0.7, this.specialDamage, true, this.weapon.getWeaponType());
            //explosion will deal special damage ^^
            return 0;
        }
        else{
            return this.getProjectileDamage();
        }
    }
}
