/********************************************************************************
*                                                                               *
*     This Class is used to fit a linear equation onto plot values, which       *
*   is essential to for all our calculations. A small portion of code found     *
*   in the following link was used to create this class:                        *
*     https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/LinearRegression.java.html
*                                                                               *
********************************************************************************/
//package lib;

public class LinearFit {
  public final double intercept, slope;
  public LinearFit(double[] x, double[] y) {
    if (x.length != y.length)
      throw new IllegalArgumentException("Array lengths are not equal");
    int n = x.length;

    double s_x = 0, s_y = 0;
    for (int i = 0; i < n; i++) {
      s_x += x[i];
      s_y += y[i];
    }
    double a_x = s_x / n;
    double a_y = s_y / n;

    double a_xx = 0, a_xy = 0;
    for (int i = 0; i < n; i++) {
      a_xx += (x[i] - a_x) * (x[i] - a_x);
      a_xy += (x[i] - a_x) * (y[i] - a_y);
    }

    slope = a_xy / a_xx;
    intercept = a_y - slope * a_x;
  }

  public double intercept() { return intercept; }
  public double slope() { return slope; }

  // private void display() { /* to be written */ }
}
