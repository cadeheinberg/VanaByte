package me.cade.vanabyte.Fighters.Enums;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.Arrays;

public class StatBundle {

    private String[] names;
    private Integer[] upgradeLevels;
    private Double[][] stats;

    public StatBundle(String[] strings, Integer[] integers, Double[][] doubles) {
        if(strings.length != integers.length || strings.length != doubles.length){
            Bukkit.getConsoleSender().sendMessage("\n" + Arrays.toString(strings));
            throw new RuntimeException(ChatColor.RED + "StatBundle lengths not correct, check WeaponType.java");
        }
        this.names = strings;
        this.upgradeLevels = integers;
        this.stats = doubles;
    }

    public String[] getNames() {
        return names;
    }

    public Integer[] getUpgradeLevels() {
        return upgradeLevels;
    }

    public Double[][] getStats() {
        return stats;
    }

}
