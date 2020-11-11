
public class Data {
  public final double[] vds;
  public final double[] vgs;
  public final double[] ids;

  public Data(double[] VDS, double[] VGS, double[] DRP, double omega) {
    vds = VDS;
    vgs = VGS;
    double[] temp = new double[DRP.length];
    for (int i = 0; i < DRP.length; i++) temp[i] = DRP[i]/omega;
    ids = temp;
  }

  public static void main(String[] args) {
    
  }
}