package me.cade.vanabyte.Money;

import org.bukkit.Material;

public class A_CakeManager {
  
  public static final String currencyNameSingular = "Cake";
  public static final String currencyNamePlural = "Cakes";
  
  public static final Material cakeMaterial = Material.CAKE;
  
  public static final int cakeMoneyWorth = 1;
  public static final int cakeExpWorth = 5;
  public static final int cakeRechargeMultiplier = 1;
  
  public static void startCakePackage() {
    SpawnCakeTags.makeGrassCake("0");
    SpawnCakeTags.makeIceCake("0");
    SpawnCakeTags.makeSandCake("0");
    CakeDropper.startDropping();
  }
  
  public static void stopCakePackage() {
    CakeDropper.endDropping();
  }

}
