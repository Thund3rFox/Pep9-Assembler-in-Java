package prob0719;

public class Tokenizer {
    private final InBuffer b;
    public Tokenizer(InBuffer inBuffer) {
        b = inBuffer;
    }
    public AToken getToken() {
        char nextChar;
        StringBuffer localStringValue = new StringBuffer("");
        int localIntValue = 0;
        int sign = +1;
        AToken aToken = new TEmpty();
        LexState state = LexState.LS_START;
        do {
            nextChar = b.advanceInput();
            switch (state) {
                case LS_START:
                    if (Util.isAlpha(nextChar)) {
                        localStringValue.append(nextChar);
                        state = LexState.LS_IDENT;
                    } else if (nextChar == '-') {
                        sign = -1;
                        state = LexState.LS_SIGN;
                    } else if (nextChar == '+') {
                        sign = +1;
                        state = LexState.LS_SIGN;
                    } else if (Util.isDigit(nextChar)) { //FIXED how to handle 0x input vs regular int input TWO CASES?
                        if (nextChar == '0') {
                            //localIntValue = 0;
                            state = LexState.LS_INT1;
                        } else if ((nextChar > '0') && (nextChar <= '9')){
                            localIntValue = nextChar - '0';
                            state = LexState.LS_INT2;
                        }
                    } else if (nextChar == '\n') {
                        state = LexState.LS_STOP;
                    } else if (nextChar == '.'){
                        state = LexState.LS_DOT1;
                    } else if (nextChar == ',') {
                        state = LexState.LS_ADDR1;
                    }else if (nextChar != ' ') {
                        aToken = new TInvalid();
                    }
                    break;
                case LS_IDENT:
                    if (Util.isAlpha(nextChar) || Util.isDigit(nextChar)) {
                        localStringValue.append(nextChar);
                    } else {
                        b.backUpInput();
                        aToken = new TIdentifier(localStringValue);
                        state = LexState.LS_STOP;
                    }
                    break;
                case LS_SIGN:
                    if (Util.isDigit(nextChar)) {
                        localIntValue = 10 * localIntValue + nextChar - '0';
                        state = LexState.LS_INT2;
                    } else {
                        aToken = new TInvalid();
                    }
                    break;
                case LS_HEX1:
                    if (Util.isDigit(nextChar) || Util.isHexAlpha(nextChar)) {
                        localIntValue = 16 * localIntValue + Util.HexValue(nextChar);
                        if (!(Util.isInRange(localIntValue))){
                            aToken = new TInvalid();
                            state = LexState.LS_STOP;
                        }
                        state = LexState.LS_HEX2;
                    } else {
                        aToken = new TInvalid();
                    }
                    break;
                case LS_HEX2:
                    if (Util.isDigit(nextChar) || Util.isHexAlpha(nextChar)) {
                        localIntValue = 16 * localIntValue + Util.HexValue(nextChar);
                        if (!(Util.isInRange(localIntValue))){
                            aToken = new TInvalid();
                            state = LexState.LS_STOP;
                        }
                    } else {
                        b.backUpInput();
                        aToken = new THex(localIntValue);
                        state = LexState.LS_STOP;
                    }
                    break;
                case LS_INT1:
                    if (Util.isDigit(nextChar)) {
                        localIntValue = 10 * localIntValue + nextChar - '0';
                        //FIXME each time you add to it you want to check that it hasnt overflowed (out of range)
                        if (localIntValue > 65535 || (sign == -1 && localIntValue > 32768)){
                            aToken = new TInvalid();
                            state = LexState.LS_STOP;
                        }
                        state = LexState.LS_INT2;
                    }else if ((nextChar == 'x') || (nextChar == 'X')){
                        state = LexState.LS_HEX1;
                    }else {
                        b.backUpInput();
                        aToken = new TInteger(sign * localIntValue);
                        state = LexState.LS_STOP;
                    }
                    break;
                case LS_INT2:
                    if (Util.isDigit(nextChar)) {
                        localIntValue = 10 * localIntValue + nextChar - '0';
                        //FIXME each time you add to it you want to check that it hasnt overflowed (out of range)
                        if (localIntValue > 65535 || (sign == -1 && localIntValue > 32768)){
                            aToken = new TInvalid();
                            state = LexState.LS_STOP;
                        }
                    } else {
                        b.backUpInput();
                        aToken = new TInteger(sign * localIntValue);
                        state = LexState.LS_STOP;
                    }
                    break;
                case LS_ADDR1:
                    if (Util.isAlpha(nextChar)) {
                        localStringValue.append(nextChar);
                        state = LexState.LS_ADDR2;
                    } else if (nextChar == ' ') {
                        //do nothing
                    } else {
                        aToken = new TInvalid();
                    }
                    break;
                case LS_ADDR2:
                    if (Util.isAlpha(nextChar) ) {
                        localStringValue.append(nextChar);
                    } else {
                        b.backUpInput();
                        aToken = new TAddress(localStringValue);
                        state = LexState.LS_STOP;
                    }
                    break;
                case LS_DOT1:
                    if (Util.isAlpha(nextChar)) {
                        localStringValue.append(nextChar);
                        state = LexState.LS_DOT2;
                    } else {
                        aToken = new TInvalid();
                    }
                    break;
                case LS_DOT2:
                    if (Util.isAlpha(nextChar) ) {
                        localStringValue.append(nextChar);
                    } else {
                        b.backUpInput();
                        aToken = new TDot(localStringValue);
                        state = LexState.LS_STOP;
                    }
                    break;
            }
        } while ((state != LexState.LS_STOP) && !(aToken instanceof TInvalid));
        return aToken;
    }

}
