package me.cade.vanabyte.Fighters.Enums;

public class StatRow {

    private final String statName;
    private final Integer defaultUpgradeLevel;
    private final Double[] stats;

    public StatRow(String statName, Integer defaultUpgradeLevel, Double[] stats) {
        this.statName = statName;
        this.defaultUpgradeLevel = defaultUpgradeLevel;
        this.stats = stats;
    }

    public String getStatName() {
        return statName;
    }

    public Integer getDefaultUpgradeLevel() {
        return defaultUpgradeLevel;
    }

    public Double[] getStats() {
        return stats;
    }
}
