package me.cade.vanabyte.Fighters.WeaponHolders;

import me.cade.vanabyte.Fighters.Enums.WeaponType;
import me.cade.vanabyte.Fighters.Fighter;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class W1_BeserkerAxe extends WeaponHolder {

    private final Player player;

    public W1_BeserkerAxe(Fighter fighter, WeaponType weaponType) {
        super(fighter, weaponType);
        this.player = fighter.getPlayer();
    }

    @Override
    public boolean doRightClick(PlayerInteractEvent e) {
        if(super.doRightClick(e)){
            this.doBoosterJump();
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
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, super.getAbilityDurationTicks(), 0));
            player.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, super.getAbilityDurationTicks(), 3));
            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, super.getAbilityDurationTicks(), 0));
            player.playSound(player.getLocation(), Sound.ENTITY_GHAST_SCREAM, 8, 1);
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

    private void doBoosterJump() {
        super.getPlayer().playSound(super.getPlayer().getLocation(), Sound.ENTITY_GHAST_SHOOT, 8, 1);
        Vector currentDirection = super.getPlayer().getLocation().getDirection().normalize();
        currentDirection = currentDirection.multiply(new Vector(1.7, 1.7, 1.7));
        super.getPlayer().setVelocity(currentDirection);
    }
}
