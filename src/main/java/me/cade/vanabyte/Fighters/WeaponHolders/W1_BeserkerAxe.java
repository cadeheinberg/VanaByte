package me.cade.vanabyte.Fighters.WeaponHolders;

import me.cade.vanabyte.Fighters.Enums.WeaponType;
import me.cade.vanabyte.Fighters.Fighter;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class W1_BeserkerAxe extends WeaponHolder {

    public W1_BeserkerAxe(Fighter fighter, WeaponType weaponType) {
        super(fighter, weaponType);
    }

    @Override
    public void doMeleeAttack(EntityDamageByEntityEvent e, Player killer, LivingEntity victim) {
        super.trackWeaponDamage(victim);
    }

    @Override
    public void doRightClick(PlayerInteractEvent e) {
        if(!super.checkAndSetMainCooldown()){
            return;
        }
        this.doBoosterJump();
    }

    @Override
    public void doDrop(PlayerDropItemEvent e) {
        if(!super.checkAndSetSpecialCooldown()){
            return;
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, statBundle.getAbilityDuration(), 0));
        player.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, statBundle.getAbilityDuration(), 3));
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, statBundle.getAbilityDuration(), 0));
        player.playSound(player.getLocation(), Sound.ENTITY_GHAST_SCREAM, 8, 1);
    }

    private void doBoosterJump() {
        player.playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 8, 1);
        Vector currentDirection = player.getLocation().getDirection().normalize();
        if(weaponAbility.isAbilityActive()){
            currentDirection = currentDirection.multiply(new Vector(statBundle.getBasePower1(), statBundle.getBasePower1(), statBundle.getBasePower1()));
        }else{
            currentDirection = currentDirection.multiply(new Vector(statBundle.getSpecialPower1(), statBundle.getSpecialPower1(), statBundle.getSpecialPower1()));
        }
        player.setVelocity(currentDirection);
    }
}
