package model.pieces;

import model.interfaces.Troop;

public class Rascal extends Rebel implements Troop {
    Rascal() {
        code = "RC";
        typeOf = type;
        cp = 3;
        initHp = 40;
        hp = initHp;
        attack = 30;
        mov = 1;
        range = 6;
        moveableOf = moveable;
        attackableOf = attackable;
    }
}
