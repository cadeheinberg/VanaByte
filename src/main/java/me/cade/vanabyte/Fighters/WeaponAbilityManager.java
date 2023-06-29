package me.cade.vanabyte.Fighters;

import dev.esophose.playerparticles.particles.ParticleEffect;
import dev.esophose.playerparticles.particles.data.OrdinaryColor;
import dev.esophose.playerparticles.styles.ParticleStyle;
import me.cade.vanabyte.Fighters.Weapons.W6_GriefSword;
import me.cade.vanabyte.Fighters.Weapons.WeaponHolder;
import me.cade.vanabyte.VanaByte;
import org.bukkit.entity.Player;

public class WeaponAbilityManager {

    public enum FighterParticlesEnum {NONE, RECHARGED, ACTIVATED}

    private Player player = null;
    private Fighter fighter = null;

    public WeaponAbilityManager(Fighter fighter) {
        this.fighter = fighter;
        this.player = this.fighter.getPlayer();
    }

    protected void fighterJoined() {
        this.clearFighterParticles();
        this.resetAllWeaponAbilities();
        this.setAbilityParticles(FighterParticlesEnum.NONE, java.awt.Color.WHITE);
    }

    protected void fighterDied() {

    }

    protected void fighterLeftServer() {

    }

    protected void fighterChangedWorld() {
        this.clearFighterParticles();
        this.resetAllWeaponAbilities();
        this.setAbilityParticles(FighterParticlesEnum.NONE, java.awt.Color.WHITE);
    }

    protected void fighterRespawned() {
        this.clearFighterParticles();
        this.resetAllWeaponAbilities();
        this.setAbilityParticles(FighterParticlesEnum.NONE, java.awt.Color.WHITE);
    }

    protected void fighterGotNewKit() {
        this.clearFighterParticles();
        this.resetAllWeaponAbilities();
        this.setAbilityParticles(FighterParticlesEnum.NONE, java.awt.Color.WHITE);
    }

    public void clearFighterParticles() {
        VanaByte.getPpAPI().resetActivePlayerParticles(player);
    }

    public void resetAllWeaponAbilities() {
        for (WeaponHolder weaponHolder : fighter.getFKit().getWeaponHolders()) {
            if (weaponHolder == null) {
                continue;
            }
            if (weaponHolder.getWeaponAbility() == null) {
                continue;
            }
            weaponHolder.getWeaponAbility().resetSpecialAbility();
        }
    }

    public WeaponHolder isSomeWeaponAbilityActive() {
        for (WeaponHolder weaponHolder : fighter.getFKit().getWeaponHolders()) {
            if (weaponHolder == null) {
                continue;
            }
            if (weaponHolder.getWeapon() == null) {
                continue;
            }
            if (weaponHolder.getWeapon().getWeaponItem() == null) {
                continue;
            }
            if (weaponHolder.getWeaponAbility() == null) {
                continue;
            }
            if (weaponHolder.getWeaponAbility().isAbilityActive()) {
                return weaponHolder;
            }
        }
        return null;
    }

    private void setAbilityParticles(FighterParticlesEnum fp, java.awt.Color color) {
        if (VanaByte.getPpAPI() == null) {
            return;
        }
        switch (fp) {
            case NONE:
                this.clearFighterParticles();
                break;
            case RECHARGED:
                VanaByte.getPpAPI().removeActivePlayerParticles(player, ParticleStyle.fromName("normal"));
//                VanaByte.getPpAPI().removeActivePlayerParticles(player, ParticleStyle.fromName("point"));
//                VanaByte.getPpAPI().addActivePlayerParticle(player, ParticleEffect.DUST, ParticleStyle.fromName("point"),
//                        new OrdinaryColor(color.getRed(),
//                                color.getGreen(), color.getBlue()));
                break;
            case ACTIVATED:
//                VanaByte.getPpAPI().removeActivePlayerParticles(player, ParticleStyle.fromName("point"));
                VanaByte.getPpAPI().removeActivePlayerParticles(player, ParticleStyle.fromName("normal"));
                VanaByte.getPpAPI().addActivePlayerParticle(player, ParticleEffect.DUST, ParticleStyle.fromName("normal"),
                        new OrdinaryColor(color.getRed(),
                                color.getGreen(), color.getBlue()));
                // code blockc
                break;
            default:
                //set to none
                player.sendMessage("Issue with your particles, tell a Mod now!!!!");
                break;
        }
    }

    public void updateAbilityParticles(WeaponHolder weaponHolder) {
        if (weaponHolder instanceof W6_GriefSword) {
            // greif goes invisible
            return;
        }
        if (weaponHolder.getWeaponAbility().isAbilityActive()) {
            this.setAbilityParticles(FighterParticlesEnum.ACTIVATED, weaponHolder.getWeaponNameColor().asBungee().getColor());
            return;
        }else if(weaponHolder.getWeaponAbility().isAbilityRecharged()){
            this.setAbilityParticles(FighterParticlesEnum.RECHARGED, weaponHolder.getWeaponNameColor().asBungee().getColor());
            return;
        }
        this.setAbilityParticles(FighterParticlesEnum.NONE, weaponHolder.getWeaponNameColor().asBungee().getColor());
    }

}
//    public static void makeEnchanted(Player player) {
//
//        ItemStack helmet = player.getEquipment().getHelmet();
//        ItemStack chest = player.getEquipment().getChestplate();
//        ItemStack leggings = player.getEquipment().getLeggings();
//        ItemStack boots = player.getEquipment().getBoots();
//
//        if(helmet != null){
//            helmet.addEnchantment(Enchantment.DURABILITY, 1);
//        }
//        if(chest != null){
//            chest.addEnchantment(Enchantment.DURABILITY, 1);
//        }
//        if(leggings != null){
//            leggings.addEnchantment(Enchantment.DURABILITY, 1);
//        }
//        if(boots != null){
//            boots.addEnchantment(Enchantment.DURABILITY, 1);
//        }
//
//        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
//            if(helmet != null){
//                p.sendEquipmentChange(player, EquipmentSlot.HEAD, helmet);
//            }
//            if(chest != null){
//                p.sendEquipmentChange(player, EquipmentSlot.CHEST, chest);
//            }
//            if(leggings != null){
//                p.sendEquipmentChange(player, EquipmentSlot.LEGS, leggings);
//            }
//            if(boots != null){
//                p.sendEquipmentChange(player, EquipmentSlot.FEET,  boots);
//            }
//        }
//    }
//
//    public static void removeEnchanted(Player player) {
//        ItemStack helmet = player.getEquipment().getHelmet();
//        ItemStack chest = player.getEquipment().getChestplate();
//        ItemStack leggings = player.getEquipment().getLeggings();
//        ItemStack boots = player.getEquipment().getBoots();
//
//        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
//            if(helmet != null){
//                p.sendEquipmentChange(player, EquipmentSlot.HEAD, helmet);
//            }
//            if(chest != null){
//                p.sendEquipmentChange(player, EquipmentSlot.CHEST, chest);
//            }
//            if(leggings != null){
//                p.sendEquipmentChange(player, EquipmentSlot.LEGS, leggings);
//            }
//            if(boots != null){
//                p.sendEquipmentChange(player, EquipmentSlot.FEET,  boots);
//            }
//        }
//    }


