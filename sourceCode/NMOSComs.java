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
import java.util.Scanner;

public class NMOSComs {
  File flg = new File("h7f5k68k.DAT");
  File vds = new File("bk851d39.DAT");
  File vgs = new File("y6u4w0r7.DAT");
  File vth = new File("eltonj.DAT");
  File drp = new File("drp.DAT");

  public NMOSComs() {
    setFile(flg);
    setFile(vds);
    setFile(vgs);
    setFile(vth);
    setFile(drp);
  }

  public void startSweep1() {
    try {
      FileWriter tmp = new FileWriter(flg.getName());
      tmp.write("true");
      tmp.close();

      while(true) {
        Scanner readVds = new Scanner(vds);         // Initiates Scanner
        Scanner readVgs = new Scanner(vgs);         // Initiates Scanner
        Scanner readDrp = new Scanner(drp);         // Initiates Scanner

        String sigVds = readVds.nextLine();         // Stores first line
        String sigVgs = readVgs.nextLine();         // Stores first line
        String sigDrp = readDrp.nextLine();         // Stores first line

        readVds.close();                            // Closes files
        readVgs.close();                            // Closes files
        readDrp.close();                            // Closes files

        System.out.println(sigVds + ", " + sigVgs); // For Debug

        if (sigVds == "true" && sigVgs == "true" && sigDrp == "true") break; // Check if files were updated

        try {                                       // Delay
          Thread.sleep(250);
        } catch (InterruptedException ie) {
          Thread.currentThread().interrupt();
        }
      }
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

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

  public static void main(String[] args) {
    NMOSComs n = new NMOSComs();
    n.startSweep1();
    /*try {
      File myObj = new File("filename.txt");
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        System.out.println(data);
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }*/
  }
}