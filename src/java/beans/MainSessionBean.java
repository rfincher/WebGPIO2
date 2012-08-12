/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import framboos.OutPin;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Rick
 */
@ManagedBean
@SessionScoped
public class MainSessionBean implements Serializable {

    private static boolean DEBUGGING = false;
    private static boolean PRINTTRACE = true;
    private OutPin pin7Out = null;
    private OutPin pin0Out = null;
    private OutPin pin2Out = null;
    private OutPin pin3Out = null;
    private OutPin pin4Out = null;

    /**
     * Creates a new instance of MainSessionBean
     */
    public MainSessionBean() {
        init();
    }

    private void init() {
        printMessage("\n\nEntering WebGPIO\n");
//        pwmInitialized = false;
//        pwmInit();
    }

    public void blink7() { // Blink gpio pin 7 a set number of times.
        // Print text between quotes to screen. The \n is a "new line" character,
        // It prints an extra return line to the screen.
        printMessage("Entering method blink7\n");

        // Set pin 7 to be output.
        if (pin7Out == null) {
            pin7Out = new OutPin(7, DEBUGGING, PRINTTRACE);
        }

        //  Loop through this 3 times, change the "3" in "i<3" to 
        // the number of times you want it to loop.
        for (int i = 0; i < 3; i++) {
            // Print out a message with the loop number.
            printMessage("Entering loop, iteration: " + i);


            // Turn on pin 7.
            pin7Out.setValue(true);

            // Delay for number of milliseconds in parentheses.
            // this leaves LED on for a time period.
            delayMilliSec(500 * (i + 1));

            // turn off gpio pin 7
            pin7Out.setValue(false);

            // Delay for number of milliseconds in parentheses.
            // This leaves LED off for a time period.
            delayMilliSec(500 * (i + 1));
        }

        // Print "End of method blink7" to the screen
        printMessage("End of method blink7");
    }

    public void blink234() { // Blink gpio pin 7 a set number of times.
        // Print text between quotes to screen. The \n is a "new line" character,
        // It prints an extra return line to the screen.
        printMessage("Entering method blink234\n");
        // Set pin 7 to be output.
        if (pin2Out == null) {
            pin2Out = new OutPin(2, DEBUGGING, PRINTTRACE);
        }
        
        if (pin3Out == null) {
            pin3Out = new OutPin(3, DEBUGGING, PRINTTRACE);
        }
        
        if (pin4Out == null) {
            pin4Out = new OutPin(4, DEBUGGING, PRINTTRACE);
        }
        
        pin2Out.setValue(true);
        delayMilliSec(5000);
        pin2Out.setValue(false);
        pin3Out.setValue(true);
        delayMilliSec(5000);
        pin3Out.setValue(false);
        pin4Out.setValue(true);
        delayMilliSec(5000);
        pin4Out.setValue(false);
        
        for (int j = 0; j < 120; j++) {
            pin2Out.setValue(true);
            pin3Out.setValue(true);
            pin4Out.setValue(true);
            delayMilliSec(250);
            pin2Out.setValue(false);
            pin3Out.setValue(false);
            pin4Out.setValue(false);
            delayMilliSec(250);
        }
        
        printMessage("End of method blink234");
    }
    

    public void pin0On() {
        if (pin0Out == null) {
            pin0Out = new OutPin(0, DEBUGGING, PRINTTRACE);
        }

        // turn off gpio pin 0
        pin0Out.setValue(true);

    }

    public void pin0Off() {
        if (pin0Out == null) {
            pin0Out = new OutPin(0, DEBUGGING, PRINTTRACE);
        }

        // turn off gpio pin 0
        pin0Out.setValue(false);

    }

    private void delayMilliSec(int mS) {  // Wait the number of milliSeconds in the integer variable mS.

        // The "try" statement is paired with the "catch" statement below to catch errors.
        // Errors are called "Exceptions" in Java.
        // If the code between the curly braces after "try" generates the errors listed
        // in the "catch" part, the program immediately jumps down to the code in the catch 
        // part and runs it.  In this case a message just gets printed.
        try {
            printMessage("Delaying for " + mS + " milliSeconds\n");

            Thread.sleep(mS);
        } catch (InterruptedException ie) {
            System.err.println("Setting gpio failed because of an interrupt error.");
        }
    }

    private void printMessage(String s) { // Print the String in s to the screen.
        if (PRINTTRACE) {
            System.out.println(s);
        }
    }

    @Override
    @SuppressWarnings("FinalizeDeclaration")
    protected void finalize() throws Throwable {
        if (pin7Out != null) {
            pin7Out.close();
        }
        
        super.finalize();
    }
}
