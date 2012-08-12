package framboos;

public class InPin extends GpioPin {

    public InPin(int pinNumber, boolean debug, boolean prtTrace) {
        super(pinNumber, Direction.IN, debug, prtTrace);
    }
}
