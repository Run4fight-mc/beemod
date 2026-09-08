package com.run4fight.client.font;

public enum TextIcons {
    A_14("a", '\uE086'),
    B_14("b", '\uE087'),
    C_14("c", '\uE088'),
    D_14("d", '\uE089'),
    E_14("e", '\uE08A'),
    F_14("f", '\uE08B'),
    G_14("g", '\uE08C'),
    H_14("h", '\uE08D'),
    I_14("i", '\uE08E'),
    J_14("j", '\uE08F'),
    K_14("k", '\uE090'),
    L_14("l", '\uE091'),
    M_14("m", '\uE092'),
    N_14("n", '\uE093'),
    O_14("o", '\uE094'),
    P_14("p", '\uE095'),
    Q_14("q", '\uE096'),
    R_14("r", '\uE097'),
    S_14("s", '\uE098'),
    T_14("t", '\uE099'),
    U_14("u", '\uE09A'),
    V_14("v", '\uE09B'),
    W_14("w", '\uE09C'),
    X_14("x", '\uE09D'),
    Y_14("y", '\uE09E'),
    Z_14("z", '\uE09F'),

    A_17("a", '\uE0AD'),
    B_17("b", '\uE0AE'),
    C_17("c", '\uE0AF'),
    D_17("d", '\uE0B0'),
    E_17("e", '\uE0B1'),
    F_17("f", '\uE0B2'),
    G_17("g", '\uE0B3'),
    H_17("h", '\uE0B4'),
    I_17("i", '\uE0B5'),
    J_17("j", '\uE0B6'),
    K_17("k", '\uE0B7'),
    L_17("l", '\uE0B8'),
    M_17("m", '\uE0B9'),
    N_17("n", '\uE0BA'),
    O_17("o", '\uE0BB'),
    P_17("p", '\uE0BC'),
    Q_17("q", '\uE0BD'),
    R_17("r", '\uE0BE'),
    S_17("s", '\uE0BF'),
    T_17("t", '\uE0C0'),
    U_17("u", '\uE0C1'),
    V_17("v", '\uE0C2'),
    W_17("w", '\uE0C3'),
    X_17("x", '\uE0C4'),
    Y_17("y", '\uE0C5'),
    Z_17("z", '\uE0C6'),

    A_20("a", '\uE0D4'),
    B_20("b", '\uE0D5'),
    C_20("c", '\uE0D6'),
    D_20("d", '\uE0D7'),
    E_20("e", '\uE0D8'),
    F_20("f", '\uE0D9'),
    G_20("g", '\uE0DA'),
    H_20("h", '\uE0DB'),
    I_20("i", '\uE0DC'),
    J_20("j", '\uE0DD'),
    K_20("k", '\uE0DE'),
    L_20("l", '\uE0DF'),
    M_20("m", '\uE0E0'),
    N_20("n", '\uE0E1'),
    O_20("o", '\uE0E2'),
    P_20("p", '\uE0E3'),
    Q_20("q", '\uE0E4'),
    R_20("r", '\uE0E5'),
    S_20("s", '\uE0E6'),
    T_20("t", '\uE0E7'),
    U_20("u", '\uE0E8'),
    V_20("v", '\uE0E9'),
    W_20("w", '\uE0EA'),
    X_20("x", '\uE0EB'),
    Y_20("y", '\uE0EC'),
    Z_20("z", '\uE0ED');


    private final char icon;
    private final String character;

    TextIcons(String character, char icon) {
        this.icon = icon;
        this.character = character;
    }

    public char getIcon() {
        return icon;
    }

    public String getCharacter() {
        return character;
    }
}
