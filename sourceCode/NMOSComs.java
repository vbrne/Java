/*******************************************************************************
 *
 * University of Texas Rio Grande Valley
 * Computer Engineering
 * Senior Design
 * Spring/Fall 2020
 * Group 15: Bernie Villalon, Samuel Solis, Leo Marroquin
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
   * Digital-to-Analog Converter (DAC) Hardware Specifications:
  **/
  public final int DACResolution = (int)Math.pow(2, 12);  // 12-bits
  public final double DACMaxVoltage = 2.048;
  /**
   * Analog-to-Digital Converter (ADC) Hardware Specifications:
  **/
  public final int ADCResolution = (int)Math.pow(2, 14);  // 14-bits
  public final double ADCMaxVoltage = 12.126;
  /**
   * DAC and ADC have different Resolutions and Reference voltages, hence
   * the different specifications.
  **/

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

  /**
   * Initiator for NMOSComs; runs communications using file read/write to communicate
   * with back-end C++ program.
  **/
  public NMOSComs() {
    setFile(flg);     // 'Sets' all files :: either creates or resets files.
    setFile(vds);     // ""
    setFile(vgs);     // ""
    setFile(vth);     // ""
    setFile(drp);     // ""
    setFile(mod);     // ""
  }

  /**
   * Initiates Threshold Sweep (Sweep 1) and reads/returns data from back-end
  **/
  // Still in Progress...
  public void startThresholdSweep() {
    try {
      FileWriter tmp = new FileWriter(flg.getName());
      tmp.write("true");                // Signals C++ program to start by setting flg "true"
      tmp.close();                      // Closes flg

      waitForFiles();                   // Waits for C++ signal that it's finished

      ThresholdSweep = readFiles();     // Reads the data from vds,vgs,drp and converts to Data object
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  /**
   * Initiates Lambda Sweep (Sweep 2) and reads/returns data from back-end
   * Takes Threshold value calculated from Threshold Sweep as input.
  **/
  // Still in Progress...
  public void startLambdaSweep(double VTH) {
    try {
      FileWriter tmp2 = new FileWriter(vth.getName());
      tmp2.write(toDigital(VTH));             // First writes calculated Threshold value to vth
      tmp2.close();                           // Closes vth

      FileWriter tmp = new FileWriter(flg.getName());
      tmp.write("true");                      // Signals C++ program to start by setting flg "true"
      tmp.close();                            // Closes flg


      waitForFiles();                         // Waits for C++ signal that it's finished

      LambdaSweep = readFiles();              // Reads data from vds, vgs, drp and converts to Data object
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

  /**
   * Helper function to setFiles
  **/
  private void setFile(File f) {
    try {
      if (f.createNewFile()) System.out.println("File created: " + f.getName()); // Creates file if it doesn't exist
      FileWriter tmp = new FileWriter(f.getName());
      tmp.write("false");         // Writes "false" to file
      tmp.close();                // Closes file
      System.out.println("Successfully set " + f.getName());
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  /**
   * Helper function to wait for files
  **/
  private void waitForFiles() {
    while(true) {
      try {
        Scanner flag = new Scanner (flg);
        String strFlag = flag.nextLine(); // Reads flg file
        flag.close();
        
        if (strFlag == "false") break;    // Checks if files were updated

      } catch (IOException e) {
        System.err.println(e);
      }

      // Delay
      try {
        Thread.sleep(250);                // Delays by 250 milliseconds
      } catch (InterruptedException ie) {
        Thread.currentThread().interrupt();
      }
    }
  }

  /**
   * Helper function to read all vds,vgs,drp files and stransfer to Data object
   * Returns said Data object
  **/
  private Data readFiles() {
    double[] VDS = getArray(vds); 
    double[] VGS = getArray(vgs);
    double[] DRP = getArray(drp);

    Data sweep = new Data(VDS, VGS, DRP, dropResistor);
    return sweep;
  }

  /**
   * Helper function to read array file individually
   * Returns double[] array version of the file
  **/
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

  /**
   * Helper function that converts ArrayList<Double> to double[] Array
   * the function '.toArray()' wasn't working so this function was made...
  **/
  private double[] toDoubleArray(ArrayList<Double> l) {
    double[] tmp = new double[l.size()];
    for (int i = 0; i < l.size(); i++) tmp[i] = l.get(i);
    return tmp;
  }

  /**
   * Converts Analog (in double) representation of a voltage to a Digital (in int) representation.
   *  ~ Formula:
   *                                     DACResolution    |  *Hardware will scale-up voltage output
   *  DigitalVoltage = AnalogVoltage * -----------------  |  by 6, hence we need to scale-down our
   *                                   DACMaxVoltage * 6  |  calculation to get the desired output.
  **/
  private int toDigital(double ana) {
    return (int)Math.ceil(ana * DACResolution / DACMaxVoltage / 6);
  }

  /**
   * Converts Digital (in int) representation of a voltage to a Analog (in double) representation.
   *  ~ Formula:
   *                                   ADCMaxVoltage
   *  AnalogVoltage = DigitalVoltage * -------------
   *                                   ADCResolution
  **/
  // Possibly not functional ;-; (yet)
  private double toAnalog(int dig) {
    return (dig / ADCResolution * ADCMaxVoltage);
  }

  /**
   * Main function for testing this specific class
  **/
  public static void main(String[] args) {
  }
}