CP=$(pwd)/lib

all: LinearFit Pins Threshold Lambda Display gui

LinearFit: LinearFit.java
	javac -d ./lib LinearFit.java

Pins: Pins.java
	javac -d ./lib Pins.java

Threshold: Threshold.java
	javac -d ./lib Threshold.java

Lambda: Lambda.java
	javac -d ./lib Lambda.java

Display: Display.java
	javac -d ./lib Display.java

gui: gui.java
	javac -d ./lib gui.java

### ~NOTES~ ###
# 20.8.20 Commented out "package Java" from each code because it was messing with the classpaths(?)
# 20.8.20 Been having to mess around with compiling and classpaths and stuff ;-;
# 20.8.20 $(pwd) :: current directory
