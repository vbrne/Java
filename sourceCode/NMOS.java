/*******************************************************************************
 *
 * University of Texas Rio Grande Valley
 * Computer Engineering
 * Senior Design
 * Spring/Fall 2020
 * Group 15: Bernie VIllalon, Samuel Solis, Leo Marroquin
 *
 * Description:
 *   This Class is responsible for directing all the information being used,
 * from communicating with the Hardware (has yet to be written) and
 * giving the other Classes the information needed for their calculations.
 *
 ******************************************************************************/

import java.io.IOException;
import java.lang.Math;      //To get more variety during testing
import javax.swing.JPanel;

public class NMOS {
  Threshold Thresh;
  Lambda Lamb;
  Display Charts;
  double[] dats = new double[4];

  // Displays 'F'... that's it...
  public NMOS(boolean fl) { Display tmp = new Display(); tmp.addF(); tmp.showAllCharts(); }

  public NMOS() {
   /****************************************************************************
    Communication with Device to get Data; returns "values" array, kinda like:

    Commute Coms = new Commute();
    double[] threshList = Coms.threshList();
    ****************************************************************************/
    double[] threshList = getThreshValues();    // Gets data to use in Threshold Class
    Thresh = new Threshold(threshList);         // Runs Threshold Tests
    dats[0] = Thresh.THRESHOLD;                 // Stores Theshold Calculated
    dats[1] = Thresh.KN;                        // Stores kn Calculated
   /****************************************************************************
    Coms.returnThresh(Thresh.threshold());

    double[] lambList = Coms.lambdaList();
    ****************************************************************************/
    double[] lambList = getLambdaValues();    // Gets data to use in Lambda Class
    Lamb = new Lambda(lambList);              // Runs Lambda Tests
    dats[2] = Lamb.LAMBDA;                    // Stores Lambda Calculated

    Charts = new Display();                   // Initializes new Display Class
    Charts.addThresholdChart(Thresh);         // Creates Charts from Threshold Data
    Charts.addLambdaChart(Lamb);              // Creates Charts from Lambda Data
    //Charts.showAllCharts();                   // Displays Charts on Seperate JFrame (big sad;-;)
  }

  public JPanel getThreshPanel(){
    JPanel tmp = Charts.getThreshPanel();
    return tmp;
  }

  public JPanel getLambPanel() {
    JPanel tmp = Charts.getLambPanel();
    return tmp;
  }

  public void export(int fl) throws IOException { // Exporting Function
    if (fl == 0)                              // Exports Only Threshold values to *.csv
      new Export().exportThresh(Thresh);
    if (fl == 1)                              // Exports Only Lambda values to *.csv
      new Export().exportLamb(Lamb);
    if (fl == 2) {                            // Exports All values to *.csv
      new Export().exportThresh(Thresh);
      new Export().exportLamb(Lamb);
    }
  }

  private double[] getThreshValues() {  //Function to get variety of values for demonstration/testing purposes
    double[] v1 = {0.000,0.500,1.000,1.500,2.000,2.500,3.000,3.500,4.000,4.500,5.000,5.500,6.000,6.500,7.000,7.500,8.000,8.500,9.000,9.500,9.992,0.000,0.000,0.000,0.000,0.000,0.000,0.035,0.279,0.658,1.072,1.513,1.960,2.419,2.883,3.350,3.814,4.279,4.747,5.217,5.691,6.156, 0.000,0.000,0.000,0.000,0.000,0.000,0.353,2.813,6.634,10.809,15.255,19.762,24.390,29.068,33.777,38.455,43.144,47.862,52.601,57.381,62.069};
    double[] v2 = {0.000,0.500,1.000,1.500,2.000,2.500,3.000,3.500,4.000,4.500,5.000,5.500,6.000,6.500,7.000,7.500,8.000,8.500,9.000,9.500,9.987,0.030,0.000,0.000,0.000,0.008,0.213,0.600,1.030,1.479,1.937,2.399,2.871,3.342,3.811,4.280,4.748,5.215,5.686,6.153,6.620,7.153,0.302,0.000,0.000,0.000,0.081,2.148,6.050,10.385,14.912,19.530,24.188,28.947,33.696,38.425,43.154,47.873,52.581,57.330,62.039,66.747,55.848};
    double[] v3 = {0.0000,0.5000,1.0000,1.5000,2.0000,2.5020,3.0000,3.5000,4.0000,4.5000,5.0000,5.5000,6.0000,6.5000,7.0000,7.5000,8.0000,8.5000,9.0000,9.5000,10.0000,0.0000,0.0000,0.0000,0.0000,0.0000,0.0003,0.0328,0.2755,0.6489,1.0648,1.5206,1.9683,2.4287,2.8887,3.3562,3.8261,4.3054,4.7690,5.2460,5.7210,6.2060,0,0,0,0,0,0.002556237,0.335685072,2.816871166,6.634969325,10.88752556,15.54805726,20.12576687,24.83333333,29.53680982,34.31697342,39.12167689,44.02249489,48.76278119,53.6400818,58.49693252,63.45603272};
    double[] v4 = {0.001,0.505,1.002,1.506,2.010,2.504,3.000,3.502,4.000,4.500,5.000,5.511,6.004,6.501,7.030,7.509,8.020,8.503,9.000,9.507,9.950,0.000,0.000,0.000,0.000,0.000,0.170,0.541,0.964,1.412,1.862,2.314,2.793,3.253,3.710,4.214,4.661,5.150,5.606,6.070,6.550,6.980,0.000408998,0.000306748,0.000306748,0.000408998,0.000408998,1.738241309,5.531697342,9.856850716,14.43762781,19.03885481,23.6605317,28.55828221,33.26175869,37.93456033,43.08793456,47.65848671,52.65848671,57.32106339,62.06543967,66.97341513,71.37014315};

    double rand = Math.random() * 4;
    if (rand < 1) return v1;
    if (rand < 2) return v2;
    if (rand < 3) return v3;
    return v4;
  }

  private double[] getLambdaValues() {
    return new double[] {0.000,0.100,0.200,0.300,0.400,0.500,0.600,0.700,0.800,0.900,1.000,1.100,1.200,1.300,1.400,1.500,1.600,1.700,1.800,1.900,2.000,2.100,2.200,2.300,2.400,2.500,3.000,3.500,4.000,4.500,5.000,5.500,6.000,6.500,7.000,7.500,8.000,8.500,9.000,9.500,10.000,10.500,11.000,11.500,12.000,0.000,0.096,0.195,0.293,0.389,0.492,0.590,0.688,0.779,0.877,0.974,1.071,1.165,1.261,1.356,1.449,1.539,1.627,1.710,1.783,1.840,1.878,1.898,1.908,1.913,1.917,1.926,1.933,1.936,1.939,1.943,1.949,1.953,1.957,1.960,1.965,1.970,1.973,1.978,1.983,1.990,2.000,2.003,2.007,2.010,0.000000000,0.000967937,0.001966122,0.002954225,0.003922162,0.004960678,0.005948780,0.006936882,0.007854406,0.008842509,0.009820528,0.010798548,0.011746320,0.012714257,0.013672111,0.014609800,0.015517241,0.016404517,0.017241379,0.017977415,0.018552127,0.018935269,0.019136923,0.019239766,0.019291188,0.019328494,0.019419238,0.019489816,0.019520065,0.019550313,0.019590643,0.019651139,0.019691470,0.019731801,0.019762049,0.019812462,0.019862876,0.019893124,0.019943537,0.019993950,0.020064529,0.020165356,0.020195604,0.020235935,0.020266183};
  }
  //public  List<XYChart> returnCharts() { return charts; }   //I'm trying to embed charts in gui, so this
  //public XYChart returnAt(int i) { return charts.get(i); }  //  is supposed to help if possible

  public static void main(String args[]) throws IOException {
    NMOS test = new NMOS();
    test.export(2);
  }
}
