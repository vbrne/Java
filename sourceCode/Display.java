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

  public void addChart(String nam, double[] x, double[] y, double intercept, double slope, double lBound, double uBound) {
    XYChart tmpch = new XYChartBuilder()
                        .width(width)
                        .height(height)
                        .title(nam)
                        .xAxisTitle("Voltage (V)")
                        .yAxisTitle("Current (mA)")
                        .build();                     //Creates new Chart

    tmpch.addSeries("Current vs Voltage", x, y);      //Adds data values to said chart

               //Calculates Upper Bound for Linear Fit
    System.out.println("uBound: " + uBound + "\nlBound: " + lBound);
    double[] xData = xRange(lBound, uBound, 2);      //Creates x-series
    double[] yData = yRange(xData, intercept, slope); //Creates y-series
    tmpch.addSeries("\'Linear Fit\'", xData, yData);  //Adds Series to said chart
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

  public static void main(String args[]) {}
}
