package me.cade.vanabyte.Fighters;

import me.cade.vanabyte.VanaByte;
import org.bukkit.entity.Entity;
import org.bukkit.metadata.FixedMetadataValue;

public class FighterProjectile {

    public static void addMetadataToProjectile(Entity projectile){
        projectile.setMetadata("correct_fighter", new FixedMetadataValue(VanaByte.getInstance(), true));
    }

    public static boolean projectileHasMetadata(Entity projectile){
        return projectile.hasMetadata("correct_fighter");
    }
}