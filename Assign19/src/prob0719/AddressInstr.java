package prob0719;

public class AddressInstr extends ACode {
    private final Mnemon mnemonic;
    private final AArg aArg;
    private final AddrMode addrMode;

    public AddressInstr(Mnemon mn, AArg aArg, AddrMode add) {
        mnemonic = mn;
        this.aArg = aArg;
        addrMode = add;
    }

    @Override
    public String generateListing() {
        return String.format("%s    %s \n", Maps.mnemonStringTable.get(mnemonic), aArg.generateListing());
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
                    ordinal, aArg.generateCode());
        } else {
            return String.format("%02X %s\n", Maps.mnemonIntTable.get(mnemonic) +
                    addrMode.ordinal(), aArg.generateCode());
        }
    }
}
