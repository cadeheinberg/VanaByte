package me.cade.vanabyte.Fighters.WeaponHolders;

import me.cade.vanabyte.Fighters.PVP.CreateExplosion;
import me.cade.vanabyte.Fighters.Enums.WeaponType;
import me.cade.vanabyte.Fighters.Fighter;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class W4_IgorsTrident extends WeaponHolder {

    private final Player player;

    public W4_IgorsTrident(Fighter fighter, WeaponType weaponType) {
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
            this.player.playSound(this.player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 8, 1);
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
        if (!super.doProjectileHitBlock(e)) {
            return false;
        }
        //trident hit ground
        if (e.getEntity().getFireTicks() > 0) {
            CreateExplosion.doAnExplosion(this.player, e.getHitBlock().getLocation(), 0.7, super.getSpecialDamage(), true, super.getWeaponType());
        }
        e.getEntity().remove();
        return true;
    }

    @Override
    public boolean doProjectileHitEntity(EntityDamageByEntityEvent e, Player shooter, LivingEntity victim, Entity damagingEntity) {
        if(super.doProjectileHitEntity(e, shooter, victim, damagingEntity)){
            if (damagingEntity.getFireTicks() > 0) {
                Location local = victim.getLocation();
                local.setY(local.getY() - 0.5);
                CreateExplosion.doAnExplosion(this.player, local, 0.7, super.getSpecialDamage(), true, super.getWeaponType());
            }
            e.setDamage(super.getProjectileDamage());
            e.getDamager().remove();
            return true;
        }
        return true;
    }

    @Override
    public boolean doProjectileLaunch(ProjectileLaunchEvent e) {
        if(super.doProjectileLaunch(e)){
            this.doThrowTrident((Trident) e.getEntity());
            return true;
        }
        return false;
    }

    public boolean doThrowTrident(Trident trident) {
        if (super.getWeaponAbility().isAbilityActive()) {
            trident.setFireTicks(10000);
        }
        this.player.getInventory().remove(this.getWeapon().getWeaponItem());
        player.getInventory().setItemInMainHand(this.getWeapon().getWeaponItem());
        trident.setShooter(player);
        return true;
    }

}
