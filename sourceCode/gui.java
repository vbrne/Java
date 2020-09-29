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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.Insets;

import java.io.IOException;             // For exporting function

import java.text.DecimalFormat;			    // For formatting displayed data

import javax.swing.AbstractAction;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.KeyStroke;

public class gui {
	JFrame frame;
	JMenuBar bar;
	JMenu menuExport, nmos;
	JMenuItem exportLamb, exportThresh;
	JPanel chartsPanel, leftPanel, panel;
	JLabel label, thresholdLabel, knLabel, lambdaLabel;
	JComboBox<String> combo;
	JButton button, exit;

  NMOS n;
  boolean nFlag = false;

	private static DecimalFormat df = new DecimalFormat("0.000");
  
	public  gui() {
		createFrame();	// Creates JFrame (window)
		createBar();		// Creates Menu Bar
		//createPanel();	// Creates Center Panel
		createLeftPanel();
		//createScroll();
		createExit();		// Creates Exit Button
		updateFrame();	// Adds/Updates elements to Frame
  }

	/****************************************************************************************************/
	// Creating the Frame
	private void createFrame() {
		frame = new JFrame("Semiconductor Parameter Analyzer");	// Initializes window with title
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		// Exits application when closing window
		frame.setSize(1280,720);																// Arbitrary window size
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
  	// Initializes Item for previous Menu
    exportThresh = new JMenuItem(new AbstractAction("Export Threshold.csv") {
      public void actionPerformed(ActionEvent e) {
        try { n.export(0); }
        catch (IOException e2) { System.out.println("!-- Could not Export Threshold.csv --!"); }
      }
    });
  	exportThresh.setAccelerator(KeyStroke.getKeyStroke(			// Adds ALT+T as shortcut
  			KeyEvent.VK_T, ActionEvent.ALT_MASK));
  	exportThresh.setEnabled(false);													// Initializes as inaccessable
  	nmos.add(exportThresh);																	// Adds to NMOS Menu

  	// Lambda Export Item in NMOS Menu
    // Initializes Item for previous Menu
    exportLamb = new JMenuItem(new AbstractAction("Export Lambda.csv") {
      public void actionPerformed(ActionEvent e) {
        try { n.export(1); }
        catch (IOException e2) { System.out.println("!-- Could not Export Lambda.csv --!"); }
      }
    });
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
	/*private void createPanel() {															// Initializes center Panel
  	panel = new JPanel();

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
	} */

	private void createLeftPanel() {
		leftPanel = new JPanel();

		BoxLayout boxlayout = new BoxLayout(leftPanel, BoxLayout.Y_AXIS);

		leftPanel.setLayout(boxlayout);

		leftPanel.setBorder(new EmptyBorder(new Insets(10, 16, 10, 16)));

		// "Choose a device:" text															// Initializes "Choose" Label
  	label = new JLabel("<html><span style='font-size:14px'>Choose a device:</span></html>");
  	leftPanel.add(label);																		// Adds Label to Panel

		JButton nButton = new JButton("NMOS");
		nButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				runNMOS();
			}
		});
		leftPanel.add(nButton);
		
    /*
  	// List of devices to chose from												// "List" of devices
  	String devices[] = {"-", "DIODE", "NMOS", "PMOS", "BJT"};
    combo = new JComboBox<>(devices);												// Initializes Combo-Box for devices
		combo.setSize(150,80);
    //leftPanel.add(combo);																	// Adds Combo-Box to Panel 
    */
    
    /*
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
    //leftPanel.add(button);																// Adds "Analyze" Button to Panel
    */

    JLabel seperator = new JLabel("-------");
    leftPanel.add(seperator);
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
		frame.getContentPane().add(BorderLayout.WEST, leftPanel); // Adds Panel to the Center of the Window
		frame.setVisible(true);
	}
	
	/****************************************************************************************************/
	// NMOS Protocol: Runs NMOS Tests and Displays Data
	private void runNMOS() {
    if (nFlag) {
      runNMOS(nFlag);
      return;
    }

    nFlag = true;

		n = new NMOS();																		      // Initializes NMOS Class

		// Returning Data:
		// dats[] = {threshold, kn, lambda, resistance(?)}			// Creates Threshold Label with Data
		thresholdLabel = new JLabel("Threshold: " + df.format(n.dats[0]));
		leftPanel.add(thresholdLabel);													// Adds Threshold Label to the Panel

		knLabel = new JLabel("kn: " + df.format(n.dats[1]));	  // Creates kn Label with Data
		leftPanel.add(knLabel);																	// Adds kn Label to the Panel
    
		lambdaLabel = new JLabel("Lambda: " + n.dats[2]);			  // Creates Lambda Label with Data
		leftPanel.add(lambdaLabel);														  // Adds Lambda Label to the Panel

    JLabel seperator = new JLabel("-------");
    leftPanel.add(seperator);
    
		
		exportThresh.setEnabled(true);													// Allows Access to Exporting in Menu Bar
		exportLamb.setEnabled(true);														// ""

		chartsPanel = new JPanel();
		chartsPanel.add(n.getThreshPanel());
		chartsPanel.add(n.getLambPanel());
    frame.getContentPane().remove(chartsPanel);
		frame.getContentPane().add(BorderLayout.CENTER, chartsPanel);
		updateFrame();																					// Updates Window to show new Labels
	}
  
  // If runNMOS() has already run once, this simply runs again and updates instead of creating
  private void runNMOS(boolean fl) {
    n = new NMOS();

    thresholdLabel.setText("Threshold: " + df.format(n.dats[0]));
    knLabel.setText("kn: " + df.format(n.dats[1]));	        // Creates kn Label with Data
    lambdaLabel.setText("Lambda: " + n.dats[2]);			      // Creates Lambda Label with Data
    
		chartsPanel = new JPanel();
		chartsPanel.add(n.getThreshPanel());
		chartsPanel.add(n.getLambPanel());
    frame.getContentPane().remove(chartsPanel);
		frame.getContentPane().add(BorderLayout.CENTER, chartsPanel);
		updateFrame();																					// Updates Window to show new Labels
  }
	
	//****************************************************************************************************************
  public static void main(String args[]) {
		gui g = new gui();
  } 
}

