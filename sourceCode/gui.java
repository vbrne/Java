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

public class gui {
	JFrame frame;
	JMenuBar bar;
	JMenu menuExport, nmos;
	JMenuItem exportThresh, exportLamb;
	JPanel panel;
	JLabel label;
	JComboBox<String> combo;
	JButton button, exit;
  
	public  gui() {
		createFrame();
		createBar();
		createPanel();
		createExit();
		updateFrame();
  }

	// ******************
	// Creating the Frame
	private void createFrame() {
		frame = new JFrame("Semiconductor Parameter Analyzer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300,150);
	}

	// **********************************
	// Creating the MenuBar w/ components
	private void createBar() {
  	bar = new JMenuBar();
		
  	// Exporting menu in bar
  	menuExport = new JMenu("Export");
  	bar.add(menuExport);

  	// NMOS Menu in Exporting Menu
  	nmos = new JMenu("NMOS");
  	nmos.setMnemonic(KeyEvent.VK_N);

  	// Threshold Export in NMOS Menu
  	exportThresh = new JMenuItem("Export Threshold.csv");
  	exportThresh.setAccelerator(KeyStroke.getKeyStroke(
  			KeyEvent.VK_T, ActionEvent.ALT_MASK));
  	exportThresh.setEnabled(false);
  	nmos.add(exportThresh);

  	// Lambda Export Item in NMOS Menu
  	exportLamb = new JMenuItem("Export Lambda.csv");
  	exportLamb.setAccelerator(KeyStroke.getKeyStroke(
  			KeyEvent.VK_L, ActionEvent.ALT_MASK));
		exportLamb.setEnabled(false);
  	nmos.add(exportLamb);

		// Adds item to bar
  	menuExport.add(nmos);
	}

	// ************************************
	// Creating Panel for Center Components
	private void createPanel() {
  	panel = new JPanel();

  	// "Choose a device:" text
  	label = new JLabel("<html><span style='font-size:14px'>Choose a device:</span></html>");
  	panel.add(label);
		
  	// List of devices to chose from
  	String devices[] = {"-", "DIODE", "NMOS", "PMOS", "BJT"};
    combo = new JComboBox<>(devices);
    panel.add(combo);
		
    // Button to Analyze
    button = new JButton("Analyze!");
    button.addActionListener(new ActionListener() {
    	public void actionPerformed( ActionEvent e) {
    		int ind = combo.getSelectedIndex();
    		String sel = devices[ind];
    		
    		if (sel == "NMOS") runNMOS();
				else new NMOS(false);
  		}
		});
    panel.add(button);
	}

	// ***************************
	// Creating Bottom Exit Button
	private void createExit() {
  	exit = new JButton("Exit");
  	exit.addActionListener(new ActionListener() {
  		public void actionPerformed(ActionEvent E) {
  			System.exit(0);
			}
		});
	}

	// ******************************
	// Adding everything to the frame
	private void updateFrame() {
		frame.getContentPane().add(BorderLayout.NORTH, bar);
		frame.getContentPane().add(BorderLayout.CENTER, panel);
		frame.getContentPane().add(BorderLayout.SOUTH, exit);
		frame.setVisible(true);
	}

	private void runNMOS() {
		NMOS n = new NMOS();
		exportThresh.setEnabled(true);
		exportLamb.setEnabled(true);
	}
	
	//****************************************************************************************************************
  public static void main(String args[]) {
		gui g = new gui();
  } 
}

