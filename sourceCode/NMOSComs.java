/*******************************************************************************
 *
 * University of Texas Rio Grande Valley
 * Computer Engineering
 * Senior Design
 * Spring/Fall 2020
 * Group 15: Bernie VIllalon, Samuel Solis, Leo Marroquin
 *
 * Description:
 *   This Class is responsible for communicating with C++ program that reads
 *  and writes to the USB-Module. 
 *
 ******************************************************************************/

import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Scanner;

public class NMOSComs {
  public final int DACResolution = (int)Math.pow(2, 12);  // 12-bits
  public final int ADCResolution = (int)Math.pow(2, 14);  // 14-bits
  public final double DACMaxVoltage = 2.048;
  public final double resistor = 100;

  Data ThresholdSweep;
  Data LambdaSweep;

  File flg = new File("h7f5k68k.dat");
  File vds = new File("bk851d39.dat");
  File vgs = new File("y6u4w0r7.dat");
  File vth = new File("eltonj.dat");
  File drp = new File("drp.dat");
  File mod = new File("irmdm18.dat");

  public NMOSComs() {
    setFile(flg);
    setFile(vds);
    setFile(vgs);
    setFile(vth);
    setFile(drp);
    setFile(mod);
  }

  public void startThresholdSweep() {
    try {
      FileWriter tmp = new FileWriter(flg.getName());
      tmp.write("true");
      tmp.close();

      waitForFiles();

      ThresholdSweep = readFiles();
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  public void startLambdaSweep(double VTH) {
    try {
      FileWriter tmp = new FileWriter(flg.getName());
      tmp.write("true");
      tmp.close();

      FileWriter tmp2 = new FileWriter(vth.getName());
      tmp2.write(getDec(VTH));
      tmp2.close();

      waitForFiles();

      LambdaSweep = readFiles();
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

  private void setFile(File f) {
    try {
      if (f.createNewFile()) System.out.println("File created: " + f.getName());
      FileWriter tmp = new FileWriter(f.getName());
      tmp.write("false");
      tmp.close();
      System.out.println("Successfully set " + f.getName());
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  private void waitForFiles() {
    while(true) {
      Scanner flag = new Scanner (flg);
      String strFlag = flag.nextLine();
      flag.close();
      
      if (strFlag == "false") break;              // Check if files were updated

      try {                                       // Delay
        Thread.sleep(250);
      } catch (InterruptedException ie) {
        Thread.currentThread().interrupt();
      }
    }
  }

  private Data readFiles() {
    ArrayList VDS = new ArrayList();
    ArrayList VGS = new ArrayList();
    ArrayList DRP = new ArrayList();
    //Data sweep = new Data(resistor);
    return null;  // temporary
  }

  private double[] getArray(File f) {
    ArrayList L = new ArrayList();

    return null;  // temporary
  }

  private int getDec(double volt) { // Converts Analog Voltage to Digital Voltage
    return (int)Math.ceil(volt * resolution / maxVoltage / 6); // Formula to convert from analog to digital
  }

  private double getDub(int dec) { // Converts Digital Voltage to Analog Voltage
    return (dec / resolution * maxVoltage * 6);
  }

  public static void main(String[] args) {
    NMOSComs n = new NMOSComs();
    System.out.println(n.getDec(7.578));
    System.out.println(n.getDub(2526));
  }
}