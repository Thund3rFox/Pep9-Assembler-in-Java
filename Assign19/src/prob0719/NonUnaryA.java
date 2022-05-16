package prob0719;

public class NonUnaryA extends ACode {
    private final Mnemon mnemonic;
    private final AArg inSpecifier;
    private final AddrMode addrMode;


    public NonUnaryA(Mnemon mn, AArg inSpecifier, AddrMode addrMode) {
        mnemonic = mn;
        this.inSpecifier = inSpecifier;
        this.addrMode = addrMode;
    }

    @Override
    public String generateListing() {
        return String.format("%s    %s \n", Maps.mnemonStringTable.get(mnemonic), inSpecifier.generateListing());
    }
    @Override
    public String generateCode() {
        if (Util.isBR(mnemonic)) {
            int ordinal = 0;

            if (addrMode == addrMode.AM_I) {
                //do nothing
            } else if (addrMode == addrMode.AM_X) {
                ordinal = 1;
            } else {
                System.out.printf("NonUnaryA generateCode error.");
            }
            return String.format("%02X %s\n", Maps.mnemonIntTable.get(mnemonic) +
                    ordinal, inSpecifier.generateCode());
        } else {
            return String.format("%02X %s\n", Maps.mnemonIntTable.get(mnemonic) +
                    addrMode.ordinal(), inSpecifier.generateCode());
        }
    }
}

