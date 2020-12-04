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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Scanner;

import java.lang.Math;                  //For Testing

public class NMOSComs {
  public final boolean debug = true;

  /**
   * Digital-to-Analog Converter (DAC) Hardware Specifications:
  **/
  public final int DACResolution = (int)Math.pow(2, 12);  // 12-bits
  public final double DACMaxVoltage = 2.048; // Volts
  /**
   * Analog-to-Digital Converter (ADC) Hardware Specifications:
   * Update: We are no longer using an ADC ;-;
  **/
  public final int ADCResolution = (int)Math.pow(2, 10);  // 10-bits
  public final double ADCMaxVoltage = 5.120; // Volts
  /**
   * Voltage divider resistors used to scale-down input voltages, labeled
   * R1 and R2. There are two seperate deviders, the vgsR1 = 1.993kOhms
   * and vgsR2 = 0.510kOhms, and vdsR1 = 2.093kOhms and vdsR2 = 0.514kOhms.
   * For simplicity, we average the values to only use two values.
   * 
   * Divider Scaling: 
  **/
  public final double divR1 = 2.043; // kOhms
  public final double divR2 = 0.513; // kOhms
  public final double divSc = (divR1 + divR2)/divR2;
  /**
   * Drop resistor used to calculate current
  **/
  public final double dropResistor = 100;

  public Data ThresholdSweep;
  public Data LambdaSweep;

  /**
   * File flg is responsible fo setting flag to denote which app is working.
   * "false" denotes Java app is working, where "true" denotes c++ is working
  **/
  File flg = new File("h7f5k68k.dat");

  /**toAnalog
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
  File mod = new File("pnkftwd1t6.dat");

  /**
   * File por is responisble for setting which COM port the user chooses.
  **/
  File por = new File("irnmdn18.dat");

  /**
   * Initiator for NMOSComs; runs communications using file read/write to communicate
   * with back-end C++ program.
  **/
  public NMOSComs(String Port) {
    setFile(flg);     // 'Sets' all files :: either creates or resets files.
    setFile(vds);     // ""
    setFile(vgs);     // ""
    setFile(vth);     // ""
    setFile(drp);     // ""
    setFile(mod);     // ""
    writeToFile(por, Port);
    launchCpp();
  }

  /**
   * Initiator for Simulation with previous data
  **/
  public NMOSComs(double[] ThreshVals) {
    ThresholdSweep = convertToData(ThreshVals);
  }

  /**
   * Initiates Threshold Sweep (Sweep 1) and reads/returns data from back-end
  **/
  // Still in Progress...
  public void startThresholdSweep() {
    //Somewhere here we set the "mode"

    writeToFile(flg, "true");

    waitForFiles();                   // Waits for C++ signal that it's finished

    ThresholdSweep = readFiles();     // Reads the data from vds,vgs,drp and converts to Data object
    if (debug) printData(ThresholdSweep);
  }

  /**
   * Initiates Lambda Sweep (Sweep 2) and reads/returns data from back-end
   * Takes Threshold value calculated from Threshold Sweep as input.
  **/
  // Still in Progress...
  public void startLambdaSweep(double VTH) {
    //Somewhere here we set the "mode"
    
    writeToFile(vth, String.valueOf(toDigital(VTH)));

    writeToFile(flg, "true");

    waitForFiles();                         // Waits for C++ signal that it's finished

    LambdaSweep = readFiles();              // Reads data from vds, vgs, drp and converts to Data object
  }

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// These should all be private but may currently be public for testing

  /**
   * Helper function to write a single line
  **/
  private void writeToFile(File f, String s) {
    try {
      if (f.createNewFile()) System.out.println("File created: " + f.getName());
      FileWriter tmp = new FileWriter(f.getName());
      tmp.write(s);
      tmp.close();
      System.out.println("Successfully set " + f.getName());
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

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
   * Helper function to launch Cpp executable
   * Uses: https://www.infoworld.com/article/2071275/when-runtime-exec---won-t.html?page=2
   * as a guide.
  **/
  private void launchCpp() {
    try {
      //String cwd = System.getProperty("user.dir");  // In case we need User Directory for some reason
      //System.out.println(cwd);
      Runtime rt = Runtime.getRuntime();
      Process proc = rt.exec("./test");
      //BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
      //String line = "";
      //while ((line = reader.readLine()) != null) System.out.println(line);

      int exitVal = proc.waitFor();
      System.out.println("Process exitValue: " + exitVal);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Helper function to wait for files
  **/
  private void waitForFiles() {
    boolean whileFlag = true;
    while(whileFlag) {
      String strFlag = "";
      try {
        Scanner flag = new Scanner (flg);
        strFlag = (String) flag.nextLine(); // Reads flg file
        flag.close();
        
      } catch (IOException e) {
        System.err.println(e);
      }

      if (debug) print(strFlag);
      if (strFlag.equals("false")) whileFlag = false;    // Checks if files were updated

      // Delay
      try {
        Thread.sleep(250);                // Delays by 250 milliseconds
        if (debug) System.out.println("Waiting...");
      } catch (InterruptedException ie) {
        Thread.currentThread().interrupt();
      }
    }
    if (debug) System.out.println("Done Waiting!");
  }

  /**
   * Helper function to read all vds,vgs,drp files and stransfer to Data object
   * Returns said Data object
  **/
  public Data readFiles() {
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
  public double[] getArray(File f) {
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
   * the function '.toArraSystem.out.println("Given: " + rand + " returns: " + ans);y()' wasn't working so this function was made...
  **/
  public double[] toDoubleArray(ArrayList<Double> l) {
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
  public int toDigital(double ana) {
    return (int)Math.ceil(ana * DACResolution / DACMaxVoltage / 6);
  }

  /**
   * Converts Digital (in int) representation of a voltage to a Analog (in double) representation.
   *  ~ Formula:
   *                                   ADCMaxVoltage
   *  AnalogVoltage = DigitalVoltage * -------------
   *                                   ADCResolution
  **/
  public double toAnalog(int dig) {

    return (dig * ADCMaxVoltage / ADCResolution)*divSc; 
    // Important that Mult is first because Div first results in 0.0 ;-;
  }

  /**
   * I just didn't like typing "System.out.println()" all the time...
  **/
  private void print(String s) { System.out.println(s);}

  /**
   * Helper to print array for debugging
  **/
  private void printData(Data dat) {
    if (!debug) return;               // To prevent un-needed printing

    System.out.println("VDS Values: ");
    printArray(dat.vds);
    System.out.println("\nVGS Values: ");
    printArray(dat.vgs);
    System.out.println("\nIDS Values: ");
    printArray(dat.ids);
  }

  /**
   * Helper for Helper to print arrays
  **/
  private void printArray(double[] arr) {
    for (int i = 0; i < arr.length; i++) System.out.println(" " + arr[i]);
  }

  /**
   * Helper to convert old data to Data object
   * -Same function as in Threshold.java
  **/
  private Data convertToData(double[] vals) {
    double[] V_G = new double[21], V_S = new double[21], I = new double[21];  // Divides values do each pin
    for (int i = 0; i < vals.length; i++) {
      if (i < 21)
        V_G[i] = vals[i];     // The first 21 values are the Voltages at the Gate pin
      else if (i < 42)
        V_S[i-21] = vals[i];  // The second 21 values are the Voltages at the Source pin
      else
        I[i-42] = vals[i];    // The final 21 values is the Current flowing through the load resistor
    }
    double[] V_GS = subtract(V_G, V_S);       // Makes difference in V_GS array
    Data tmp = new Data(new double[21], V_GS, multArray(I, 100), 100);
    return tmp;
  }
  public double[] multArray(double[] arr, int s) {
    double[] tmp = new double[arr.length];
    for (int i = 0; i < arr.length; i++) tmp[i] = arr[i]*s;
    return tmp;
  }
  public static double[] subtract(double[] arr1, double[] arr2) {
    double[] ans = new double[arr1.length];
    if (arr1.length != arr2.length)
      throw new IllegalArgumentException("Array lengths are not equal");
    else {
      for (int i = 0; i < arr1.length; i++)
        ans[i] = arr1[i] - arr2[i];
      return ans;
    }
  }
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// Testing for the class:

  /**
   * Main function for testing this specific class
  **/
  public static void main(String[] args) {
    NMOSComs t = new NMOSComs("COM10");
    t.startLambdaSweep(3.2);
    //t.startThresholdSweep();
    //t.testToDigital(10);
    //t.testToAnalog(10);
    //t.testToDoubleArray();
    //t.testPrintData();
    //t.testGetArray();
  }

  public void testGetArray() {  // Questionable; don't know what values HW should output yet
    File t = new File("test.dat");
    printArray(getArray(t));
  }
  public void testToDoubleArray() {
    ArrayList<Double> list = new ArrayList<>();
    for (int i = 0; i < 10; i++) list.add(sigFigs(Math.random()*12, 3));
    double[] ans = toDoubleArray(list);
    printArray(ans);
  }
  public void testToDigital(int t) {  // Seems good
    for (int i = 0; i < t; i++) {
      double rand = sigFigs(Math.random()*12, 3);
      int ans = toDigital(rand);
      System.out.println("Given: " + rand + " returns: " + ans);
    }
  }
  public void testToAnalog(int t) {   // Seems good? Value shouldnt go above 2.4V (480)
    for (int i = 0; i < t; i++) {
      int rand = (int) Math.round(Math.random()*500);
      double ans = sigFigs(toAnalog(rand), 3);
      System.out.println("Given: " + rand + " returns: " + ans);
    }
  }
  public void testPrintData() {       // Seems good
    double[] randVDS = getRandArray(20);
    double[] randVGS = getRandArray(20);
    double[] randDRP = getRandArray(20);
    Data tmp = new Data(randVDS, randVGS, randDRP, 100);
    printData(tmp);
  }
  public double[] getRandArray(int size) {
    double[] tmp = new double[size];
    for (int i = 0; i < size; i++) tmp[i] = sigFigs(Math.random()*12, 3);
    return tmp;
  }
  public double sigFigs(double num, int fig) {
    double tens = Math.pow(10, fig);
    return Math.round(Math.ceil(num*tens))/tens;
  }
}