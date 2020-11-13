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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Scanner;

public class NMOSComs {
  public final boolean debug = true;

  /**
   * Digital-to-Analog Converter Hardware Specifications:
  **/
  public final int DACResolution = (int)Math.pow(2, 12);  // 12-bits
  public final double DACMaxVoltage = 2.048;

  /**
   * Analog-to-Digital Converter Hardware Specifications:
  **/
  public final int ADCResolution = (int)Math.pow(2, 14);  // 14-bits
  public final double ADCMaxVoltage = 12.126;

  public final double dropResistor = 100;

  Data ThresholdSweep;
  Data LambdaSweep;

  /**
   * File flg is responsible fo setting flag to denote which app is working.
   * "false" denotes Java app is working, where "true" denotes c++ is working
  **/
  File flg = new File("h7f5k68k.dat");

  /**
   * Files vds, vgs, and drp are responsible for getting VDS, VGS, and Drop 
   * readings from Microcontroller to java app respectively.
  **/
  File vds = new File("bk851d39.dat");
  File vgs = new File("y6u4w0r7.dat");
  File drp = new File("drp.dat");

  /**
   * File vth is responsible for getting VTH calculation from Java application
   * to Microcontroller. 
  **/
  File vth = new File("eltonj.dat");

  /**
   * File mod is responsible for denoting the "mode", whatever that means o.O
  **/
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
      tmp2.write(toDigital(VTH));
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
      try {
        Scanner flag = new Scanner (flg);
        String strFlag = flag.nextLine();
        flag.close();
        
        if (strFlag == "false") break;              // Check if files were updated

      } catch (IOException e) {
        System.err.println(e);
      }

      // Delay
      try {
        Thread.sleep(250);
      } catch (InterruptedException ie) {
        Thread.currentThread().interrupt();
      }
    }
  }

  private Data readFiles() {
    double[] VDS = getArray(vds);
    double[] VGS = getArray(vgs);
    double[] DRP = getArray(drp);

    Data sweep = new Data(VDS, VGS, DRP, dropResistor);
    return null;  // temporary
  }

  private double[] getArray(File f) {
    ArrayList<Double> L = new ArrayList<>();
    String fileName = f.getName();

    try {
      BufferedReader lineReader = new BufferedReader(new FileReader(fileName));
      String line = null;

      while ((line = lineReader.readLine()) != null) {
        if (debug) System.out.println("StringRead: " + line);

        int digitalValue = Integer.parseInt(line);
        if (debug) System.out.println("DigitalRead: " + digitalValue);

        double analogValue = toAnalog(digitalValue);
        if (debug) System.out.println("AnalogConv.: " + analogValue);

        L.add(analogValue);
      }
    } catch (IOException e) {
      System.err.println(e);
    }

    return toDoubleArray(L);  // temporary
  }

  private double[] toDoubleArray(ArrayList<Double> l) {
    double[] tmp = new double[l.size()];
    for (int i = 0; i < l.size(); i++) tmp[i] = l.get(i);
    return tmp;
  }

  private int toDigital(double ana) { // Converts Analog Voltage to Digital Voltage
    return (int)Math.ceil(ana * DACResolution / DACMaxVoltage / 6); // Formula to convert from analog to digital
  }

  // Possibly not functional ;-; (yet)
  private double toAnalog(int dig) { // Converts Digital Voltage to Analog Voltage
    return (dig / ADCResolution * ADCMaxVoltage);
  }

  public static void main(String[] args) {
    //NMOSComs n = new NMOSComs();
    //System.out.println(n.toDigital(7.578));
    //System.out.println(n.toAnalog(2526));
  }
}