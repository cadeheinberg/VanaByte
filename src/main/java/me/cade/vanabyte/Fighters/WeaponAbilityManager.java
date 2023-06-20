package me.cade.vanabyte.Fighters;

import dev.esophose.playerparticles.particles.ParticleEffect;
import dev.esophose.playerparticles.particles.data.OrdinaryColor;
import dev.esophose.playerparticles.styles.ParticleStyle;
import me.cade.vanabyte.Fighters.Weapons.GriefSword;
import me.cade.vanabyte.Fighters.Weapons.WeaponHolder;
import me.cade.vanabyte.VanaByte;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.awt.*;

public class WeaponAbilityManager {

    private Player player = null;
    private Fighter fighter = null;

    public WeaponAbilityManager(Fighter fighter){
        this.fighter = fighter;
        this.player = this.fighter.getPlayer();
    }

    protected void fighterJoined(){
        this.resetPlayerParticles();
        this.resetAllWeaponAbilities();
    }

    protected void fighterDied(){

    }

    protected void fighterLeftServer(){

    }

    protected void fighterChangedWorld(){
        this.resetPlayerParticles();
        this.resetAllWeaponAbilities();
    }

    protected void fighterRespawned(){
        //this.resetSpecialAbility();
        this.resetPlayerParticles();
        this.resetAllWeaponAbilities();
    }

    protected void fighterGotNewKit(){
        this.changeAbilityRechargedParticleEffect();
    }

    public void resetPlayerParticles(){
        VanaByte.getPpAPI().resetActivePlayerParticles(player);
    }

    public void resetAllWeaponAbilities(){
        for (WeaponHolder weaponHolder : fighter.getFKit().getWeaponHolders()){
            if(weaponHolder == null){
                continue;
            }
            if(weaponHolder.getWeaponAbility() == null){
                continue;
            }
            weaponHolder.getWeaponAbility().resetSpecialAbility();
        }
    }

    public WeaponHolder isSomeWeaponAbilityActive(){
        for (WeaponHolder weaponHolder : fighter.getFKit().getWeaponHolders()){
            if(weaponHolder == null){
                continue;
            }
            if(weaponHolder.getWeapon() == null){
                continue;
            }
            if(weaponHolder.getWeapon().getWeaponItem() == null){
                continue;
            }
            if(weaponHolder.getWeaponAbility() == null){
                continue;
            }
            if(weaponHolder.getWeaponAbility().isAbilityActive()){
                return weaponHolder;
            }
        }
        return null;
    }

    public void changeAbilityActiveParticleEffect(WeaponHolder weaponHolder) {
        if (weaponHolder instanceof GriefSword) {
            // greif goes invisible
            return;
        }
        if (this.isSomeWeaponAbilityActive() != null) {
            if (VanaByte.getPpAPI() != null) {
                VanaByte.getPpAPI().removeActivePlayerParticles(player, ParticleStyle.fromName("normal"));
                VanaByte.getPpAPI().addActivePlayerParticle(player, ParticleEffect.DUST, ParticleStyle.fromName("normal"),
                        new OrdinaryColor(fighter.getFKit().getArmorColor().getRed(),
                                fighter.getFKit().getArmorColor().getGreen(), fighter.getFKit().getArmorColor().getBlue()));
                this.makeEnchanted(this.player);
            }
        } else {
            if (VanaByte.getPpAPI() != null) {
                VanaByte.getPpAPI().removeActivePlayerParticles(player, ParticleStyle.fromName("normal"));
                this.removeEnchanted(this.player);
            }
        }
    }

    public void changeAbilityRechargedParticleEffect() {
        if (this.isSomeWeaponAbilityActive() != null) {
            if (VanaByte.getPpAPI() != null) {
                VanaByte.getPpAPI().removeActivePlayerParticles(player, ParticleStyle.fromName("point"));
                VanaByte.getPpAPI().addActivePlayerParticle(player, ParticleEffect.DUST, ParticleStyle.fromName("point"),
                        new OrdinaryColor(fighter.getFKit().getArmorColor().getRed(),
                                fighter.getFKit().getArmorColor().getGreen(), fighter.getFKit().getArmorColor().getBlue()));
            }
        } else {
            if (VanaByte.getPpAPI() != null) {
                VanaByte.getPpAPI().removeActivePlayerParticles(player, ParticleStyle.fromName("point"));
            }
        }
    }
    public static void makeEnchanted(Player player) {

        ItemStack helmet = player.getEquipment().getHelmet();
        ItemStack chest = player.getEquipment().getChestplate();
        ItemStack leggings = player.getEquipment().getLeggings();
        ItemStack boots = player.getEquipment().getBoots();

        if(helmet != null){
            helmet.addEnchantment(Enchantment.DURABILITY, 1);
        }
        if(chest != null){
            chest.addEnchantment(Enchantment.DURABILITY, 1);
        }
        if(leggings != null){
            leggings.addEnchantment(Enchantment.DURABILITY, 1);
        }
        if(boots != null){
            boots.addEnchantment(Enchantment.DURABILITY, 1);
        }

        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            if(helmet != null){
                p.sendEquipmentChange(player, EquipmentSlot.HEAD, helmet);
            }
            if(chest != null){
                p.sendEquipmentChange(player, EquipmentSlot.CHEST, chest);
            }
            if(leggings != null){
                p.sendEquipmentChange(player, EquipmentSlot.LEGS, leggings);
            }
            if(boots != null){
                p.sendEquipmentChange(player, EquipmentSlot.FEET,  boots);
            }
        }
    }

    public static void removeEnchanted(Player player) {
        ItemStack helmet = player.getEquipment().getHelmet();
        ItemStack chest = player.getEquipment().getChestplate();
        ItemStack leggings = player.getEquipment().getLeggings();
        ItemStack boots = player.getEquipment().getBoots();

        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            if(helmet != null){
                p.sendEquipmentChange(player, EquipmentSlot.HEAD, helmet);
            }
            if(chest != null){
                p.sendEquipmentChange(player, EquipmentSlot.CHEST, chest);
            }
            if(leggings != null){
                p.sendEquipmentChange(player, EquipmentSlot.LEGS, leggings);
            }
            if(boots != null){
                p.sendEquipmentChange(player, EquipmentSlot.FEET,  boots);
            }
        }
    }

}
