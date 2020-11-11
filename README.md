# Welcome to the MosBox Java Application

> The MosBox is a device used to analyze characteristics of various semiconductors, including both N-Type and P-Type Metal-Oxide Semiconductor Field Effect Transistors (MOSFETs), which we will refer to as NMOS and PMOS transistors respectively, as well as Diodes and Bipolar Junction Transistors (BJTs).
>
> This is the repository for the Java Application that will be accompanying the device.

Here is a run-down of the Java Classes being used in the creation of said application, in order of their hierarchy:

---

## gui.java
General Description:
> -This is a Basic Graphic User Interface for the Semiconductor Parameter Analyzer. Has Titles, Labels, a Drop-Down Box, and some buttons.
>  -When in use, the User is supposed do click on the drop-down menu next to the label 'Choose a device:', select between the listed devices, and then click the button labeled 'Analyze'. Afterwards, the respective class will be called and the relative data will be displayed in the form of graphs.
> -Currently, the NMOS devices are being prioratized during devlopement.

### NMOS.java

#### NMOSComs.java

#### Threshold.Java

#### Lambda.java

#### Pins.java

#### LinearFit.java

#### Display.java

#### Export.java
