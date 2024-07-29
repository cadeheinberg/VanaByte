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

    public StatRow getStatRowWithName(String name){
        for(StatRow statRow : statRows){
            if(statRow.getStatName().equals(name)){
                return statRow;
            }
        }
        return null;
    }

    public int getStatRowIndexWithName(String name){
        for(int i = 0; i < statRows.length; i++){
            if(statRows[i].getStatName().equals(name)){
                return i;
            }
        }
        return -1;
    }

}
