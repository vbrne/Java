/*******************************************************************************
 *
 * University of Texas Rio Grande Valley
 * Computer Engineering
 * Senior Design 1
 * Spring 2020
 * Group 15: Bernie VIllalon, Samuel Solis, Leo Marroquin
 *
 * We assume we pass a fixed number of readings in sweep0.txt file;
 * First 8 are Voltages before R_1, Second 8 are Voltages after R_1;
 * Third 8 are Voltages before R_2, Fourth 8 are Voltages after R_2;
 * Fifth 8 are Voltages before R_3, Sixth 8 are Voltages after R_3;
 *  -- R_N := Resistor at Nth pin
 *
 ******************************************************************************/

 /* CURRENTLY NOT IN DEVELOPEMENT */

//package lib;

public class Pins {
  public static class Voltages {
    private int gate, drain, source;
    public Voltages(double[] ins) {
      double[]  V_1b = new double[8], V_1a = new double[8],
                V_2b = new double[8], V_2a = new double[8],
                V_3b = new double[8], V_3a = new double[8];
      for (int i = 0; i < ins.length; i++)
        if (i < 8)
          V_1b[i] = ins[i];
        else if (i < 16)
          V_1a[i - 8] = ins[i];
        else if (i < 24)
          V_2b[i - 16] = ins[i];
        else if (i < 32)
          V_2a[i - 24] = ins[i];
        else if (i < 40)
          V_3b[i - 32] = ins[i];
        else
          V_3a[i - 40] = ins[i];

      double[] V_1 = pairer(V_1b, V_1a);
      double[] V_2 = pairer(V_2b, V_2a);
      double[] V_3 = pairer(V_3b, V_2a);

      gate = getGate(V_1, V_2, V_3);
      drain = getDrain(V_1, V_2, V_3);
      source = 6 - gate - drain;
    }

    public int gate() { return gate; }
    public int drain() { return drain; }
    public int source() { return source; }

    private double[] pairer(double[] bef, double[] aft) {
      if (bef.length != aft.length)
        throw new IllegalArgumentException("Array lengths are not equal");
      double[] temp = new double[bef.length];
      for (int i = 0; i < bef.length; i++)
        temp[i] = bef[i] - aft[i];
      return temp;
    }

    private int getGate(double[] one, double[] two, double[] tre) {
      boolean f = true;
      for (int i = 0; i < one.length; i++)
        if (one[i] > 0.5)
          f = false;
      if (f)
        return 1;

      f = true;
      for (int i = 0; i < two.length; i++)
        if (two[i] > 0.5)
          f = false;
      if (f)
        return 2;

      f = true;
      for (int i = 0; i < tre.length; i++)
        if (tre[i] > 0.5)
          f = false;
      if (f)
        return 3;

      return -1;
    }

    private int getDrain(double[] one, double[] two, double[] tre) {
      int count = 0;
      for (int i = 0; i < one.length; i++)
        if (one[i] < 0)
          count++;
      if (count == 2)
        return 1;

      count = 0;
      for (int i = 0; i < two.length; i++)
        if (two[i] < 0)
          count++;
      if (count == 2)
        return 2;

      count = 0;
      for (int i = 0; i < tre.length; i++)
        if (tre[i] < 0)
          count++;
      if (count == 2)
        return 3;

      return -1;
    }
  }

// *** Main Program ************************************************************

  public static void main(String[] args) {
    // Gate = 2, Drain = 1, Source = 3
    //double[] vals = {      0,0  ,0,0  ,5,5,5  ,5,      0,2.5,0,2.5,0,0,2.5,0,      0,0  ,5,5  ,0,0,5  ,5,      0,0  ,5,5  ,0,0,5  ,5,      0,5  ,0,5  ,0,5,2.5,5,      0,2.5,0,2.5,0,0,0  ,0    };
    // Gate = 1, Drain = 2, Source = 3
    //double[] vals = {      0,0  ,5,5  ,0,0,5  ,5,      0,0  ,5,5  ,0,0,5  ,5,      0,0  ,0,0  ,5,5,5  ,5,      0,2.5,0,2.5,0,0,2.5,0,      0,5  ,0,5  ,0,5,2.5,5,      0,2.5,0,2.5,0,0,0  ,0    };
    // Gate = 1,  Drain = 3, Source = 2
    double[] vals = {      0,0  ,5,5  ,0,0,5  ,5,      0,0  ,5,5  ,0,0,5  ,5,      0,5  ,0,5  ,0,5,2.5,5,      0,2.5,0,2.5,0,0,0  ,0,      0,0  ,0,0  ,5,5,5  ,5,      0,2.5,0,2.5,0,0,2.5,0    };

    Voltages DUT = new Voltages(vals);
    System.out.println("Gate   Pin: " + DUT.gate);
    System.out.println("Drain  Pin: " + DUT.drain);
    System.out.println("Source Pin: " + DUT.source);
  }
}
