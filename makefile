cp=$(pwd)./lib
dir=./sourceCode

all: Display

LinearFit: $(dir)/LinearFit.java
	javac -d $(cp) $(dir)/LinearFit.java

Pins: $(dir)/Pins.java
	javac -d $(cp) $(dir)/Pins.java

Threshold: $(dir)/Threshold.java
	javac -d $(cp) $(dir)/Threshold.java

Lambda: $(dir)/Lambda.java
	javac -d $(cp) $(dir)/Lambda.java

Display: $(dir)/Display.java
	javac -d $(cp) $(dir)/Display.java
	java -cp $(cp) Display									#Runs program; for DEVELOPEMENT

gui: $(dir)/gui.java
	javac -d $(cp) $(dir)/gui.java

NMOS: $(dir)/NMOS.java
	javac -d $(cp) $(dir)/NMOS.java

### ~NOTES~ ###
# 20.8.20 Commented out "package Java" from each code because it was messing with the classpaths(?)
# 20.8.20 Been having to mess around with compiling and classpaths and stuff ;-;
# 20.8.20 $(pwd) :: current directory
