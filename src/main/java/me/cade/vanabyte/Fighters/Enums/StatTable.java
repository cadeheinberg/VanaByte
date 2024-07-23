package me.cade.vanabyte.Fighters.Enums;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.Arrays;

public class StatTable {

    private final StatRow[] statRows;

    public StatTable(StatRow[] statRows) {
        this.statRows = statRows;
    }

    public StatRow[] getStatRows() {
        return statRows;
    }

//    public String[] getNames() {
//        return names;
//    }
//
//    public Integer[] getUpgradeLevels() {
//        return upgradeLevels;
//    }
//
//    public Double[][] getStats() {
//        return stats;
//    }

}
