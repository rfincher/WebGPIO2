package framboos;

import static framboos.FilePaths.*;

public class OutPin extends GpioPin {

    private boolean printTrace;
    private int pinNum;

    public OutPin(int pinNumber, boolean debug, boolean prtTrace) {
        super(pinNumber, Direction.OUT, debug, prtTrace);
        printTrace = prtTrace;
        pinNum = pinNumber;
        setValue(false);
    }

    public void setValue(boolean isOne) {
        if (!isClosing) {
            printMessage("Pin-" + pinNum + ": " + (isOne ? "1" : "0"));
            
            writeFile(getValuePath(pinNumberCPU), isOne ? "1" : "0");
        }
    }

    @Override
    public void close() {
        setValue(false);
        super.close();
    }
}