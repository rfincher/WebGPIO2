package framboos;

import static framboos.FilePaths.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class GpioPin {

    private static final int[] mappedPins = {17, 18, 21, 22, 23, 24, 25, 4, 0, 1, 8, 7, 10, 9, 11, 14, 15};
    protected final int pinNumberCPU;
    protected boolean isClosing = false;
    protected boolean debugging;
    protected boolean printTrace;
    protected int pinNum;

    public GpioPin(int pinNumber, Direction direction, boolean debug, boolean prtTrace) {
        this.pinNumberCPU = mappedPins[pinNumber]; //this is the pin on the cpu chip, not the GPIO number
        pinNum=pinNumberCPU;  // This is the GPIO number.
        debugging = debug;
        printTrace = prtTrace;

        printMessage("Initializing pin-" + pinNumber + " " + direction.getValue()+"\n");
        
        if (!debugging) {
            writeFile(getExportPath(), Integer.toString(this.pinNumberCPU));
            writeFile(getDirectionPath(this.pinNumberCPU), direction.getValue());
        }
    }

    public boolean getValue() {
        if (isClosing) {
            return false;
        }
        try {
            boolean value;
            if (!debugging) {
                FileInputStream fis = new FileInputStream(getValuePath(pinNumberCPU));
                value = (fis.read() == '1');
                fis.close();
            } else {
                value = false;
            }

            printMessage("Pin-" + pinNum + ", value = " + value);
            
            return value;
        } catch (IOException e) {
            throw new RuntimeException("Could not read from GPIO file: " + e.getMessage());
        }
    }

    public void close() {
        isClosing = true;
        
        printMessage("Closing Pin-"+pinNum);
        
        writeFile(getUnexportPath(), Integer.toString(pinNumberCPU));
    }

    protected void writeFile(String fileName, String value) {
        try {
            if (!debugging) {
                FileOutputStream fos = new FileOutputStream(fileName);
                fos.write(value.getBytes());
                fos.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not write to GPIO file: " + e.getMessage());
        }
    }

    public enum Direction {

        IN("in"),
        OUT("out"),
        PWM("pwm");
        private String value;

        Direction(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
    
    protected void printMessage(String s) { // Print the String in s to the screen.
        if(printTrace) {
            System.out.println(s);
        }
    }
}
