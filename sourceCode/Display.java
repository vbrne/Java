/*******************************************************************************
 *
 * University of Texas Rio Grande Valley
 * Computer Engineering
 * Senior Design
 * Spring/Fall 2020
 * Group 15: Bernie Villalon, Samuel Solis, Leo Marroquin
 *
 * Description:
 *   This class is responsible for creating graphs for displaying the data
 * received from the tests. This is accomplished using an external Java
 * Library called XChart, which could be found at:
 *                                       https://knowm.org/open-source/xchart/
 *
 ******************************************************************************/

import java.util.List;
import java.util.ArrayList;

import javax.swing.JPanel;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

public class Display {
  private List<XYChart> charts = new ArrayList<XYChart>();  //List of charts for future matrix display
  XYChart Threshold_Chart, Lambda_Chart;

  /**
   * Universal Widths/Heights
  **/
  private int width = 1000;
  private int height = 500;

  /**
   * Helper functions to calculate Domains/Ranges for Graph.
  **/
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
    for (int i = 0; i < x.length; i++)              //Calculates y value for each x in array
      tmp[i] = slope * x[i] + intercept;
    return tmp;
  }

  /**
   * Converts Threshold Sweep value into Chart form.
  **/
  public void addThresholdChart(Threshold Th) {
    XYChart tmpch = new XYChartBuilder().width(width).height(height).title("Threshold").xAxisTitle("Voltage (V)").yAxisTitle("Current (sqrt(mA))").build();
    tmpch.addSeries("Root of Current vs Voltage", Th.dispX, Th.dispY);

    double[] XLF = new double[] {Th.THRESHOLD, Th.V_GS_eq[Th.V_GS_eq.length - 1]};
    double[] YLF = yRange(XLF, Th.INTERCEPT, Th.SLOPE);
    tmpch.addSeries("\'Linear Fit\'", XLF, YLF);

    Threshold_Chart = tmpch;

    charts.add(tmpch);
  }

  /**
   * Converts Lambda Sweep values into Chart form.
  **/
  public void addLambdaChart(Lambda La) {
    XYChart tmpch = new XYChartBuilder().width(width).height(height).title("Lambda").xAxisTitle("Voltage (V)").yAxisTitle("Current (mA)").build();
    tmpch.addSeries("Current vs Voltage", La.VDS, La.IDS);

    double[] XLF = new double[] {La.sat_V_DS[0], La.sat_V_DS[La.sat_V_DS.length - 1]};
    double[] YLF = yRange(XLF, La.INTERCEPT, La.SLOPE);
    tmpch.addSeries("\'Linear Fit\'", XLF, YLF);

    Lambda_Chart = tmpch;

    charts.add(tmpch);
  }

  /**
   * Displays "F" in chart form.
  **/
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

  /**
   * Not in use
  **/
  public void showAllCharts() {
    new SwingWrapper<XYChart>(charts).displayChartMatrix(); //Displays all Charts next to one another
  }

  /**
   * Functions to get JPanels for GUI
  **/
  @SuppressWarnings("unchecked")  // XChartPanel extends JPanel
  public JPanel getThreshPanel() {
    return new XChartPanel(Threshold_Chart);
  }
  @SuppressWarnings("unchecked")  // XChartPanel extends JPanel
  public JPanel getLambPanel() {
    return new XChartPanel(Lambda_Chart);
  }

  /**
   * "Main" function; not in use
  **/
  public static void main(String args[]) {}
}
