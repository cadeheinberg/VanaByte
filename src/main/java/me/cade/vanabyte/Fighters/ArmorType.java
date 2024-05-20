package me.cade.vanabyte.Fighters;

public enum ArmorType {
    SPECIAL_ARMOR("special armor"),
    UNKOWN_ARMOR("unknown");

    private final String name;

    private ArmorType(String name){
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}

