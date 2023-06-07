package me.cade.vanabyte.ScoreBoard;

public class Experience {
  
  private static int[] expNeeded;
  
  public static void makeExpNeeded() {
    expNeeded = new int[29];
    int exp = 3000;
    for(int i = 0; i < 28; i++) {
      expNeeded[i] = exp;
      exp = exp + 105;
    }
    expNeeded[28] = 0;
  }

  public static int[] getArray() {
    return expNeeded;
  }

  public static int getExpNeeded(int indexOfLevelToBecomeNext) {
    return expNeeded[indexOfLevelToBecomeNext];
  }

}
