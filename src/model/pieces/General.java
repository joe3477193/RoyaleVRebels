package model.pieces;

import model.interfaces.Troop;

public class General extends Royale implements Troop {
    public General() {
        code = "GN";
        typeOf = type;
        cp = 5;
        initHp = 50;
        hp = initHp;
        attack = 50;
        mov = 1;
        range = 1;
        moveableOf = moveable;
        attackableOf = attackable;
    }
}
