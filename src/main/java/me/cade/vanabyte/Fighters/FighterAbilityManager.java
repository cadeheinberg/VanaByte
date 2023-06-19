package me.cade.vanabyte.Fighters;

import dev.esophose.playerparticles.particles.ParticleEffect;
import dev.esophose.playerparticles.particles.data.OrdinaryColor;
import dev.esophose.playerparticles.styles.ParticleStyle;
import me.cade.vanabyte.VanaByte;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.*;

public class FighterAbilityManager {

    private Player player = null;
    private Fighter fighter = null;

    private boolean abilityActive = false;
    private boolean abilityRecharged = true;

    protected FighterAbilityManager(Player player, Fighter fighter){
        this.player = player;
        this.fighter = fighter;
    }

    protected void resetPlayerParticles(){
        VanaByte.getPpAPI().resetActivePlayerParticles(player);
    }

    protected void resetSpecialAbility(){
        this.setAbilityActive(false);
        this.setAbilityRecharged(true);
    }
    protected void startAbilityDuration() {
        this.setAbilityActive(true);
        this.setAbilityRecharged(false);
        int durationTicks = fighter.getDurationTicks();
        player.setCooldown(Material.BARRIER, durationTicks);
        int cooldownTask = new BukkitRunnable() {
            @Override
            public void run() {
                //Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Cooldown Task Running");
                if (player != null) {
                    if (!player.isOnline()) {
                        return;
                    }
                    if (player.getCooldown(Material.BARRIER) < 1) {
                        cancel();
                        fighter.setAbilityActive(false);
                        fighter.setActionBarToFloat(0);
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_BREAK, 8, 1);
                        startAbilityRecharge();
                        return;
                    }
                    Fighter.get(player).setActionBarToFloat(((float) player.getCooldown(Material.BARRIER)) / durationTicks);
                }
            }
        }.runTaskTimer(VanaByte.getInstance(), 0L, 1L).getTaskId();
        Fighter.get(player).setCooldownTask(cooldownTask);
    }

    private void startAbilityRecharge() {
        int rechargeTicks = fighter.getRechargeTicks();
        player.setCooldown(Material.BARRIER, rechargeTicks);
        int rechargeTask = new BukkitRunnable() {
            @Override
            public void run() {
                //Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Recharge Task Running");
                if (player != null) {
                    if (!player.isOnline()) {
                        return;
                    }
                    if (player.getCooldown(Material.BARRIER) < 1) {
                        cancel();
                        fighter.setAbilityRecharged(true);
                        fighter.setActionBarToFloat(1);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 8, 1);
                        return;
                    }
                    Fighter.get(player).setActionBarToFloat(
                            ((float) (rechargeTicks - player.getCooldown(Material.BARRIER))) / rechargeTicks);
                }
            }
        }.runTaskTimer(VanaByte.getInstance(), 0L, 1L).getTaskId();
        Fighter.get(player).setRechargeTask(rechargeTask);
    }

    public void applyNightVision() {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(fighter.getPlugin(), new Runnable() {
            @Override
            public void run() {
                player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 9999999, 0, true, false));
            }
        }, 1);
    }

    public void setAbilityActive(boolean fighterAbility) {
        if (!fighterAbility) {
            // turning ability active off
            // only if the ability was already active
            if (this.abilityActive) {
                player.setCooldown(Material.BARRIER, 0);
                fighter.fighterTaskManager.cancelCooldownTask();
                fighter.getFKit().deActivateSpecial();
            }
        }
        this.abilityActive = fighterAbility;
        changeAbilityActiveParticleEffect();

    }

    public void setAbilityRecharged(boolean fighterRecharged) {
        if (fighterRecharged) {
            // turning ability charged fully on
            // only if the ability was already not recharged
            if (!this.abilityRecharged) {
                player.setCooldown(Material.BARRIER, 0);
                fighter.fighterTaskManager.cancelRechargeTask();
            }
        }
        this.abilityRecharged = fighterRecharged;
        this.changeAbilityRechargedParticleEffect();
    }

    private void changeAbilityActiveParticleEffect() {
        if (fighter.fighterKitManager.getKitID() == 6) {
            // greif goes invisible
            return;
        }
        if (this.abilityActive) {
            if (VanaByte.getPpAPI() != null) {
                VanaByte.getPpAPI().removeActivePlayerParticles(player, ParticleStyle.fromName("normal"));
                VanaByte.getPpAPI().addActivePlayerParticle(player, ParticleEffect.DUST, ParticleStyle.fromName("normal"),
                        new OrdinaryColor(fighter.getFKit().getArmorColor().getRed(),
                                fighter.getFKit().getArmorColor().getGreen(), fighter.getFKit().getArmorColor().getBlue()));
                fighter.fighterAbilityManager.makeEnchanted(this.player);
            }
        } else {
            if (VanaByte.getPpAPI() != null) {
                VanaByte.getPpAPI().removeActivePlayerParticles(player, ParticleStyle.fromName("normal"));
                fighter.fighterAbilityManager.removeEnchanted(this.player);
            }
        }
    }

    protected void changeAbilityRechargedParticleEffect() {
        if (this.abilityRecharged) {
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

    protected void setActionBarToFloat(float setter){
        String text = "";
        if(setter != 0){
            for(int i = 0; i < 40; i++){
                if(i < (40 * setter)){
                    text = text.concat("|");
                }else{
                    text = text.concat(":");
                }

            }
        }
        TextComponent message = new TextComponent(text);
        if(setter == 1){
            message.setColor(net.md_5.bungee.api.ChatColor.of(Color.GREEN));
        }else{
            message.setColor(net.md_5.bungee.api.ChatColor.of("#FF99CC"));
        }
        message.setBold(true);
        this.player.spigot().sendMessage(ChatMessageType.ACTION_BAR, message);
    }

    protected static void makeEnchanted(Player player) {

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

    protected static void removeEnchanted(Player player) {
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

    public boolean isAbilityActive() {
        return this.abilityActive;
    }

}
