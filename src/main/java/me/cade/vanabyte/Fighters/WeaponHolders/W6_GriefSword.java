package me.cade.vanabyte.Fighters.WeaponHolders;

import me.cade.vanabyte.Fighters.Enums.WeaponType;
import me.cade.vanabyte.Fighters.Fighter;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class W6_GriefSword extends WeaponHolder {

    private final Player player;

    public W6_GriefSword(Fighter fighter, WeaponType weaponType) {
        super(fighter, weaponType);
        player = fighter.getPlayer();
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
        if (!super.doDrop(e)){
            return false;
        }
        this.activateSpecial();
        return true;
    }

    @Override
    public boolean activateSpecial() {
        if(super.activateSpecial()){
            this.player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, super.getAbilityDurationTicks(), 0));
            this.makeInvisible();
            this.player.playSound(this.player.getLocation(), Sound.ENTITY_GHAST_SCREAM, 8, 1);
            super.getFighter().getFighterKitManager().setExplosionImmune(false);
            return true;
        }
        return false;
    }
    @Override
    public boolean deActivateSpecial() {
        if(super.deActivateSpecial()){
            this.makeVisible();
            super.getFighter().getFighterKitManager().setExplosionImmune(false);
            return true;
        }
        return false;
    }

    @Override
    public boolean doMeleeAttack(EntityDamageByEntityEvent e, Player killer, LivingEntity victim) {
        if(super.doMeleeAttack(e, killer, victim)){
            this.doStealHealth(e.getFinalDamage());
            return true;
        }
        return false;
    }

    public void doStealHealth(double health) {
        if (super.getWeaponAbility().isAbilityActive()) {
            double combined = player.getHealth() + health;
            if (combined > 20) {
                player.setHealth(20);
            } else {
                player.setHealth(combined);
            }
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_HURT, 16, 1);
        }
    }

    public void makeInvisible() {
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            p.sendEquipmentChange(player, EquipmentSlot.HEAD, new ItemStack(Material.AIR));
            p.sendEquipmentChange(player, EquipmentSlot.CHEST, new ItemStack(Material.AIR));
            p.sendEquipmentChange(player, EquipmentSlot.LEGS, new ItemStack(Material.AIR));
            p.sendEquipmentChange(player, EquipmentSlot.FEET, new ItemStack(Material.AIR));
        }
    }

    public void makeVisible() {
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            p.sendEquipmentChange(player, EquipmentSlot.HEAD, player.getEquipment().getHelmet());
            p.sendEquipmentChange(player, EquipmentSlot.CHEST, player.getEquipment().getChestplate());
            p.sendEquipmentChange(player, EquipmentSlot.LEGS, player.getEquipment().getLeggings());
            p.sendEquipmentChange(player, EquipmentSlot.FEET,  player.getEquipment().getBoots());
        }
    }
}
