package me.cade.vanabyte.Fighters;

import me.cade.vanabyte.Fighters.WeaponHolders.W6_GriefSword;
import me.cade.vanabyte.Fighters.WeaponHolders.WeaponHolder;
import org.bukkit.entity.Player;

public class FighterAbilityManager {

    public enum FighterParticlesEnum {NONE, RECHARGED, ACTIVATED}

    private Player player = null;
    private Fighter fighter = null;

    public FighterAbilityManager(Fighter fighter) {
        this.fighter = fighter;
        this.player = this.fighter.getPlayer();
    }

    public void fighterJoined() {
        this.clearFighterParticles();
        this.resetAllWeaponAbilities();
        this.setAbilityParticles(FighterParticlesEnum.NONE, java.awt.Color.WHITE);
    }

    public void fighterDied() {

    }

    public void fighterLeftServer() {

    }

    public void fighterChangedWorld() {
        this.clearFighterParticles();
        this.resetAllWeaponAbilities();
        this.setAbilityParticles(FighterParticlesEnum.NONE, java.awt.Color.WHITE);
    }

    public void fighterRespawned() {
        this.clearFighterParticles();
        this.resetAllWeaponAbilities();
        this.setAbilityParticles(FighterParticlesEnum.NONE, java.awt.Color.WHITE);
    }

    public void fighterGotNewKit() {
        this.clearFighterParticles();
        this.resetAllWeaponAbilities();
        this.setAbilityParticles(FighterParticlesEnum.NONE, java.awt.Color.WHITE);
    }

    public void clearFighterParticles() {
        //VanaByte.getPpAPI().resetActivePlayerParticles(player);
    }

    public void resetAllWeaponAbilities() {
        for (WeaponHolder weaponHolder : fighter.getFighterKitManager().getWeaponHolders()) {
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
        for (WeaponHolder weaponHolder : fighter.getFighterKitManager().getWeaponHolders()) {
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

    public void setAbilityParticles(FighterParticlesEnum fp, java.awt.Color color) {
//        if (VanaByte.getPpAPI() == null) {
//            return;
//        }
        switch (fp) {
            case NONE:
                this.clearFighterParticles();
                break;
            case RECHARGED:
//                VanaByte.getPpAPI().removeActivePlayerParticles(player, ParticleStyle.fromName("normal"));
//                VanaByte.getPpAPI().removeActivePlayerParticles(player, ParticleStyle.fromName("point"));
//                VanaByte.getPpAPI().addActivePlayerParticle(player, ParticleEffect.DUST, ParticleStyle.fromName("point"),
//                        new OrdinaryColor(color.getRed(),
//                                color.getGreen(), color.getBlue()));
                break;
            case ACTIVATED:
//                VanaByte.getPpAPI().removeActivePlayerParticles(player, ParticleStyle.fromName("point"));
//                VanaByte.getPpAPI().removeActivePlayerParticles(player, ParticleStyle.fromName("normal"));
//                VanaByte.getPpAPI().addActivePlayerParticle(player, ParticleEffect.DUST, ParticleStyle.fromName("normal"),
//                        new OrdinaryColor(color.getRed(),
//                                color.getGreen(), color.getBlue()));
//                // code blockc
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
            this.setAbilityParticles(FighterParticlesEnum.ACTIVATED, weaponHolder.getWeaponType().getTextColor().asBungee().getColor());
            return;
        }else if(weaponHolder.getWeaponAbility().isAbilityRecharged()){
            this.setAbilityParticles(FighterParticlesEnum.RECHARGED, weaponHolder.getWeaponType().getTextColor().asBungee().getColor());
            return;
        }
        this.setAbilityParticles(FighterParticlesEnum.NONE, weaponHolder.getWeaponType().getTextColor().asBungee().getColor());
    }

}


