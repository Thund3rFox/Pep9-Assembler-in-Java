package prob0719;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public final class Maps {
    public static final Map<String, Mnemon> unaryMnemonTable;
    public static final Map<String, Mnemon> nonUnaryMnemonTable;
    public static final Map<String, DCommand> endMnemonTable;
    public static final Map<DCommand, String> endStringTable;

    public static final Map<String, DCommand> blockMnemonTable;
    public static final Map<DCommand, String> blockStringTable;
    public static final Map<String, Mnemon> branchMnemonTable;
    public static final Map<Mnemon, String> branchStringTable;
    public static final Map<String, AddrMode> addressingModeTable;
    public static final Map<AddrMode, String> addressingModeStringTable;
    public static final Map<Mnemon, String> mnemonStringTable;

    public static final Map<Mnemon, Integer> unaryMnemonIntTable;
    public static final Map<Mnemon, Integer> mnemonIntTable;

    static {
        unaryMnemonTable = new HashMap<>();
        unaryMnemonTable.put("stop", Mnemon.M_STOP);
        unaryMnemonTable.put("asla", Mnemon.M_ASLA);
        unaryMnemonTable.put("asra", Mnemon.M_ASRA);

        endMnemonTable = new HashMap<>();
        endMnemonTable.put("end", DCommand.D_END);

        endStringTable = new HashMap<>();
        endStringTable.put(DCommand.D_END, ".END");

        blockMnemonTable = new HashMap<>();
        blockMnemonTable.put("block", DCommand.D_BLOCK);

        blockStringTable = new HashMap<>();
        blockStringTable.put(DCommand.D_BLOCK, ".BLOCK");

        branchMnemonTable = new HashMap<>();  //FIXME might be unnecessary?
        branchMnemonTable.put("br", Mnemon.M_BR);
        branchMnemonTable.put("brlt", Mnemon.M_BRLT);
        branchMnemonTable.put("breq", Mnemon.M_BREQ);
        branchMnemonTable.put("brle", Mnemon.M_BRLE);

        branchStringTable = new HashMap<>();  //FIXME might be unnecessary?
        branchStringTable.put(Mnemon.M_BR, "BR");
        branchStringTable.put(Mnemon.M_BRLT, "BRLT");
        branchStringTable.put(Mnemon.M_BREQ, "BREQ");
        branchStringTable.put(Mnemon.M_BRLE, "BRLE");

        addressingModeTable = new HashMap<>();
        addressingModeTable.put("i", AddrMode.AM_I);
        addressingModeTable.put("d", AddrMode.AM_D);
        addressingModeTable.put("n", AddrMode.AM_N);
        addressingModeTable.put("s", AddrMode.AM_S);
        addressingModeTable.put("sf", AddrMode.AM_SF);
        addressingModeTable.put("x", AddrMode.AM_X);
        addressingModeTable.put("sx", AddrMode.AM_SX);
        addressingModeTable.put("sfx", AddrMode.AM_SFX);


        addressingModeStringTable = new HashMap<>();
        addressingModeStringTable.put(AddrMode.AM_I,"i");
        addressingModeStringTable.put(AddrMode.AM_D,"d");
        addressingModeStringTable.put(AddrMode.AM_N,"n");
        addressingModeStringTable.put(AddrMode.AM_S,"s");
        addressingModeStringTable.put(AddrMode.AM_SF,"sf");
        addressingModeStringTable.put(AddrMode.AM_X,"x");
        addressingModeStringTable.put(AddrMode.AM_SX,"sx");
        addressingModeStringTable.put(AddrMode.AM_SFX,"sfx");

        nonUnaryMnemonTable = new HashMap<>();
        nonUnaryMnemonTable.put("br", Mnemon.M_BR);
        nonUnaryMnemonTable.put("brlt", Mnemon.M_BRLT);
        nonUnaryMnemonTable.put("breq", Mnemon.M_BREQ);
        nonUnaryMnemonTable.put("brle", Mnemon.M_BRLE);
        nonUnaryMnemonTable.put("cpwa", Mnemon.M_CPWA);
        nonUnaryMnemonTable.put("deci", Mnemon.M_DECI);
        nonUnaryMnemonTable.put("deco", Mnemon.M_DECO);
        nonUnaryMnemonTable.put("adda", Mnemon.M_ADDA);
        nonUnaryMnemonTable.put("suba", Mnemon.M_SUBA);
        nonUnaryMnemonTable.put("stwa", Mnemon.M_STWA);
        nonUnaryMnemonTable.put("ldwa", Mnemon.M_LDWA);

        mnemonStringTable = new EnumMap<>(Mnemon.class);
        mnemonStringTable.put(Mnemon.M_BR, "BR");
        mnemonStringTable.put(Mnemon.M_BRLT, "BRLT");
        mnemonStringTable.put(Mnemon.M_BREQ, "BREQ");
        mnemonStringTable.put(Mnemon.M_BRLE, "BRLE");
        mnemonStringTable.put(Mnemon.M_CPWA, "CPWA");
        mnemonStringTable.put(Mnemon.M_DECI, "DECI");
        mnemonStringTable.put(Mnemon.M_DECO, "DECO");
        mnemonStringTable.put(Mnemon.M_ADDA, "ADDA");
        mnemonStringTable.put(Mnemon.M_SUBA, "SUBA");
        mnemonStringTable.put(Mnemon.M_STWA, "STWA");
        mnemonStringTable.put(Mnemon.M_LDWA, "LDWA");
        mnemonStringTable.put(Mnemon.M_STOP, "STOP");
        mnemonStringTable.put(Mnemon.M_ASLA, "ASLA");
        mnemonStringTable.put(Mnemon.M_ASRA, "ASRA");
        mnemonStringTable.put(Mnemon.M_BLOCK, ".BLOCK");
        mnemonStringTable.put(Mnemon.M_END, ".END");
        mnemonStringTable.put(Mnemon.M_I, "i");
        mnemonStringTable.put(Mnemon.M_D, "d");
        mnemonStringTable.put(Mnemon.M_S, "s");

        unaryMnemonIntTable = new EnumMap<>(Mnemon.class);
        unaryMnemonIntTable.put(Mnemon.M_STOP, 0);
        unaryMnemonIntTable.put(Mnemon.M_ASLA, 10);
        unaryMnemonIntTable.put(Mnemon.M_ASRA, 12);

        mnemonIntTable = new EnumMap<>(Mnemon.class);
        mnemonIntTable.put(Mnemon.M_BR, 18);
        mnemonIntTable.put(Mnemon.M_BRLT, 22);
        mnemonIntTable.put(Mnemon.M_BREQ, 24);
        mnemonIntTable.put(Mnemon.M_BRLE, 20);
        mnemonIntTable.put(Mnemon.M_CPWA, 160);
        mnemonIntTable.put(Mnemon.M_DECI, 48);
        mnemonIntTable.put(Mnemon.M_DECO, 56);
        mnemonIntTable.put(Mnemon.M_ADDA, 96);
        mnemonIntTable.put(Mnemon.M_SUBA, 112);
        mnemonIntTable.put(Mnemon.M_STWA, 224);
        mnemonIntTable.put(Mnemon.M_LDWA, 192);



    }
}
