/*********************************************
 * My Notes describing classes and functions
 *  Last updated: 12.04.2020
**/
GUI Class::

	gui() (Constructor)
		Creates the Frame, being the entire window itself
		Creates the Bar, shown simply having an "Export" item in the photo
		Creates the LeftPanel, where all the options are shown, such as choosing a COM Port and Device
		Creates the BotPanel, being the flash message and long exit button at the bottom 
		Lastly, it updates the Frame; 
			This "loads" the frame itself; it isnt in the createFrame() simply because I wanted a seperate function
				to update the frame after whenever I add new items

	createFrame() (Helper Function)
		Simple, JFrame frame is creted with the title "Semiconductor Parameter Analyzer"
		It sets the default close operation, being that the 'x' on the top right closes the window
		It sets the size of the window, being 500px by 720px in this example
			This is honestly arbitrary, but its simply set big enough to show the entire initial construction
		Lastly, it sets the Layout of the frame as BorderLayout
			BorderLayout is simply a type of Layout that separates the frame into 5 quadrants: North, East, South, 
				West, and Center 
			This is used to make it easy to simply add items to the frame and not have to worry about the placing 
				them at specified coordinates
			This also allows for Dynamic Rearrangement of already created items if new items are added or the window 
				is re-sized

	createBar() (Helper Function) //Straight forward...
		Initializes the Bar
		Creates the Export Menu Item 
		Creates the NMOS Option in the Export Menu Item 
		Creates two Options within the NMOS option which take op the bulk of the clode: exportThresh & exportLamb
			Both exportThresh & exportLamb have action listeners that are programmed to export the data from each 
				analysis when pressed
			Notice: Both options are disabled (Lines 126 & 139) to prevent users from trying to export non-existent
				data before the analysis has even Run 
		Each of these items are added in reverse order
		The Bar is added to the NORTH section of the Frame

		Short Version:
			The Bar is created,
			The Export option is added to the Bar
			The NMOS option is added to the Export option
			The two Export Threshold.csv and Export Lambda.csv are added to the NMOS option
			The Bar is added to the NORTH section of the Frame 


	createLeftPanel() (HelperFunction)
		Initializes the LeftPanel
		Sets the Layout of the leftPanel to BoxLayout


		