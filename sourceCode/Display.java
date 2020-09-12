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

import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.SwingWrapper;
import java.util.List;
import java.util.ArrayList;

public class Display {
  private List<XYChart> charts = new ArrayList<XYChart>();  //List of charts for future matrix display
  private int width = 1000;
  private int height = 500;
/******************************************************************************/
/******************************************************************************/
  //public Display() {} //I didn't know I could omit this
/******************************************************************************/
/******************************************************************************/
  private double[] xRange(double lBound, double uBound, int increments) { //To get x-series for Linear Fit
    if (increments == 2) return new double[] {lBound, uBound};
    double[] tmp = new double[increments];
    double incVal = (uBound - lBound) / increments;
    for (int i = 0; i < increments; i++)            //Creates increments between lBound and uBound
      tmp[i] = lBound + (i * incVal);
    return tmp;
  }
  private double[] yRange(double[] x, double intercept, double slope) { //To get y-series for Linear Fit
    double[] tmp = new double[x.length];
    for (int i = 0; i < x.length; i++)    //Calculates y value for each x in array
      tmp[i] = slope * x[i] + intercept;
    return tmp;
  }
/******************************************************************************/
/******************************************************************************/
  public void addChart(String nam, double[] x, double[] y, double intercept, double slope, double lBound, double uBound) {
    XYChart tmpch = new XYChartBuilder()
                        .width(width)
                        .height(height)
                        .title(nam)
                        .xAxisTitle("Voltage (V)")
                        .yAxisTitle("Current (mA)")
                        .build();                     //Creates new Chart

    tmpch.addSeries("Current vs Voltage", x, y);      //Adds data values to said chart

    double[] xData = xRange(lBound, uBound, 2);       //Creates x-series
    double[] yData = yRange(xData, intercept, slope); //Creates y-series
    tmpch.addSeries("\'Linear Fit\'", xData, yData);  //Adds Series to said chart

    charts.add(tmpch);                                //Adds chart to chart array
  }
/******************************************************************************/
/******************************************************************************/
  public void addChart(String nam, double[] x, double[] y) {  //Mainly used for debugging/developing
    XYChart tmpch = new XYChartBuilder().width(width).height(height).title(nam).xAxisTitle("Voltage (V)").yAxisTitle("Current (mA)").build();
    tmpch.addSeries("Current vs Voltage", x, y);

    charts.add(tmpch);
  }
/******************************************************************************/
/******************************************************************************/
  public void addThresholdChart(Threshold Th) {
    XYChart tmpch = new XYChartBuilder().width(width).height(height).title("Threshold").xAxisTitle("Voltage (V)").yAxisTitle("Current (sqrt(mA))").build();
    tmpch.addSeries("Root of Current vs Voltage", Th.VGS, Th.sqrtIDS);

    double[] XLF = new double[] {Th.THRESHOLD, Th.V_GS_eq[Th.V_GS_eq.length - 1]};
    double[] YLF = yRange(XLF, Th.INTERCEPT, Th.SLOPE);
    tmpch.addSeries("\'Linear Fit\'", XLF, YLF);

    charts.add(tmpch);
  }
/******************************************************************************/
/******************************************************************************/
  public void addLambdaChart(Lambda La) {
    XYChart tmpch = new XYChartBuilder().width(width).height(height).title("Lambda").xAxisTitle("Voltage (V)").yAxisTitle("Current (mA)").build();
    tmpch.addSeries("Current vs Voltage", La.VDS, La.IDS);

    double[] XLF = new double[] {La.sat_V_DS[0], La.sat_V_DS[La.sat_V_DS.length - 1]};
    double[] YLF = yRange(XLF, La.INTERCEPT, La.SLOPE);
    tmpch.addSeries("\'Linear Fit\'", XLF, YLF);

    charts.add(tmpch);
  }
/******************************************************************************/
/******************************************************************************/
  // This is just to display a sad face, like if people try to analyze anything that isn't NMOS
  // Honestly, most of the constants were just Trial-Error 'til I got a face I liked; no thought really
  public void addF() {
    XYChart tmpch = new XYChartBuilder()
                        .width(width)
                        .height(height)
                        .title("F")
                        .build();

    tmpch.addSeries("BOX", new double[] {1, 1, 10, 10, 1}, new double[] {1, 10, 10, 1, 1});
    tmpch.addSeries("F", new double[] {5, 5, 5.5, 5, 5, 6}, new double[] {4, 5.5, 5.5, 5.5, 7, 7});
    charts.add(tmpch);
  }
/******************************************************************************/
/******************************************************************************/
  public void showAllCharts() {
    new SwingWrapper<XYChart>(charts).displayChartMatrix(); //Displays all Charts next to one another
  }

  public static void main(String args[]) {}
}
