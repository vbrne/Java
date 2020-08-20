
//package lib;
import org.knowm.xchart.*; //.jar file wasn't working or something, so extracted org folder and it worked

public class Display {
  public Display(double[] x, double[] y) {
    XYChart chart = QuickChart.getChart("Test Chart", "X", "Y", "y(x)", x, y);
    new SwingWrapper(chart).displayChart();
  }

  public static void main(String args[]) {
    //double[] xData = new double[] {0.0, 1.0, 2.0};
    //double[] yData = new double[] {2.0, 1.0, 0.0};
    double [] values = {0.0000,0.5000,1.0000,1.5000,2.0000,2.5020,3.0000,3.5000,4.0000,4.5000,5.0000,5.5000,6.0000,6.5000,7.0000,7.5000,8.0000,8.5000,9.0000,9.5000,10.0000,0.0000,0.0000,0.0000,0.0000,0.0000,0.0003,0.0328,0.2755,0.6489,1.0648,1.5206,1.9683,2.4287,2.8887,3.3562,3.8261,4.3054,4.7690,5.2460,5.7210,6.2060,0,0,0,0,0,0.002556237,0.335685072,2.816871166,6.634969325,10.88752556,15.54805726,20.12576687,24.83333333,29.53680982,34.31697342,39.12167689,44.02249489,48.76278119,53.6400818,58.49693252,63.45603272};

    Threshold thresh = new Threshold(values);

    Display chart = new Display(thresh.VGS(), thresh.IDS());
    //XYChart chart = QuickChart.getChart("Sample Chart", "X", "Y", "y(x)", thresh.VGS(), thresh.IDS());

    //new SwingWrapper(chart).displayChart();
  }
}
