#Makefile: Add/Rem script in "all:" as necessary for testing and developing
#Format:
# {scriptName}: {requirements}
#	  {Terminal Command}

cp=./lib
dir=./sourceCode

all: CompileAll

CompileAll:
	javac -cp $(cp) -d $(cp) $(dir)/*.java

LinearFit: $(dir)/LinearFit.java
	javac -d $(cp) $(dir)/LinearFit.java
	java -cp $(cp) LinearFit

Pins: $(dir)/Pins.java
	javac -d $(cp) $(dir)/Pins.java
	java -cp $(cp) Pins

Threshold: $(dir)/Threshold.java
	javac -cp $(cp) -d $(cp) $(dir)/Threshold.java
	java -cp $(cp) Threshold

Lambda: $(dir)/Lambda.java
	javac -cp $(cp) -d $(cp) $(dir)/Lambda.java
	java -cp $(cp) Lambda

Display: $(dir)/Display.java
	javac -cp $(cp) -d $(cp) $(dir)/Display.java
	java -cp $(cp) Display

gui: $(dir)/gui.java
	javac -cp $(cp) -d $(cp) $(dir)/gui.java
	java -cp $(cp) gui

NMOS: $(dir)/NMOS.java
	javac -cp $(cp) -d $(cp) $(dir)/NMOS.java
	java -cp $(cp) NMOS
	
Export: $(dir)/Export.java
	javac -cp $(cp) -d $(cp) $(dir)/Export.java
	java -cp $(cp) Export

### ~NOTES~ ###
# 20.8.20 Commented out "package Java" from each code because it was messing with the classpaths(?)
# 20.8.20 Been having to mess around with compiling and classpaths and stuff ;-;
# 20.8.20 $(pwd) :: current directory
# 20.8.27 I didn't know you can do '/*' to compile an entire folder o.O
