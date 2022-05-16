package prob0719;

public class THex extends AToken{
    private final int intValue;

    public THex(int i) {
        intValue = i;
    }

    @Override
    public String getDescription() {
        return String.format("Hexadecimal constant = %d", intValue);
    }

    public int getHexValue(){
        return intValue;
    }
}
