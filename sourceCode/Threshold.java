/*******************************************************************************
 *
 * University of Texas Rio Grande Valley
 * Computer Engineering
 * Senior Design
 * Spring/Fall 2020
 * Group 15: Bernie Villalon, Samuel Solis, Leo Marroquin
 *
 * Description:
 *   This class is responsible for deriving the Threshold Voltage labeled as
 * 'THRESHOLD', the Transconductor Parameter labeled as 'KN', and also storing
 * useful data used for said processes. This is accomplished through
 * formatting the data and calculations.
 * ~Formatting:
 *   Currently there's no set way we've decided to format communications, but
 * in general the end goal is to have one array for Voltages Tested (VGS) and
 * the output Current (IDS). We then find the 'usable' data for calculations,
 * being all indeces where the Current (IDS) is greater than 5mA, then we
 * use the new arrays for the calculations.
 * ~Calculations:
 *   We use the formatted arrays and apply Linear Regression using the
 * LinearFit Class to get a Linear Fit of the data. Afterwards, we use the
 * derived Slope and Intercept to calculate 'THRESHOLD' and 'KN'.
 *
 ******************************************************************************/

//package lib;

public class Threshold {
  public double THRESHOLD, KN, INTERCEPT, SLOPE;
  public double[] VGS, IDS, sqrtIDS, I_eq, V_GS_eq;

  public Threshold(double[] vals) {
  // Formatting
    double[] V_G = new double[21], V_S = new double[21], I = new double[21];  // Divides values do each pin
    for (int i = 0; i < vals.length; i++) {
      if (i < 21)
        V_G[i] = vals[i];     // The first 21 values are the Voltages at the Gate pin
      else if (i < 42)
        V_S[i-21] = vals[i];  // The second 21 values are the Voltages at the Source pin
      else
        I[i-42] = vals[i];    // The final 21 values is the Current flowing through the load resistor
    }

    sqrtIDS = sqrtArr(I);

    double[] V_GS = subtract(V_G, V_S);       // Makes difference in V_GS array

  // Find Saturation  Region
    int start = currStart(I);                 // Finds starting indices of the Saturation Region

    I_eq = newArr(start, sqrtIDS);  // Current at the Saturation Region
    V_GS_eq = newArr(start, V_GS);  // Gate voltages at the Saturation Region

  // Linear Regression
    LinearFit fit = new LinearFit(V_GS_eq, I_eq); // Applies LinearReagression to Plot values

  //Calculations
    INTERCEPT = fit.intercept;  System.out.println("TH Intercept: " + INTERCEPT);
    SLOPE = fit.slope;          System.out.println("TH Slope:     " + SLOPE);
    THRESHOLD = (INTERCEPT * -1) / SLOPE; // Solves for Vth
    KN = 2 * SLOPE * SLOPE;               // Solves for kn
    VGS = V_GS;
    IDS = I;
  }

  // Simply prints array; used for testing purposes
  public static void printArr(double[] arr) {
    for (int i = 0; i < arr.length; i++)
      System.out.println(arr[i]);
    System.out.println();
  }

  // Returns the difference of two arrays; ans = arr1 - arr2
  public static double[] subtract(double[] arr1, double[] arr2) {
    double[] ans = new double[arr1.length];
    if (arr1.length != arr2.length)
      throw new IllegalArgumentException("Array lengths are not equal");
    else {
      for (int i = 0; i < arr1.length; i++)
        ans[i] = arr1[i] - arr2[i];
      return ans;
    }
  }

  // Finds the starting index of the Current array
  public static int currStart(double[] curr) {
    int start = 0;
    for (int i = 0; i < curr.length; i++) {
      if (curr[i] < 5)
        start = 0;          // If the current is/falls below 5mA, reset starting index
      else if (start == 0)  // If the current is greater than 5mA and the starting
        start = i;          //  index hasn't been set, set it to current index.
    }
    return start;
  }

  // Returns new Current array consisting of only the Saturation Region values.
  public static double[] sqrtArr(double[] arr) {
    double[] temp = new double[arr.length];
    for (int i = 0; i < temp.length; i++)
      temp[i] = Math.sqrt(arr[i]);  //Square Roots the values to match equation
    return temp;
  }

  // Returns new Voltage array consisting of only the Saturation Region values.
  public static double[] newArr(int start, double[] arr) {
    double[] temp = new double[arr.length - start];
    for (int i = 0; i < temp.length; i++)
      temp[i] = arr[i + start];
    return temp;
  }

//*** ~test~ Program *************************************************************

    /****************************************************************************
    *                                                                           *
    *     Since we currently don't have working communications between the      *
    *   software and hardware, we will be using data from a previous seme-      *
    *   ster to varify if the math behind this code is working. All the         *
    *   arrays below listed as double [] vals are said data.                    *
    *                                                                           *
    ****************************************************************************/
public static void main(String[] args) {
    // Jr Lab IRF Example Data:
    //double[] vals = {0.000,0.500,1.000,1.500,2.000,2.500,3.000,3.500,4.000,4.500,5.000,5.500,6.000,6.500,7.000,7.500,8.000,8.500,9.000,9.500,9.992,0.000,0.000,0.000,0.000,0.000,0.000,0.035,0.279,0.658,1.072,1.513,1.960,2.419,2.883,3.350,3.814,4.279,4.747,5.217,5.691,6.156, 0.000,0.000,0.000,0.000,0.000,0.000,0.353,2.813,6.634,10.809,15.255,19.762,24.390,29.068,33.777,38.455,43.144,47.862,52.601,57.381,62.069};
    // Jr Lab 2N7000 Example Data:
    //double[] vals = {0.000,0.500,1.000,1.500,2.000,2.500,3.000,3.500,4.000,4.500,5.000,5.500,6.000,6.500,7.000,7.500,8.000,8.500,9.000,9.500,9.987,0.030,0.000,0.000,0.000,0.008,0.213,0.600,1.030,1.479,1.937,2.399,2.871,3.342,3.811,4.280,4.748,5.215,5.686,6.153,6.620,7.153,0.302,0.000,0.000,0.000,0.081,2.148,6.050,10.385,14.912,19.530,24.188,28.947,33.696,38.425,43.154,47.873,52.581,57.330,62.039,66.747,55.848};
    // Jr Lab IRF Example Data 2:
    double [] vals = {0.0000,0.5000,1.0000,1.5000,2.0000,2.5020,3.0000,3.5000,4.0000,4.5000,5.0000,5.5000,6.0000,6.5000,7.0000,7.5000,8.0000,8.5000,9.0000,9.5000,10.0000,0.0000,0.0000,0.0000,0.0000,0.0000,0.0003,0.0328,0.2755,0.6489,1.0648,1.5206,1.9683,2.4287,2.8887,3.3562,3.8261,4.3054,4.7690,5.2460,5.7210,6.2060,0,0,0,0,0,0.002556237,0.335685072,2.816871166,6.634969325,10.88752556,15.54805726,20.12576687,24.83333333,29.53680982,34.31697342,39.12167689,44.02249489,48.76278119,53.6400818,58.49693252,63.45603272};
    // Jr Lab 2N7000 Example Data 2:
    //double[] vals = {0.001,0.505,1.002,1.506,2.010,2.504,3.000,3.502,4.000,4.500,5.000,5.511,6.004,6.501,7.030,7.509,8.020,8.503,9.000,9.507,9.950,0.000,0.000,0.000,0.000,0.000,0.170,0.541,0.964,1.412,1.862,2.314,2.793,3.253,3.710,4.214,4.661,5.150,5.606,6.070,6.550,6.980,0.000408998,0.000306748,0.000306748,0.000408998,0.000408998,1.738241309,5.531697342,9.856850716,14.43762781,19.03885481,23.6605317,28.55828221,33.26175869,37.93456033,43.08793456,47.65848671,52.65848671,57.32106339,62.06543967,66.97341513,71.37014315};

    Threshold test = new Threshold(vals);
    System.out.println("Threshold: " + test.THRESHOLD);
    System.out.println("K_n: " + test.KN);
  }
}
