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
  public final int accMarg = 2;

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
  public final double ADCMaxVoltage = 5.180; // Volts
  /**
   * Voltage divider resistors used to scale-down input voltages, labeled
   * R1 and R2. There are two seperate deviders, the vgsR1 = 1.993kOhms
   * and vgsR2 = 0.510kOhms, and vdsR1 = 2.093kOhms and vdsR2 = 0.514kOhms.
   * For simplicity, we average the values to only use two values.
   * 
   * Divider Scaling: 
   *  R2 = 221, 221
   *  R1 = 330, 330
   * 
  **/
  public final double divR1 = 0.330; // kOhms
  public final double divR2 = 0.221; // kOhms
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
   * Boolean Array; for integrity when reading files.
   * If reading seems off, remove all values of said index.
  **/
  Boolean[] check = new Boolean[ADCResolution];

  ArrayList<Double> VDSList; 
  ArrayList<Double> DRPList;
  ArrayList<Double> VGSList;
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
    //launchCpp();
  }

  /**
   * Initiator for Simulation with previous data
  **/
  public NMOSComs(double[] ThreshVals, double[] LambVals) {
    ThresholdSweep = convertToThreshData(ThreshVals);
    //LambdaSweep = convertToLambData(LambVals);
  }

  /**
   * Initiates Threshold Sweep (Sweep 1) and reads/returns data from back-end
  **/
  // Still in Progress...
  public void startThresholdSweep() {
    //Somewhere here we set the "mode"
    writeToFile(mod, "mod1");         // Mode 1 ==  Threshold Sweep

    writeToFile(flg, "true");

    waitForFiles();                   // Waits for C++ signal that it's finished

    ThresholdSweep = readFiles();     // Reads the data from vds,vgs,drp and converts to Data object
    //if (debug) printData(ThresholdSweep);
  }

  /**
   * Initiates Lambda Sweep (Sweep 2) and reads/returns data from back-end
   * Takes Threshold value calculated from Threshold Sweep as input.
  **/
  // Still in Progress...
  public void startLambdaSweep(double VTH) {
    writeToFile(mod, "mod2");               // Mode 2 == Lambda Sweep
    
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
  public void writeToFile(File f, String s) {
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
  public void waitForFiles() {
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
    VDSList = getArray(vds); 
    DRPList = getArray(drp);
    VGSList = getArray(vgs);

    //checkCount();
    //filterArray();

    Data sweep = new Data(toDoubleArray(VDSList), toDoubleArray(VGSList), toDoubleArray(DRPList), dropResistor);
    return sweep;
  }

  /**
   * Helper to the helper; used to check number of anomalies detected.
  **/
  private void filterArray() {
	int size = VGSList.size();
	if (debug) print(Integer.toString(size));
    for (int i = size - 1; i >= 0; i--) {
	  if (debug) System.out.println("ind: " + i + ":" + check[i]);
      if (check[i] != null) {
        //VDSList.remove(i);
		//if (debug) print("VDSRem");
        VGSList.remove(i);
		if (debug) print("VGSRem");
        DRPList.remove(i);
		if (debug) print("DRPRem");
      }
	}
  }

  /**
   * Helper function to read array file individually
   * Returns double[] array version of the file
  **/
  public ArrayList<Double> getArray(File f) {
    ArrayList<Double> L = new ArrayList<>();
    String fileName = f.getName();
    int index = 0;

    try {
      BufferedReader lineReader = new BufferedReader(new FileReader(fileName));
      String line = null;

      boolean first = false;
      int prevValue = 0;
      int dist = 1;
      while ((line = lineReader.readLine()) != null) {
        //if (debug) System.out.println("StringRead: " + line);

         if (first) { 
          int digitalValue = Integer.parseInt(line);
          //if (debug) System.out.println("DigitalRead: " + digitalValue);

          double analogValue = toAnalog(digitalValue);
          //if (debug) System.out.println("AnalogConv.: " + analogValue);

          L.add(analogValue);

          if (fileName == "y6u4w0r7.dat") {
            if (digitalValue >= 600) check[index] = true;
            System.out.println("Index: " + index + "; Margin: " + (digitalValue - prevValue));
            System.out.println("  Prev: " + prevValue + "; Curr: " + digitalValue);
            if ((digitalValue < prevValue) || (digitalValue > prevValue + dist*accMarg)) {
             System.out.println("Error Detected! Dist count: " + (dist + 1));
              check[index] = true;
              dist++;
            } else {
              prevValue = digitalValue;
              dist = 1;
            }
            index++;
            //prevValue = digitalValue;
          }
        }
        first = true;
      }
    } catch (IOException e) {
      System.err.println(e);
    }

    return L;  // temporary
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
  public void printArray(double[] arr) {
    for (int i = 0; i < arr.length; i++) System.out.println(" " + arr[i]);
  }

  /**
   * Helper to convert old data to Data object
   * -Same function as in Threshold.java
  **/
  private Data convertToThreshData(double[] vals) {
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
    Data tmp = new Data(new double[21], V_GS, multArray(I, 0.1), 100);
    printData(tmp);
    return tmp;
  }
  public double[] multArray(double[] arr, double s) {
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

  private Data convertToLambData(double[] vals) {
    double[] V_D = new double[45];
    double[] V_S = new double[45];
    double[] D = new double[45];
    for (int i = 0; i < vals.length; i++ )
      if (i < 45)
        V_D[i] = vals[i];       // First 45 values are Voltages at the Drain
      else if (i < 90)
        V_S[i - 45] = vals[i];  // Second 45 values are Voltages at the Source
      else
        D[i - 90] = vals[i]*100;    // Final 45 values are Current through the Load Resistor

    double[] V_DS = pairer(V_D, V_S); // Makes difference in V_DS array

    Data tmp = new Data(V_DS, new double[45], D, 100);
    return tmp;
  }
  private double[] pairer(double[] x, double[] y) {
    if (x.length != y.length)
      throw new IllegalArgumentException("Array lengths are not equal");
    double[] temp = new double[x.length];
    for (int i = 0; i < temp.length; i++ )
      temp[i] = x[i] - y[i];
    return temp;
  }
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// Testing for the class:

  /**
   * Main function for testing this specific class
  **/
  public static void main(String[] args) {
    NMOSComs t = new NMOSComs("COM10");
    t.startThresholdSweep();
  	Threshold test = new Threshold(t.ThresholdSweep);
  	Display dis = new Display();
  	dis.addThresholdChart(test);
  	dis.showAllCharts();
	
    //t.startLambdaSweep(3.2);
    //t.startThresholdSweep();
    //t.testToDigital(10);
    //t.testToAnalog(10);
    //t.testToDoubleArray();
    //t.testPrintData();
    //t.testGetArray();
  }
}