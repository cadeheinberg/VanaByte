package me.cade.vanabyte.NPCS.GUIs;

public enum UpgradeAlgebraType {
    SUBTRACTION("-"),
    ADDITION("+"),
    MULTIPLICATION("*"),
    DIVISION("/");

    private final String name;

    private UpgradeAlgebraType(String name){
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
