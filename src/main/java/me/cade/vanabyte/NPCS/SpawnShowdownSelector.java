package me.cade.vanabyte.NPCS;

import me.cade.vanabyte.VanaByte;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.entity.Cat;
import org.bukkit.entity.EntityType;

public class SpawnShowdownSelector {

    private static Location location =
            new Location(VanaByte.hub, -1052.5, 195, -112.5, 180, 0);

    private static Location joinlocation =
            new Location(VanaByte.hub, -1052.5, 193.45, -112.5, 180, 0);

    private static String name = ChatColor.AQUA + "" + ChatColor.BOLD + "Vana's Battle Royale";
    private static String join = ChatColor.GREEN + "" + ChatColor.BOLD + "Click to Join";

    private static D1_LivingEntity battleroyale = null;

    public static void spawn() {
        battleroyale = new D1_LivingEntity(EntityType.CAT, name, location, null, null, null);
        Cat vana = (Cat) battleroyale.getEntity();
        vana.setCatType(Cat.Type.BRITISH_SHORTHAIR);
        vana.setCollarColor(DyeColor.MAGENTA);
        new D1_ArmorStand(join, joinlocation, 180, false, false, false);
    }
}
