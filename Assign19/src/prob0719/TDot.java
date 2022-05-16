package prob0719;

public class TDot extends AToken {
    private final String stringValue;

    public TDot(StringBuffer stringBuffer) {
        stringValue = new String(stringBuffer);
    }
    @Override
    public String getDescription() {
        return String.format("Dot command = %s", stringValue);
    }

    public String getStringValue() {
        return stringValue;
    }
}
