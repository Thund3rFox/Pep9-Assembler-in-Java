package prob0719;

public class TAddress extends AToken {
    private final String stringValue;

    public TAddress(StringBuffer stringBuffer) {
        stringValue = new String(stringBuffer);
    }
    @Override
    public String getDescription() {
        return String.format("Addressing Mode = %s", stringValue);
    }

    public String getStringValue() {
        return stringValue;
    }
}
