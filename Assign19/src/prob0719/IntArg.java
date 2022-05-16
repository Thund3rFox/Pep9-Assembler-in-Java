package prob0719;


public class IntArg extends AArg {
    private final int intValue;

    public IntArg(int i) {
        intValue = i;
    }

    public String generateListing() {
        return String.format("%d", intValue);
    }

    public String generateCode() {
        if (intValue >= 0) {
            return String.format("%02X %02X", (intValue / 256), (intValue % 256));
        } else {
            String negInt = String.format("%X", intValue);
            return String.format("%s %s", negInt.substring(4, 6), negInt.substring(6, 8));
        }
    }

    @Override
    int getIntValue() {
        return intValue;
    }
}