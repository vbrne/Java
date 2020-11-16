/*******************************************************************************
 *
 * University of Texas Rio Grande Valley
 * Computer Engineering
 * Senior Design
 * Spring/Fall 2020
 * Group 15: Bernie Villalon, Samuel Solis, Leo Marroquin
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
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumnModel;

public class gui {
	JButton button;
	JButton exit;
	JFrame frame;
	JLabel flash;
	JMenu menuExport;
	JMenu nmos;
	JMenuBar bar;
	JMenuItem exportLamb;
	JMenuItem exportThresh;
	JPanel botPanel;
	JPanel leftPanel;
	JPanel panel;
	JScrollPane leftScrollPane;

  NMOS n;
  String COMPort = "";
  boolean nFlag = false;
	JLabel thresholdLabel;
	JLabel knLabel;
	JLabel lambdaLabel;
	JPanel chartsPanel;
	JScrollPane chartsScrollPane;
	JTable lambdaTable;
	JTable thresholdTable;

	private static DecimalFormat df = new DecimalFormat("0.000");
	private final int margin = 2;
  
	public  gui() {
		createFrame();	// Creates JFrame (window)
		createBar();		// Creates Menu Bar
		//createPanel();	// Creates Center Panel
		createLeftPanel();
		//createScroll();
		createBotPanel();
		//createExit();		// Creates Exit Button
		updateFrame();	// Adds/Updates elements to Frame
  }

	/****************************************************************************************************
	 * Creating the Frame
	**/
	private void createFrame() {
		frame = new JFrame("Semiconductor Parameter Analyzer");	// Initializes window with title
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		// Exits application when closing window
		frame.setSize(500, 720);
		//frame.setSize(176, 720);																// Arbitrary window size
		frame.setLayout(new BorderLayout( ));
	}

	/****************************************************************************************************
	 * Creating the MenuBar w/ components
	**/
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

	/****************************************************************************************************
	 * Creating Panel for Center Components
	**/
	private void createLeftPanel() {
		leftPanel = new JPanel();
		BoxLayout boxlayout = new BoxLayout(leftPanel, BoxLayout.Y_AXIS);
		leftPanel.setLayout(boxlayout);
		leftPanel.setBorder(new EmptyBorder(new Insets(margin, margin, margin, margin)));

		JLabel selCom = new JLabel("<html>" +
																"<span style='font-size:14px'>" +
																	"Choose a Com Port:" +
																"</span>" +
															"</html>");
		leftPanel.add(selCom);

		String[] comList = {"-", "COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7"};
		JComboBox<String> selComsList = new JComboBox<String>(comList);
		//selComsList.setPreferredSize(new Dimension(200, 25));
		//selComsList.setMaximumSize(selComsList.getPreferredSize());
		//leftPanel.add(selComsList);
		JPanel tmp = new JPanel();
		tmp.add(selComsList);
		leftPanel.add(tmp);	
		leftPanel.add(Box.createRigidArea(new Dimension(0, margin)));

		// "Choose a device:" text															// Initializes "Choose" Label
  	JLabel label = new JLabel("<html>" +
																"<span style='font-size:14px'>" +
																	"Choose a device:" +
																"</span>" +
															"</html>");
		//label.setHorizontalAlignment(JLabel.CENTER);
  	leftPanel.add(label);																		// Adds Label to Panel
		leftPanel.add(Box.createRigidArea(new Dimension(0, margin)));
		//leftPanel.add(new JSeparator()); 
		leftPanel.add(Box.createRigidArea(new Dimension(0, margin)));

		JButton nButton = new JButton("NMOS");
		nButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				COMPort = (String) selComsList.getSelectedItem();
				runNMOS();
			}
		});
		leftPanel.add(nButton);
		
		leftPanel.add(Box.createRigidArea(new Dimension(0, margin)));

    leftScrollPane = new JScrollPane(leftPanel,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		leftScrollPane.getVerticalScrollBar().setUnitIncrement(8);
	}

	/****************************************************************************************************
	 * Creating Bottom Panel
	**/
	private void createBotPanel() {
		botPanel = new JPanel(new BorderLayout());
		botPanel.setBorder(new EmptyBorder(new Insets(margin, margin, margin, margin)));

		flash = new JLabel("{Flash messages will be displayed here.}");													// For Flash messages like Errors
		flash.setHorizontalAlignment(JLabel.CENTER);						// Centers Flash Message
		botPanel.add(BorderLayout.NORTH, flash);								// Sets north of botPanel

		botPanel.add(BorderLayout.CENTER, Box.createRigidArea(new Dimension(0, margin)));

		exit = new JButton("Exit");															// Initializes "Exit" Button
  	exit.addActionListener(new ActionListener() {						// Listens for Action on "Exit" Button
  		public void actionPerformed(ActionEvent E) {
  			System.exit(0);																			// Exits Application when clicked
			}
		});
		botPanel.add(BorderLayout.SOUTH, exit);
		frame.getContentPane().add(BorderLayout.SOUTH, botPanel);
	}

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
		//frame.getContentPane().add(BorderLayout.WEST, leftPanel);
		frame.getContentPane().add(BorderLayout.WEST, leftScrollPane); // Adds Panel to the Left of the Window
		frame.revalidate();
		frame.setVisible(true);
	}
	
	/****************************************************************************************************/
	// NMOS Protocol: Runs NMOS Tests and Displays Data
	private void runNMOS() {
    if (nFlag) {
      runNMOS(nFlag);
      return;
    }

    System.out.println(COMPort);
    nFlag = true;

		n = new NMOS(COMPort);																		      // Initializes NMOS Class

		leftPanel.add(new JSeparator()); // Seperator; Prolly going to remove
		leftPanel.add(Box.createRigidArea(new Dimension(0, margin)));

		// Returning Data:
		// dats[] = {threshold, kn, lambda, resistance(?)}			// Creates Threshold Label with Data
		thresholdLabel = new JLabel("Threshold: " + df.format(n.dats[0]));
		leftPanel.add(thresholdLabel);													// Adds Threshold Label to the Panel

		knLabel = new JLabel("kn: " + df.format(n.dats[1]));	  // Creates kn Label with Data
		leftPanel.add(knLabel);																	// Adds kn Label to the Panel
    
		lambdaLabel = new JLabel("Lambda: " + n.dats[2]);			  // Creates Lambda Label with Data
		leftPanel.add(lambdaLabel);														  // Adds Lambda Label to the Panel

		leftPanel.add(Box.createRigidArea(new Dimension(0, margin)));
		
		exportThresh.setEnabled(true);													// Allows Access to Exporting in Menu Bar
		exportLamb.setEnabled(true);														// ""

		String[] colNames = {"VGS", "sqrtIDS"};
		String[][] thresholdTableValues = n.thresholdTable;
		thresholdTable = new JTable(thresholdTableValues, colNames);
		TableColumnModel colMod = thresholdTable.getColumnModel();
		//colMod.getColumn(0).setPreferredWidth(50);
		//colMod.getColumn(1).setPreferredWidth(50);
		//thresholdTable.setBounds(30,40,200,300);
		//leftPanel.add(thresholdTable);

		colNames[0] = "VDS"; colNames[1] = "IDS";
		String[][] lambdaTableValues = n.lambdaTable;
		lambdaTable = new JTable(lambdaTableValues, colNames);
		//leftPanel.add(lambdaTable);

		JScrollPane tmp = new JScrollPane(thresholdTable);
		//tmp.setPreferedSize(new Dimension(170,720));
		/*leftPanel.add(tmp);																									*/

		tmp = new JScrollPane(lambdaTable);
		//tmp.setPreferedSize(new Dimension(170,720));
		/*leftPanel.add(tmp);																									*/

		chartsPanel = new JPanel();                             // Initializes chartsPanel
		BoxLayout boxlayout = new BoxLayout(chartsPanel, BoxLayout.Y_AXIS); // Initializes BoxLayout for chartsPanel
		chartsPanel.setLayout(boxlayout);                       // Sets Layout; BoxLayout == Stacks on top of one another
		chartsPanel.setBorder(new EmptyBorder(new Insets(margin, margin, margin, margin))); // Sets 5-Pixel spacing from Border

		chartsPanel.add(n.getThreshPanel());                    // Loads Threshold Chart to Panel
		chartsPanel.add(n.getLambPanel());                      // Loads Lambda Chart to Panel
		
		chartsScrollPane = new JScrollPane(chartsPanel,         // Initializes ScrollPane (Scrollable-Panel)
   		ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,     // Only shows ScrollBars as needed
   		ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		chartsScrollPane.getVerticalScrollBar().setUnitIncrement(8); // Sets scroll speed to 8px/increment

		frame.setSize(1290,720);																// Arbitrary window size
		frame.getContentPane().add(BorderLayout.CENTER, chartsScrollPane);
		updateFrame();																					// Updates Window to show new Labels
	}
  
  // If runNMOS() has already run once, this simply runs again and updates instead of creating
  private void runNMOS(boolean fl) {
    n = new NMOS(COMPort);

    thresholdLabel.setText("Threshold: " + df.format(n.dats[0]));
    knLabel.setText("kn: " + df.format(n.dats[1]));	        // Creates kn Label with Data
    lambdaLabel.setText("Lambda: " + n.dats[2]);			      // Creates Lambda Label with Data
    
		chartsPanel = new JPanel();
		BoxLayout boxlayout = new BoxLayout(chartsPanel, BoxLayout.Y_AXIS); // Initializes BoxLayout for chartsPanel
		chartsPanel.setLayout(boxlayout);                       // Sets Layout; BoxLayout == Stacks on top of one another
		chartsPanel.setBorder(new EmptyBorder(new Insets(margin, margin, margin, margin))); // Sets 5-Pixel spacing from Border
		chartsPanel.add(n.getThreshPanel());
		chartsPanel.add(n.getLambPanel());

		frame.getContentPane().remove(chartsScrollPane);
		chartsScrollPane = new JScrollPane(chartsPanel,         // Initializes ScrollPane (Scrollable-Panel)
   		ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,     // Only shows ScrollBars as needed
   		ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		chartsScrollPane.getVerticalScrollBar().setUnitIncrement(8); 

		frame.getContentPane().add(BorderLayout.CENTER, chartsScrollPane);
		updateFrame();																					// Updates Window to show new Labels
  }
	
	//****************************************************************************************************************
  public static void main(String args[]) {
		gui g = new gui();
  } 
}

