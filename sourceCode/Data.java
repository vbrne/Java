/*******************************************************************************
 *
 * University of Texas Rio Grande Valley
 * Computer Engineering
 * Senior Design
 * Spring/Fall 2020
 * Group 15: Bernie Villalon, Samuel Solis, Leo Marroquin
 *
 * Description:
 *   This class is simply an object that will convert the Voltage-Drop array
 * into current, as well as hold each data array for the plotting and calculations
 *
 ******************************************************************************/

public class Data {
  public final double[] vds;
  public final double[] vgs;
  public final double[] ids;

  public Data(double[] VDS, double[] VGS, double[] DRP, double omega) {
    vds = VDS;
    vgs = VGS;
    double[] temp = new double[DRP.length];
    for (int i = 0; i < DRP.length; i++) temp[i] = 1000*DRP[i]/omega;  // Ohm's Law: V=I*R => I=V/R
    ids = temp;
  }

  public static void main(String[] args) {}
}