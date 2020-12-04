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

		Short:
			The Bar is created,
			The Export option is added to the Bar
			The NMOS option is added to the Export option
			The two Export Threshold.csv and Export Lambda.csv are added to the NMOS option
			The Bar is added to the NORTH section of the Frame 


	createLeftPanel() (HelperFunction)
		Initializes the LeftPanel
		Sets the Layout of the leftPanel to BoxLayout
			BoxLayout is different from BorderLayout
				Box Layout automatically aligns things vertically when added.
		Creates Label to Choose the COM port.
		Creates a ComboBox, which is simply a drop-down selection menu.
			The ComboBox lists COMs 1-7 and a '-'
		Creates Label to choose a device 
			Currently only has NMOS since thats the only supported decice right now
		Creates Button for the NMOS
			Action Listener to listen for the button being pressed
				When Pressed: Assigns selected COM port to the global COMPort variable
				Executes the runNMOS() function, which analyzes the NMOS device 
		Adds everything to the leftPanel
		Creates a Scroll Pane labeled leftScrollPane
			Scroll Pane is a type of Panel, ?, that dynamically adds or removes any scroll 
				option when needed
			leftPanel is added to said Scroll Pane 
		leftScrollPane is eventually added to the frame, but thats done in updateFrame()

		runNMOS() (Helper Function to the Helper Function)
			Checks flag to see if program has already run the NMOS Analysis before
				If so, runs different runNMOS() that takes a boolean variable
					The input isnt needed, though it is used simply to have another function with the same name
					runNMOS(boolean) does pretty much the same thing, but instead of re-creating everything, it 
						simply updates the already existing items.
				If it has not, then it continues with the noremal function
			Changes the previously mentioned flag
			Runs the NMOS Analysis 
			After analysis, creates a Label to show the Threshold, kN, and Lambda values
				Adds them to the LeftPanel 
					This is the reason we didnt instantly add the LeftPanel to the frame when it was created; if 
						done so, these labeles would not have shown up since they were created after being added
			It enables the previously created Export Functions

			*It goes on a whole spiel to display each point on the analysis in a table 
				One of our advisors suggested we make it so if a user hovers their mouse pointer on the graph,
					then it would show that specific point on the graph
					We could not find anythong on the XYChart Library we are using, so we thought this might be 
						an okay alternative, though it currently isnt 100% functional

			Creates the Panel for the Charts, which will be added to the CENTER of the frame
				Sets it as the previously mentioned BoxLayout
					This is so the two charts are vertical and horizontal, for the users convenience
				Gets the Charts from the NMOS Analysis and adds them to the Panel
			Creates Scroll Pane 
				Adds the Panel to the a Scroll Pane, to get that Scroll Functionality
			Resizes Frame so that the Charts are visible
			Adds Scroll Pane to CENTER of Frame 
			Lastly, Updates Frame

	createBotPanel() (Helper Function)
		Creates BotPanel with a BorderLayou()
		Creates a flash Label and adds it to the NORTH section of the Panel
		Creates a Exit Button
			Action Listener; exits code when pressed 
			Adds Exit Button the the SOUTH section of the Panel
		Adds the Panel to the SOUTH of Frame 

	updateFrame() (Helper Function)
		Adds leftScrollPane to WEST of Frame 
		Sets Frame to be Visible 	

	Post Analysis Screenies:
		Example of the Post-Analysis Screen;
		This is slightly OutDated
			Weve been putting all efforts to the actually communications for the past few weeks
			You can see that the COMPort option hadnt been implemented on the leftPanel yet
		You can see the tables that were referred to 
			User can change values in the table, which is a no-no that needs to be fixed
			We dont know how itll handle an increase in data points as we expect to take a lot of them
		You can see all the nice Scroll Bars

		After resizing, you can see the Scroll Bars that werent needed have been removed
		

NMOS Class::
	