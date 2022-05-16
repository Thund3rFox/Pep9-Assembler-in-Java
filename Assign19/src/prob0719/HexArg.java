package prob0719;

public class HexArg extends AArg{
    private final int intValue;
    public HexArg(int i) {
        intValue = i;
    }
    public String generateListing() {
        return "0x" + String.format("%04X", intValue);
    }

    public String generateCode() {
        return String.format("%02X %02X", (intValue / 256), (intValue % 256));
    }

    @Override
    int getIntValue() {
        return intValue;
    }
}
