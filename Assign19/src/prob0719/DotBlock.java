package prob0719;

public class DotBlock extends ACode {
    private final DCommand dotCommand;
    private final AArg aArg;

    public DotBlock(DCommand d, AArg aArg) {
        dotCommand = d;
        this.aArg = aArg;
    }

    public String generateCode() {
        return "00" + " 00".repeat(Math.max(0, aArg.getIntValue() - 1)) + "\n";
    }



    public String generateListing() {
        return String.format("%s    %s\n", Maps.blockStringTable.get(dotCommand), aArg.generateListing());
    }
}
