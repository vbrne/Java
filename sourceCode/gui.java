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

import javax.swing.*;						// For gui
import java.awt.*;							// For gui
import java.awt.event.*;				// The above didn't cut it for some reason :/

import java.text.DecimalFormat;	// For formatting displayed data

import org.knowm.xchart.*;			// For Testing
import java.util.*;							// --?

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

	private static DecimalFormat df = new DecimalFormat("0.000");
  
	public  gui() {
		createFrame();	// Creates JFrame (window)
		createBar();		// Creates Menu Bar
		createPanel();	// Creates Center Panel
		//createScroll();
		createExit();		// Creates Exit Button
		updateFrame();	// Adds/Updates elements to Frame
  }

	/****************************************************************************************************/
	// Creating the Frame
	private void createFrame() {
		frame = new JFrame("Semiconductor Parameter Analyzer");	// Initializes window with title
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		// Exits application when closing window
		frame.setSize(1240,700);																// Arbitrary window size
		frame.setLayout(new BorderLayout( ));
	}

	/****************************************************************************************************/
	// Creating the MenuBar w/ components
	private void createBar() {
  	bar = new JMenuBar();																		// Initializes Menu Bar
		
  	// Exporting menu in bar
  	menuExport = new JMenu("Export");												// Initializes Export Menu for Menu Bar
  	bar.add(menuExport);																		// Adds Export Menu to Menu Bar

  	// NMOS Menu in Exporting Menu
  	nmos = new JMenu("NMOS");																// Initializes NMOS Menu for previous Menu
  	nmos.setMnemonic(KeyEvent.VK_N);												// Adds 'N' keypress as shortcut

  	// Threshold Export in NMOS Menu
  	exportThresh = new JMenuItem("Export Threshold.csv");		// Initializes Item for previous Menu
  	exportThresh.setAccelerator(KeyStroke.getKeyStroke(			// Adds ALT+T as shortcut
  			KeyEvent.VK_T, ActionEvent.ALT_MASK));
  	exportThresh.setEnabled(false);													// Initializes as inaccessable
  	nmos.add(exportThresh);																	// Adds to NMOS Menu

  	// Lambda Export Item in NMOS Menu
  	exportLamb = new JMenuItem("Export Lambda.csv");				// Initializes Item for previous Menu
  	exportLamb.setAccelerator(KeyStroke.getKeyStroke(				// Adds ALT+L as shortcut
  			KeyEvent.VK_L, ActionEvent.ALT_MASK));
		exportLamb.setEnabled(false);														// Initializes as inaccessable
  	nmos.add(exportLamb);																		// Adds to NMOS Menu

		// Adds item to bar
  	menuExport.add(nmos);																		// Adds NMOS Menu to Export Menu

		frame.getContentPane().add(BorderLayout.NORTH, bar);		// Adds Bar to the Top of the Window
	}

	/****************************************************************************************************/
	// Creating Panel for Center Components
	private void createPanel() {															// Initializes center Panel
  	panel = new JPanel();
		//panel.setLayout(new BoxLayout(BoxLayout.Y_AXIS));

  	// "Choose a device:" text															// Initializes "Choose" Label
  	label = new JLabel("<html><span style='font-size:14px'>Choose a device:</span></html>");
  	panel.add(label);																				// Adds Label to Panel
		
  	// List of devices to chose from												// "List" of devices
  	String devices[] = {"-", "DIODE", "NMOS", "PMOS", "BJT"};
    combo = new JComboBox<>(devices);												// Initializes Combo-Box for devices
    panel.add(combo);																				// Adds Combo-Box to Panel

    // Button to Analyze
    button = new JButton("Analyze!");												// Initializes "Analyze" Button
    button.addActionListener(new ActionListener() {					// Listens for Action on "Analyze" Button
    	public void actionPerformed( ActionEvent e) {
    		int ind = combo.getSelectedIndex();
    		String sel = devices[ind];													// Checks which device is selected
    		if (sel == "NMOS") 																	// Checks if "NMOS" is selected
					runNMOS();																				// Runs NMOS protocol
				else 
					new NMOS(false);																	// Displays 'F' if NMOS isn't selected
  		}
		});
    panel.add(button);																			// Adds "Analyze" Button to Panel
	}

	/****************************************************************************************************/
	// Creating Scroll Bar
	private void createScroll() {
		JScrollBar scroll = new JScrollBar(JScrollBar.VERTICAL);
		frame.getContentPane().add(BorderLayout.EAST, scroll);
	}

	/****************************************************************************************************/
	// Creating Bottom Exit Button
	private void createExit() {
  	exit = new JButton("Exit");															// Initializes "Exit" Button
  	exit.addActionListener(new ActionListener() {						// Listens for Action on "Exit" Button
  		public void actionPerformed(ActionEvent E) {
  			System.exit(0);																			// Exits Application when clicked
			}
		});
		frame.getContentPane().add(BorderLayout.SOUTH, exit);		// Adds Exit to the Bottom of the Window
	}

	/****************************************************************************************************/
	// Adding everything to the frame
	// Also used to reload if changes
	private void updateFrame() {
		frame.getContentPane().add(BorderLayout.WEST, panel);		// Adds Panel to the Center of the Window
		frame.setVisible(true);
	}
	
	/****************************************************************************************************/
	// NMOS Protocol: Runs NMOS Tests and Displays Data
	private void runNMOS() {
		NMOS n = new NMOS();																		// Initializes NMOS Class
		
		/*
		// Returning Data:
		// dats[] = {threshold, kn, lambda, resistance(?)}			// Creates Threshold Label with Data
		JLabel threshold = new JLabel("Threshold: " + df.format(n.dats[0]));
		panel.add(threshold);																		// Adds Threshold Label to the Panel
		JLabel kn = new JLabel("kn: " + df.format(n.dats[1]));	// Creates kn Label with Data
		panel.add(kn);																					// Adds kn Label to the Panel
		JLabel lambda = new JLabel("Lambda: " + n.dats[2]);			// Creates Lambda Label with Data
		panel.add(lambda);																			// Adds Lambda Label to the Panel
		*/

		exportThresh.setEnabled(true);													// Allows Access to Exporting in Menu Bar
		exportLamb.setEnabled(true);														// ""
		frame.getContentPane().add(BorderLayout.CENTER, n.getThreshPanel());
		//frame.getContentPane().add(BorderLayout.EAST, n.getLambPanel());
		updateFrame();																					// Updates Window to show new Labels
	}
	
	//****************************************************************************************************************
  public static void main(String args[]) {
		gui g = new gui();
  } 
}

