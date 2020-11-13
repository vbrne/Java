/*******************************************************************************
 *
 * University of Texas Rio Grande Valley
 * Computer Engineering
 * Senior Design
 * Spring/Fall 2020
 * Group 15: Bernie Villalon, Samuel Solis, Leo Marroquin
 *
 * Description:
 *   This Class is responsible for exporting the data gathered and used onto a
 * *.csv or *.txt file for the user to use in different applications if watned.
 *
 ******************************************************************************/

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Export {
  FileWriter write;
  ArrayList<String[]> list = new ArrayList<String[]>();

  /**
   * Converts Threshold Sweep (Sweep 1) into Comma-Seperated-Values (CSV) and writes out to file.
  **/
  public void exportThresh(Threshold Th) throws IOException {
    list.add(new String[] {"VGS", "IDS", "sqrtIDS", "V_GS_Eq", "I_Eq", "Threshold", "Kn"});
    list.add(toStringArr(Th.VGS));
    list.add(toStringArr(Th.IDS));
    list.add(toStringArr(Th.sqrtIDS));
    list.add(toStringArr(Th.V_GS_eq));
    list.add(toStringArr(Th.I_eq));
    list.add(new String[] { Double.toString(Th.THRESHOLD) });
    list.add(new String[] { Double.toString(Th.KN) });

    export("Threshold.csv");
  }

  /**
   * Converts Lambda Sweep (Sweep 2) into Comma-Seperated-Values (CSV) and writes out to file.
  **/
  public void exportLamb(Lambda La) throws IOException {
    list.add(new String[] {"VDS", "IDS", "Sriode V_DS", "Sriode I_DS", "Saturation V_DS", "Saturation I_DS", "Lambda", "Resistance"});
    list.add(toStringArr(La.VDS));
    list.add(toStringArr(La.IDS));
    list.add(toStringArr(La.tri_V_DS));
    list.add(toStringArr(La.tri_I_DS));
    list.add(toStringArr(La.sat_V_DS));
    list.add(toStringArr(La.sat_I_DS));
    list.add(new String[] {Double.toString(La.LAMBDA)});
    list.add(new String[] {Double.toString(La.RESIST)});

    export("Lambda.csv");
  }

  /**
   * Helper function to write out CSV to file
  **/
  private void export(String name) throws IOException {
    File file = new File(name);
    file.createNewFile();
    write = new FileWriter(file);

    for (int i = 0; i < list.get(0).length; i++) {
      write.append(list.get(0)[i]);
      write.append(",");
    }
    write.append("\n");

    for (int i = 0; i < list.get(1).length; i++) {
      for (int j = 1; j < list.size(); j++)
        if (i < list.get(j).length) {
          write.append(list.get(j)[i]); write.append(",");
        }
      write.append("\n");
    }

    write.flush();
    write.close();
    System.out.println("...exported to " + name + "...");
  }

  /**
   * Converts double[] array to String[] array.
  **/
  private String[] toStringArr(double[] d) {
    String[] str = new String[d.length];
    for (int i = 0; i < d.length; i++)
      str[i] = Double.toString(d[i]);
    return str;
  }
}
