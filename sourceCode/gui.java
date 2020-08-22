//package lib;

import java.awt.event.*;
import javax.swing.*;
import org.knowm.xchart.*;
import java.util.*;

//guru99.com/java-swing-gui.html#1
// I'm following this guide: javatpoint.com/java-swing

public class gui {
  public static void main(String args[]) {
    int xWin = 900;
    int yWin = 290;

    JFrame w = new JFrame("Semiconductor Parameter Analyzer");
    w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // This is good enough
    // Title of Project
    int titleXSize = 625;
    int titleYSize = 75;
    JLabel title = new JLabel("<html><span style='font-size:24px'>Semiconductor Parameter Analyzer</span></html>");
    title.setBounds(xWin/2 - titleXSize/2, 15, titleXSize, titleYSize);

    // Good enough
    // Displays Text
    int xStart = 175;
    int yStart = 125;
    JLabel pick = new JLabel("<html><span style='font-size:14px'>Choose a device:</span></html>");
    pick.setBounds(xStart, yStart, 180, 30);

    // Good enough
    // Box of Selections
    String devices[] = {"-", "DIODE", "NMOS", "PMOS", "BJT"};
    JComboBox<String> picker = new JComboBox<>(devices);
    picker.setBounds(xStart + 180, yStart, 140, 30);

    // Good enough
    // Confirms picked selection
    JLabel pickedd = new JLabel();
    pickedd.setBounds(xStart + 333, yStart + 30, 250, 30);

    // Needs Work
    // Box to Analyze
    JButton picked = new JButton("Analyze!");
    picked.setBounds(xStart + 335, yStart, 140,30);
    picked.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        int ind = picker.getSelectedIndex();
        String sel = devices[ind];
        String dev = "<html><span style='font-size:12px'>Selected: "
                    + sel + "</span></html>";
        pickedd.setText(dev);
        if (sel == "-") { /* Do Nothing */ }
        if (sel == "DIODE") { /* Run DIODE tests */ }
        if (sel == "NMOS") {
          NMOS TestN = null;
          TestN = new NMOS();
        }
        if (sel == "PMOS") { /* Run PMOS tests */ }
        if (sel == "BJT") { /* Run BJT tests */ }
      }
    });

    // Fully Funnctional
    // Exits Application
    JButton endButton = new JButton("Exit");
    int endXSize = 90;
    int endYSize = 30;
    endButton.setBounds(xWin/2 - endXSize/2, yWin - endYSize - 50, endXSize, endYSize);
    endButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent E) {
        System.exit(0);
      }
    });

    // Adds all previously defined components to UI
    w.add(title);
    w.add(pick);
    w.add(picker);
    w.add(pickedd);
    w.add(picked);
    w.add(endButton);

    // Window Settings
    w.setSize(xWin, yWin);
    w.setLayout(null);
    w.setVisible(true);
  }
}
