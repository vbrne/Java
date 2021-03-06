/*******************************************************************************
 *
 * University of Texas Rio Grande Valley
 * Computer Engineering
 * Senior Design
 * Spring/Fall 2020
 * Group 15: Bernie Villalon, Samuel Solis, Leo Marroquin
 *
 * Description:
 *   This class is responsible for deriving the lambda constant, labeled as
 * Lambda, and also store useful data used for said process. This is
 * accomplished through formatting the data and calculations.
 * ~Formatting:
 *   Currently there's no set way we've decided to format communications, but
 * in general the end goal is to have one array for Voltages Tested (VDS) and
 * the output Current (IDS). We then find the 'usable' data for caluclations,
 * being all indeces where the Current (IDS) begins to plateau and increase
 * linearly, then we use the new arrays for the calculations.
 * ~Calculations:
 *   We use the formatted arrays and apply Linear Regression using the
 * LinearFit Class to get a Linear Fit of the data. Afterwards, we use the
 * derived Slope and Intercept to caluclate 'LAMBDA'.
 *
 ******************************************************************************/

//package lib;

public class Lambda {
  public double LAMBDA, RESIST, INTERCEPT, SLOPE;
  public double[] VDS, IDS, tri_V_DS, tri_I_DS, sat_V_DS, sat_I_DS;

  public Lambda(Data sweep) {
  // Formatting Array; going to be converted to taking Data Object as input
    double[] V_DS = sweep.vds;
    double[] I = sweep.ids;

  // Finding Saturation and Triode Regions
    int tLow = 0;             // Sets the Lower Bound for the Triode Region
    int tUp = tUpper(I);      // Sets the Upper Bound for the Triode Region
    int sLow = sLower(I);     // Sets the Lower Bound for the Saturation Region
    int sUp = I.length - 1;   // Sets the Upper Bound for the Saturation Region

  // Linear Regression
    tri_V_DS = range(V_DS, tLow, tUp);
    tri_I_DS = range(I, tLow, tUp);
    LinearFit Triode = new LinearFit(tri_V_DS, tri_I_DS);
    sat_V_DS = range(V_DS, sLow, sUp);
    sat_I_DS = range(I, sLow, sUp);
    LinearFit Saturation = new LinearFit(sat_V_DS, sat_I_DS);

    INTERCEPT = Saturation.intercept;
    SLOPE = Saturation.slope;

  // Calculations
    LAMBDA = Saturation.slope / Saturation.intercept;
    RESIST = 1 / Triode.slope;  // This is optional but I'm still not sure about this one
    VDS = V_DS;
    IDS = I;
  }

  // Finds Triode Upper Bound Indices
  private int tUpper(double[] x) {
    int upper = 0;
    for (int i = 1; i < x.length; i++)
      if (x[i - 1] / x[i] < 0.915)  // If the previous value is less than 91.5% of the current value, set the upper Bound
        upper = i;                  //  if it's any closer, then don't, this is to show the points are still some-what
                                    //  'rapidly' increasing.
    return upper;
  }

  // Finds Saturation Lower Bound Indices
  private int sLower(double[] x) {
    int lower = 0;
    for (int i = 1; i < x.length; i++)
      if (!(x[i - 1] / x[i] > 0.97))  // If the previous value is less than 97% of the current value, set the lower
        lower = i;                    //  Bond, we do this because we want the part of the array that starts to plateau

    return lower;
  }

  // Calculates diffrence between two arrays; temp = x - y
  private double[] pairer(double[] x, double[] y) {
    if (x.length != y.length)
      throw new IllegalArgumentException("Array lengths are not equal");
    double[] temp = new double[x.length];
    for (int i = 0; i < temp.length; i++ )
      temp[i] = x[i] - y[i];
    return temp;
  }

  // Returns partition of array x from indices start to end
  private double[] range(double[] x, int start, int end) {
    double[] temp = new double[end - start];
    for (int i = 0; i < temp.length; i++)
      temp[i] = x[i + start];
    return temp;
  }

  /****************************************************************************
  *                                                                           *
  *     Since we currently don't have working communications between the      *
  *   software and hardware, we will be using data from a previous seme-      *
  *   ster to varify if the math behind this code is working. All the         *
  *   arrays below listed as double [] vals are said data.                    *
  *                                                                           *
  ****************************************************************************/
  public static void main(String[] args) {
    double[] vals = {0.000,0.100,0.200,0.300,0.400,0.500,0.600,0.700,0.800,0.900,1.000,1.100,1.200,1.300,1.400,1.500,1.600,1.700,1.800,1.900,2.000,2.100,2.200,2.300,2.400,2.500,3.000,3.500,4.000,4.500,5.000,5.500,6.000,6.500,7.000,7.500,8.000,8.500,9.000,9.500,10.000,10.500,11.000,11.500,12.000,0.000,0.096,0.195,0.293,0.389,0.492,0.590,0.688,0.779,0.877,0.974,1.071,1.165,1.261,1.356,1.449,1.539,1.627,1.710,1.783,1.840,1.878,1.898,1.908,1.913,1.917,1.926,1.933,1.936,1.939,1.943,1.949,1.953,1.957,1.960,1.965,1.970,1.973,1.978,1.983,1.990,2.000,2.003,2.007,2.010,0.000000000,0.000967937,0.001966122,0.002954225,0.003922162,0.004960678,0.005948780,0.006936882,0.007854406,0.008842509,0.009820528,0.010798548,0.011746320,0.012714257,0.013672111,0.014609800,0.015517241,0.016404517,0.017241379,0.017977415,0.018552127,0.018935269,0.019136923,0.019239766,0.019291188,0.019328494,0.019419238,0.019489816,0.019520065,0.019550313,0.019590643,0.019651139,0.019691470,0.019731801,0.019762049,0.019812462,0.019862876,0.019893124,0.019943537,0.019993950,0.020064529,0.020165356,0.020195604,0.020235935,0.020266183};

    //Lambda test = new Lambda(vals);
    //makeSystem.out.println("Lambda: " + test.LAMBDA);
    //System.out.println("Triode?Resistance: " + lam.resist + "<- Didn't ask for this so whatev");
  }
}
