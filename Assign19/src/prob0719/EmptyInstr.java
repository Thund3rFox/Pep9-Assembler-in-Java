package prob0719;

public class EmptyInstr extends ACode {
    // For an empty source line.
    @Override
    public String generateListing() {
        return "\n";
    }
    @Override
    public String generateCode() {
        return ""; }
}