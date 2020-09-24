/*******************************************************************************
 *
 * University of Texas Rio Grande Valley
 * Computer Engineering
 * Senior Design
 * Spring/Fall 2020
 * Group 15: Bernie VIllalon, Samuel Solis, Leo Marroquin
 *
 * Description:
 *   This is a Basic Graphic User Interface for the Semiconductor Parameter
 * Analyzer. Has Titles, Labels, a Drop-Down Box, and some buttons.
 *   When in use, the User is supposed do click on the drop-down menu next to
 * the label 'Choose a device:', select between the listed devices, and then
 * click the button labeled 'Analyze'. Afterwards, the respective class will
 * be called and the relative data will be displayed in the form of graphs.
 *   Currently, the NMOS devices are being prioratized during devlopement.
 *
 ******************************************************************************/

import javax.swing.*;			// For gui
import java.awt.*;				// For gui
import java.awt.event.*;	// The above didn't cut it for some reason :/

import org.knowm.xchart.*;
import java.util.*;

//guru99.com/java-swing-gui.html#1
//https://www.guru99.com/java-swing-gui.html
// I'm following this guide: javatpoint.com/java-swing

public class gui { /*
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
    // Confirms picked selection in text
    JLabel pickedd = new JLabel();
    pickedd.setBounds(xStart + 333, yStart + 30, 250, 30);

    // Needs Work
    // Box to "Analyze"
    JButton picked = new JButton("Analyze!");
    picked.setBounds(xStart + 335, yStart, 140,30);
    picked.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        int ind = picker.getSelectedIndex();
        String sel = devices[ind];
        String dev = "<html><span style='font-size:12px'>Selected: "
                    + sel + "</span></html>";
        pickedd.setText(dev);

        NMOS n;

        if (sel == "DIODE") new NMOS(false);
        if (sel == "NMOS") runNMOS(w);
        if (sel == "PMOS") new NMOS(false);
        if (sel == "BJT") new NMOS(false);
      }
    });

    // Fully Funnctional
    // Box to Exit Application
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
    //w.add(pickedd);
    w.add(picked);
    //w.add(top);
    //w.add(bot);
    //w.add(export);
    w.add(endButton);

    // Window Settings
    w.setSize(xWin, yWin);
    w.setLayout(null);
    w.setVisible(true);
  }
  */
  public static void main(String args[]) {
  	// ******************
  	// Creating the Frame
  	JFrame frame = new JFrame("Semiconductor Parameter Analyzer");
  	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  	frame.setSize(300,300);
  	
  	// **********************************
  	// Creating the MenuBar w/ components
  	JMenuBar bar = new JMenuBar();
  	
  	// Exporting menu in bar
  	JMenu menuExport = new JMenu("Export");
  	bar.add(menuExport);
  	
  	// NMOS Menu in Exporting Menu
  	JMenu nmos = new JMenu("NMOS");
  	nmos.setMnemonic(KeyEvent.VK_N);
  	
  	// Threshold Export in NMOS Menu
  	JMenuItem exportThresh = new JMenuItem("Export Threshold.csv");
  	exportThresh.setAccelerator(KeyStroke.getKeyStroke(
  			KeyEvent.VK_T, ActionEvent.ALT_MASK));
  	nmos.add(exportThresh);
  	
  	// Lambda Export Item in NMOS Menu
  	JMenuItem exportLamb = new JMenuItem("Export Lambda.csv");
  	exportLamb.setAccelerator(KeyStroke.getKeyStroke(
  			KeyEvent.VK_L, ActionEvent.ALT_MASK));
  	nmos.add(exportLamb);
  	menuExport.add(nmos);
  	
  	// ************************************
  	// Creating Panel for Center Components
  	JPanel panel = new JPanel();
  	
  	// "Choose a device:" text
  	JLabel label = new JLabel("<html><span style='font-size:14px'>Choose a device:</span></html>");
  	panel.add(label);
  	
  	// List of devices to chose from
  	String devices[] = {"-", "DIODE", "NMOS", "PMOS", "BJT"};
    JComboBox<String> combo = new JComboBox<>(devices);
    panel.add(combo);
    
    // Button to Analyze
    JButton button = new JButton("Analyze!");
    panel.add(button);
  	
  	// ***************************
  	// Creating Bottom Exit Button
  	JButton exit = new JButton("Exit");
  	exit.addActionListener(new ActionListener() {
  		public void actionPerformed(ActionEvent E) {
  			System.exit(0);
			}
		});
		
		// ******************************
		// Adding everything to the frame
		frame.getContentPane().add(BorderLayout.NORTH, bar);
		frame.getContentPane().add(BorderLayout.CENTER, panel);
		frame.getContentPane().add(BorderLayout.SOUTH, exit);
		frame.setVisible(true);
	}
  
  static void runNMOS(JFrame w) {
  	//NMOS n = new NMOS();
  	
  	// In Development
    // Checkboxes for Export function
    JCheckBox top = new JCheckBox("Export Threshold");
    JCheckBox bot = new JCheckBox("Export Lambda");
    
    // In Development
    // Box to Export 
    JButton export = new JButton("Export");
    export.setBounds(510, 160, 140, 30);
  	
  	w.add(export);
  	
  	w.setVisible(true);
  } 
}
