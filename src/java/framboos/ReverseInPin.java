package framboos;

public class ReverseInPin extends InPin {

    public ReverseInPin(int pinNumber, boolean debug, boolean prtTrace) {
        super(pinNumber, debug, prtTrace);
    }

    @Override
    public boolean getValue() {
        return !super.getValue();
    }
}
