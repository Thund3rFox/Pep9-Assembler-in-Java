package prob0719;

public class Util {
    public static boolean isDigit(char ch) {
        return ('0' <= ch) && (ch <= '9');
    }
    public static boolean isAlpha(char ch) {
        return (('a' <= ch) && (ch <= 'z') || ('A' <= ch) && (ch <= 'Z'));
    }
    public static boolean isHexAlpha(char ch) {
        return (('a' <= ch) && (ch <= 'f') || ('A' <= ch) && (ch <= 'F'));
    }

    public static int HexValue(char ch){
        int intHolder;

        if (isDigit(ch)){
            intHolder = ch - '0';
        } else {
            if(('a' <= ch) && (ch <= 'f')){
                intHolder = ch - 96 + 9;
            } else {
                intHolder = ch - '@' + 9;
            }
        }
        return intHolder;
    }
    //FIXME function to check if ints and hex are in range -32768 … 65535
    public static boolean isInRange(int i){
        return (i < 65536);
    }

    public static boolean isBR(Mnemon mn) {
        if (mn == Mnemon.M_BR || mn == Mnemon.M_BRLT || mn == Mnemon.M_BREQ || mn == Mnemon.M_BRLE) {
            return true;
        } else {
            return false;
        }
    }
}
