package model.pieces;

import model.interfaces.Artillery;

public class Catapult extends Rebel implements Artillery {
    public Catapult() {
        code = "CT";
        typeOf = type;
        cp = 5;
        initHp = 50;
        hp = initHp;
        attack = 20;
        mov = 0;
        range = 8;
        moveableOf = moveable;
        attackableOf = attackable;
    }
}
