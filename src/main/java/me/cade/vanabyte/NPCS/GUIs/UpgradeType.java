package me.cade.vanabyte.NPCS.GUIs;

public enum UpgradeType {
        RIGHT_CLICK_COOLDOWN("right click cooldown"),
        MELEE_DAMAGE("melee damage"),
        SPECIAL_ABILITY_COOLDOWN("special ability cooldown"),
        //grief specific
        EXPLOSION_IMMUNE_WHEN_SPECIAL_ACTIVATED("become explosion immune when special is activated");

        private final String name;

        private UpgradeType(String name){
                this.name = name;
        }

        public String getName() {
                return this.name;
        }
}
