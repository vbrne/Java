#Makefile: Add/Rem script in "all:" as necessary for testing and developing
#Format:
# {scriptName}: {requirements}
#	  {Terminal Command}

cp=$(pwd)./lib
dir=./sourceCode

all: LinearFit Pins Threshold Lambda Display NMOS gui
LinearFit: $(dir)/LinearFit.java
	javac -d $(cp) $(dir)/LinearFit.java
	#java -cp $(cp) LinearFit 							#Runs program; for DEVELOPEMENT

Pins: $(dir)/Pins.java
	javac -d $(cp) $(dir)/Pins.java
	#java -cp $(cp) Pins 										#Runs program; for DEVELOPEMENT

Threshold: $(dir)/Threshold.java
	javac -d $(cp) $(dir)/Threshold.java
	#java -cp $(cp) Threshold								#Runs program; for DEVELOPEMENT

Lambda: $(dir)/Lambda.java
	javac -d $(cp) $(dir)/Lambda.java
	#java -cp $(cp) Lambda 									#Runs program; for DEVELOPEMENT

Display: $(dir)/Display.java
	javac -d $(cp) $(dir)/Display.java
	#java -cp $(cp) Display									#Runs program; for DEVELOPEMENT

gui: $(dir)/gui.java
	javac -d $(cp) $(dir)/gui.java
	#java -cp $(cp) gui 										#Runs program; for DEVELOPEMENT

NMOS: $(dir)/NMOS.java
	javac -d $(cp) $(dir)/NMOS.java
	#java -cp $(cp) NMOS 										#Runs program; for DEVELOPEMENT

### ~NOTES~ ###
# 20.8.20 Commented out "package Java" from each code because it was messing with the classpaths(?)
# 20.8.20 Been having to mess around with compiling and classpaths and stuff ;-;
# 20.8.20 $(pwd) :: current directory
