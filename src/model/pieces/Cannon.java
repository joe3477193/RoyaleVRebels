package model.pieces;

import model.interfaces.Artillery;

public class Cannon extends Royale implements Artillery {
    Cannon() {
        code = "CA";
        typeOf = type;
        cp = 10;
        initHp = 50;
        hp = initHp;
        attack = 50;
        mov = 0;
        range = 10;
        moveableOf = moveable;
        attackableOf = attackable;
    }
}
