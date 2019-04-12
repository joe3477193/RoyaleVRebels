package model.pieces;

import model.interfaces.Artillery;

public class Balista extends Royale implements Artillery {
    Balista() {
        code = "BA";
        typeOf = type;
        cp = 8;
        initHp = 40;
        hp = initHp;
        attack = 30;
        mov = 0;
        range = 8;
        moveableOf = moveable;
        attackableOf = attackable;
    }
}
