/*******************************************************************************
 *
 * University of Texas Rio Grande Valley
 * Computer Engineering
 * Senior Design
 * Spring/Fall 2020
 * Group 15: Bernie VIllalon, Samuel Solis, Leo Marroquin
 *
 * Description:
 *   This class is responsible for creating graphs for displaying the data
 * received from the tests. This is accomplished using an external Java
 * Library called XChart, which could be found at:
 *                                       https://knowm.org/open-source/xchart/
 *
 ******************************************************************************/

//package lib;

import org.knowm.xchart.*; //.jar file wasn't working or something, so extracted org folder and it worked
import java.util.*;

public class Display {
  private List<XYChart> charts = new ArrayList<XYChart>();  //List of charts for future matrix display
  private int width = 1000;
  private int height = 500;

  public Display() {}
//  public void close() { charts = new ArrayList<XYChart>; }

  private double[] xRange(double lBound, double uBound, int increments) { //To get x-series for Linear Fit
    double[] tmp = new double[increments];
    double incVal = (lBound - uBound) / increments;
    for (int i = 0; i < increments; i++)            //Creates increments between lBound and uBound
      tmp[i] = lBound + (i * incVal);
    return tmp;
  }

  private double[] yRange(double[] x, double intercept, double slope) { //To get y-series for Linear Fit
    double[] tmp = new double[x.length];
    for (int i = 0; i < x.length; i++)    //Calculates y value for each x in array
      tmp[i] = -1 * slope * x[i] + intercept;
    return tmp;
  }

  public void addChart(String nam, double[] x, double[] y, double intercept, double slope) {
    XYChart tmpch = new XYChartBuilder()
                        .width(width)
                        .height(height)
                        .title(nam)
                        .xAxisTitle("Voltage (V)")
                        .yAxisTitle("Current (mA)")
                        .build();                     //Creates new Chart

    tmpch.addSeries("Current vs Voltage", x, y);      //Adds data values to said chart

    double lBound = intercept * -1 / slope;           //Calculates Lower Bound for Linear Fit
    double uBound = x[x.length - 1];                  //Calculates Upper Bound for Linear Fit
    double[] xData = xRange(lBound, uBound, 20);      //Creates x-series
    double[] yData = yRange(xData, intercept, slope); //Creates y-series
    //tmpch.addSeries("\'Linear Fit\'", xData, yData);  //Adds Series to said chart
    //^^Isn't working well yet ;-;
    //THOUGHTS: Get lBound from threshold / usable I_eq from Lambda :thinking_emoji:

    charts.add(tmpch);                                //Adds chart to chart array
  }

  public void addChart(String nam, double[] x, double[] y) {  //Mainly used for debugging/developing
    XYChart tmpch = new XYChartBuilder()
                        .width(width)
                        .height(height)
                        .title(nam)
                        .xAxisTitle("Voltage (V)")
                        .yAxisTitle("Current (mA)")
                        .build();

    //tmpch.getStyler().setLegendPosition(LegendPosition.InsideSE); //Didn't work
    tmpch.addSeries("Current vs Voltage", x, y);

    charts.add(tmpch);
  }

  public List<XYChart> returnCharts() { return charts; }    //I'm trying to embed charts in gui, so this
  public XYChart returnAt(int i) { return charts.get(i); }  //  is supposed to help if possible

  public void showAllCharts() {
    new SwingWrapper<XYChart>(charts).displayChartMatrix();
  } //Displays all Charts next to one another

  public static void main(String args[]) {
    Display dis = new Display();

    //double[] xData = new double[] {0.0, 1.0, 2.0};
    //double[] yData = new double[] {2.0, 1.0, 0.0};
    double [] values = {0.0000,0.5000,1.0000,1.5000,2.0000,2.5020,3.0000,3.5000,4.0000,4.5000,5.0000,5.5000,6.0000,6.5000,7.0000,7.5000,8.0000,8.5000,9.0000,9.5000,10.0000,0.0000,0.0000,0.0000,0.0000,0.0000,0.0003,0.0328,0.2755,0.6489,1.0648,1.5206,1.9683,2.4287,2.8887,3.3562,3.8261,4.3054,4.7690,5.2460,5.7210,6.2060,0,0,0,0,0,0.002556237,0.335685072,2.816871166,6.634969325,10.88752556,15.54805726,20.12576687,24.83333333,29.53680982,34.31697342,39.12167689,44.02249489,48.76278119,53.6400818,58.49693252,63.45603272};

    Threshold thresh = new Threshold(values);
    dis.addChart("Threshold", thresh.VGS(), thresh.IDS(), thresh.intercept(), thresh.slope());

    double[] values2 = {0.000,0.100,0.200,0.300,0.400,0.500,0.600,0.700,0.800,0.900,1.000,1.100,1.200,1.300,1.400,1.500,1.600,1.700,1.800,1.900,2.000,2.100,2.200,2.300,2.400,2.500,3.000,3.500,4.000,4.500,5.000,5.500,6.000,6.500,7.000,7.500,8.000,8.500,9.000,9.500,10.000,10.500,11.000,11.500,12.000,0.000,0.096,0.195,0.293,0.389,0.492,0.590,0.688,0.779,0.877,0.974,1.071,1.165,1.261,1.356,1.449,1.539,1.627,1.710,1.783,1.840,1.878,1.898,1.908,1.913,1.917,1.926,1.933,1.936,1.939,1.943,1.949,1.953,1.957,1.960,1.965,1.970,1.973,1.978,1.983,1.990,2.000,2.003,2.007,2.010,0.000000000,0.000967937,0.001966122,0.002954225,0.003922162,0.004960678,0.005948780,0.006936882,0.007854406,0.008842509,0.009820528,0.010798548,0.011746320,0.012714257,0.013672111,0.014609800,0.015517241,0.016404517,0.017241379,0.017977415,0.018552127,0.018935269,0.019136923,0.019239766,0.019291188,0.019328494,0.019419238,0.019489816,0.019520065,0.019550313,0.019590643,0.019651139,0.019691470,0.019731801,0.019762049,0.019812462,0.019862876,0.019893124,0.019943537,0.019993950,0.020064529,0.020165356,0.020195604,0.020235935,0.020266183};

    double[] newVals = new double[values2.length];  //Converts values2 to milliamps instead of just amps
    for (int i = 0; i < values2.length; i++)
      newVals[i] = values2[i] * 1000;

    Lambda lam = new Lambda(newVals);
    dis.addChart("Lambda", lam.VDS(), lam.IDS(), lam.intercept(), lam.slope());
    //System.out.println(lam.lambda());

    dis.showAllCharts();
    //XYChart chart = QuickChart.getChart("Sample Chart", "X", "Y", "y(x)", thresh.VGS(), thresh.IDS());

    //new SwingWrapper(chart).displayChart();
  }
}
