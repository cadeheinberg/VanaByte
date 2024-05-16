package me.cade.vanabyte.FighterWeapons;

import me.cade.vanabyte.FighterWeapons.InUseWeapons.WeaponHolder;
import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.Fighters.FighterKitManager;
import me.cade.vanabyte.VanaByte;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.*;

public class WeaponAbility {
    private Fighter fighter = null;
    private Player player = null;
    private WeaponHolder weaponHolder = null;
    private FighterAbilityManager fighterAbilityManager = null;
    private boolean abilityActive = false;
    private boolean abilityRecharged = true;

    private WeaponAbility weaponAbility = null;

    public WeaponAbility(Fighter fighter, WeaponHolder weaponHolder){
        this.fighter = fighter;
        this.player = fighter.getPlayer();
        this.weaponHolder = weaponHolder;
        this.fighterAbilityManager = fighter.getWeaponAbilityManager();
        this.weaponAbility = this;
    }
    public void resetSpecialAbility(){
        this.abilityActive = false;
        this.abilityRecharged = true;
    }

    public void startAbilityDuration() {
        this.setAbilityActive(true);
        this.setAbilityRecharged(false);
        int durationTicks = this.weaponHolder.getAbilityDurationTicks();
        player.setCooldown(FighterKitManager.cooldownMaterial, durationTicks);
        int cooldownTask = new BukkitRunnable() {
            @Override
            public void run() {
                //Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Cooldown Task Running");
                if (player != null) {
                    if (!player.isOnline()) {
                        return;
                    }
                    if (player.getCooldown(FighterKitManager.cooldownMaterial) < 1) {
                        cancel();
                        weaponAbility.setAbilityActive(false);
                        weaponAbility.setActionBarToFloat(0);
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_BREAK, 8, 1);
                        startAbilityRecharge();
                        return;
                    }
                    weaponAbility.setActionBarToFloat(((float) player.getCooldown(FighterKitManager.cooldownMaterial)) / durationTicks);
                }
            }
        }.runTaskTimer(VanaByte.getInstance(), 0L, 1L).getTaskId();
        Fighter.get(player).setCooldownTask(cooldownTask);
    }

    public void startAbilityRecharge() {
        int rechargeTicks = weaponHolder.getAbilityRechargeTicks();
        player.setCooldown(FighterKitManager.cooldownMaterial, rechargeTicks);
        int rechargeTask = new BukkitRunnable() {
            @Override
            public void run() {
                //Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Recharge Task Running");
                if (player != null) {
                    if (!player.isOnline()) {
                        return;
                    }
                    if (player.getCooldown(FighterKitManager.cooldownMaterial) < 1) {
                        cancel();
                        weaponAbility.setAbilityRecharged(true);
                        weaponAbility.setActionBarToFloat(1);
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 8, 1);
                        return;
                    }
                    weaponAbility.setActionBarToFloat(
                            ((float) (rechargeTicks - player.getCooldown(FighterKitManager.cooldownMaterial)) / rechargeTicks));
                }
            }
        }.runTaskTimer(VanaByte.getInstance(), 0L, 1L).getTaskId();
        Fighter.get(player).setRechargeTask(rechargeTask);
    }

    public void setAbilityActive(boolean fighterAbility) {
        if (!fighterAbility) {
            // turning ability active off
            // only if the ability was already active
            if (this.abilityActive) {
                player.setCooldown(FighterKitManager.cooldownMaterial, 0);
                fighter.getFighterTaskManager().cancelCooldownTask();
                weaponHolder.deActivateSpecial();
            }
        }
        this.abilityActive = fighterAbility;
        fighterAbilityManager.updateAbilityParticles(weaponHolder);
    }

    public void setAbilityRecharged(boolean fighterRecharged) {
        if (fighterRecharged) {
            // turning ability charged fully on
            // only if the ability was already not recharged
            if (!this.abilityRecharged) {
                player.setCooldown(Material.BARRIER, 0);
                fighter.getFighterTaskManager().cancelRechargeTask();
            }
        }
        this.abilityRecharged = fighterRecharged;
        fighterAbilityManager.updateAbilityParticles(weaponHolder);
    }


    public void setActionBarToFloat(float setter){
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
//            message.setColor(net.md_5.bungee.api.ChatColor.of("#FF99CC"));
            message.setColor(weaponHolder.getWeaponNameColor().asBungee());
        }
        message.setBold(true);
        this.player.spigot().sendMessage(ChatMessageType.ACTION_BAR, message);
    }

    public boolean isAbilityActive() {
        return this.abilityActive;
    }

    public boolean isAbilityRecharged() {
        return this.abilityRecharged;
    }

}
