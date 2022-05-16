package prob0719;

public class DEnd extends ACode{
    private final DCommand dotCommand;

    public DEnd (DCommand d){
        dotCommand = d;
    }

    @Override
    public String generateCode() {
        return "zz\n";
    }

    @Override
    public String generateListing() {
        return Maps.endStringTable.get(dotCommand) + "\n";
    }
}
