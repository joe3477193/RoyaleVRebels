package model.pieces;

import model.interfaces.Troop;

public class Liutenant extends Royale implements Troop {
    public Liutenant() {
        code = "LT";
        typeOf = type;
        cp = 3;
        initHp = 30;
        hp = initHp;
        attack = 30;
        mov = 2;
        range = 1;
        moveableOf = moveable;
        attackableOf = attackable;
    }
}
