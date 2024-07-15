package me.cade.vanabyte.Fighters.WeaponHolders;

import me.cade.vanabyte.Fighters.Enums.WeaponType;
import me.cade.vanabyte.Fighters.Fighter;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class W6_GriefSword extends WeaponHolder {

    public W6_GriefSword(Fighter fighter, WeaponType weaponType) {
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
        this.player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, this.abilityDurationTicks, 0));
        this.makeInvisible(this.player);
        this.player.playSound(this.player.getLocation(), Sound.ENTITY_GHAST_SCREAM, 8, 1);
        this.fighterKitManager.setExplosionImmune(explosionImmuneUpgrade);
        //VanaByte.getPpAPI().addActivePlayerParticle(player, ParticleEffect.HEART, ParticleStyle.fromName("swords"));
    }
    @Override
    public void deActivateSpecial() {
        super.deActivateSpecial();
        this.makeVisible(player);
        this.fighterKitManager.setExplosionImmune(false);
        //VanaByte.getPpAPI().removeActivePlayerParticles(player, ParticleEffect.HEART);
    }

    @Override
    public boolean doLivingEntityHitEntity(EntityDamageByEntityEvent e) {
        return true;
    }

    public void doStealHealth(double health) {
        if (super.getWeaponAbility().isAbilityActive()) {
            double combined = this.player.getHealth() + health;
            if (combined > 20) {
                this.player.setHealth(20);
            } else {
                this.player.setHealth(combined);
            }
            this.player.playSound(this.player.getLocation(), Sound.ENTITY_ENDERMAN_HURT, 16, 1);
        }
    }
    public static void makeInvisible(Player player) {
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            p.sendEquipmentChange(player, EquipmentSlot.HEAD, new ItemStack(Material.AIR));
            p.sendEquipmentChange(player, EquipmentSlot.CHEST, new ItemStack(Material.AIR));
            p.sendEquipmentChange(player, EquipmentSlot.LEGS, new ItemStack(Material.AIR));
            p.sendEquipmentChange(player, EquipmentSlot.FEET, new ItemStack(Material.AIR));
        }
    }

    public static void makeVisible(Player player) {
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            p.sendEquipmentChange(player, EquipmentSlot.HEAD, player.getEquipment().getHelmet());
            p.sendEquipmentChange(player, EquipmentSlot.CHEST, player.getEquipment().getChestplate());
            p.sendEquipmentChange(player, EquipmentSlot.LEGS, player.getEquipment().getLeggings());
            p.sendEquipmentChange(player, EquipmentSlot.FEET,  player.getEquipment().getBoots());
        }
    }
}
