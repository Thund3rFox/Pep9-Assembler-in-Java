package prob0719;

import java.util.ArrayList;

public class Translator {
    private final InBuffer b;
    private Tokenizer t;
    private ACode aCode;

    public Translator(InBuffer inBuffer) {
        b = inBuffer;
    }

    // Sets aCode and returns boolean true if end statement is processed.
    private boolean parseLine() {
        boolean terminate = false;
        AArg localIntArg = new IntArg(0);
        AArg localHexArg = new HexArg(0);
        AArg localSpecifierArg = new IntArg(0);
        // Compiler requires following useless initialization.
        DCommand localDCommand = DCommand.D_END;
        Mnemon localMnemon = Mnemon.M_STOP;
        AddrMode localAddrMode = AddrMode.AM_I;
        AToken aToken;
        aCode = new EmptyInstr();
        ParseState state = ParseState.PS_START;

        do {
            aToken = t.getToken();
            switch (state) {
                case PS_START:
                    if (aToken instanceof TIdentifier) {
                        TIdentifier localTIdentifier = (TIdentifier) aToken;
                        String tempStr = localTIdentifier.getStringValue();
                        if (Maps.unaryMnemonTable.containsKey(tempStr.toLowerCase())) {
                            localMnemon = Maps.unaryMnemonTable.get(tempStr.toLowerCase());
                            aCode = new UnaryInstr(localMnemon);
                            state = ParseState.PS_Unary;
                        } else if (Maps.nonUnaryMnemonTable.containsKey(tempStr.toLowerCase())) {
                            localMnemon = Maps.nonUnaryMnemonTable.get(tempStr.toLowerCase());
                            state = ParseState.PS_NonUnary;
                        } else {
                            aCode = new Error(
                                    "Line must begin with mnemonic.");
                        }
                    } else if (aToken instanceof TDot) {
                        TDot localTDot = (TDot) aToken;
                        String tempStr = localTDot.getStringValue();
                        if (Maps.endMnemonTable.containsKey(tempStr.toLowerCase())) {
                            localDCommand = Maps.endMnemonTable.get(tempStr.toLowerCase());
                            aCode = new DEnd(localDCommand);
                            terminate = (localDCommand == DCommand.D_END);
                            state = ParseState.PS_End;
                        } else if (Maps.blockMnemonTable.containsKey(tempStr.toLowerCase())) {
                            localDCommand = Maps.blockMnemonTable.get(tempStr.toLowerCase());
                            state = ParseState.PS_Block;
                        } else {
                            aCode = new Error(
                                    "Dot command was invalid."); //FIXME Failing here
                        }
                    } else if (aToken instanceof TEmpty) {
                        aCode = new EmptyInstr();
                        state = ParseState.PS_FINISH;
                    } else {
                        aCode = new Error("Dot command was invalid.");
                    }
                    break;

                case PS_Block:
                    if (aToken instanceof TInteger) {
                        TInteger localTInteger = (TInteger) aToken;
                        localIntArg = new IntArg(localTInteger.getIntValue());
                        aCode = new DotBlock(localDCommand, localIntArg);
                        state = ParseState.PS_Block2;
                    } else if (aToken instanceof THex) {
                        THex localTHex = (THex) aToken;
                        localHexArg = new HexArg(localTHex.getHexValue());
                        aCode = new DotBlock(localDCommand, localHexArg);
                        state = ParseState.PS_Block2;
                    } else if (aToken instanceof TInvalid) {
                        aCode = new Error("Invalid int or hex.");
                    } else {
                        aCode = new Error("Expected int or hex specifier.");
                    }
                    break;

                case PS_NonUnary:
                    if (aToken instanceof TInteger) {
                        TInteger localTInteger = (TInteger) aToken;
                        localIntArg = new IntArg(localTInteger.getIntValue());
                        aCode = new NonUnaryA(localMnemon, localIntArg, localAddrMode);
                        localSpecifierArg = localIntArg;
                        state = ParseState.PS_NonUnaryNoA;
                    } else if (aToken instanceof THex) {
                        THex tempTHex = (THex) aToken;
                        localHexArg = new HexArg(tempTHex.getHexValue());
                        aCode = new NonUnaryA(localMnemon, localHexArg, localAddrMode);
                        localSpecifierArg = localHexArg;
                        state = ParseState.PS_NonUnaryNoA;
                    } else if (aToken instanceof TInvalid){
                        aCode = new Error ("Invalid int or hex input.");
                }else {
                        aCode = new Error(
                                "Mnemonic was expected.");
                    }
                    break;
                case PS_NonUnaryNoA:
                    boolean isBR = (Maps.branchStringTable.containsKey(localMnemon));
                    if (aToken instanceof TAddress) {
                        TAddress localTAddressingMode = (TAddress) aToken;
                        String tempStr = localTAddressingMode.getStringValue();
                        if (Maps.addressingModeTable.containsKey(tempStr.toLowerCase())) {
                            localAddrMode = Maps.addressingModeTable.get(tempStr.toLowerCase());
                            aCode = new AddressInstr(localMnemon, localSpecifierArg, localAddrMode);
                            if ((localAddrMode == AddrMode.AM_I) && (localMnemon == Mnemon.M_DECI)) {
                                aCode = new Error("Invalid addressing mode for DECI instruction.");
                            }
                            if ((localAddrMode == AddrMode.AM_I) && (localMnemon == Mnemon.M_STWA)) {
                                aCode = new Error("Invalid addressing mode for STWA instruction.");
                            }
                            if (!((localAddrMode == AddrMode.AM_I) || (localAddrMode == AddrMode.AM_X)) && isBR) {
                                aCode = new Error("Invalid addressing mode for BR instruction.");
                            }
                            if ((localAddrMode == AddrMode.AM_I) && isBR) {
                                aCode = new NonUnaryA(localMnemon, localSpecifierArg, localAddrMode);
                            }
                            state = ParseState.PS_NonUnaryA;
                        } else {
                            aCode = new Error("Invalid addressing mode.");
                        }
                    } else if (aToken instanceof TEmpty) {
                        if (isBR) {
                            state = ParseState.PS_FINISH;
                        } else {
                            aCode = new Error("Expected an addressing mode to follow.");
                        }
                    } else {
                        aCode = new Error("Invalid trailing characters.");
                    }
                    break;
                case PS_NonUnaryA:
                case PS_Block2:
                case PS_Unary:
                case PS_End:
                    if (aToken instanceof TEmpty) {
                        state = ParseState.PS_FINISH;
                    } else {
                        aCode = new Error(
                                "Illegal trailing character after instruction.");
                    }
                    break;
            }
        } while (state != ParseState.PS_FINISH && !(aCode instanceof Error));
        return terminate;
    }


    public void translate() {
        ArrayList<ACode> codeTable = new ArrayList<>();
        int numErrors = 0;
        t = new Tokenizer(b);
        boolean terminateWithEnd = false;
        b.getLine();
        while (b.inputRemains() && !terminateWithEnd) {
            terminateWithEnd = parseLine(); // Sets aCode and returns boolean.
            codeTable.add(aCode);
            if (aCode instanceof Error) {
                numErrors++;
            }
            b.getLine();
        }
        if (!terminateWithEnd) {
            aCode = new Error("Missing \"end\" sentinel.");
            codeTable.add(aCode);
            numErrors++;
        }
        if (numErrors == 0) {
            System.out.printf("Object code:\n");
            for (int i = 0; i < codeTable.size(); i++) {
                System.out.printf("%s", codeTable.get(i).generateCode());
            }
        }
        if (numErrors == 1) {
            System.out.printf("One error was detected.\n");
        } else if (numErrors > 1) {
            System.out.printf("%d errors were detected.\n", numErrors);
        }
        System.out.printf("\nProgram listing:\n");
        for (int i = 0; i < codeTable.size(); i++) {
            System.out.printf("%s", codeTable.get(i).generateListing());
        }
    }
}