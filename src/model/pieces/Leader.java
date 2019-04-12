package model.pieces;

import model.interfaces.Troop;

public class Leader extends Rebel implements Troop {
    public Leader() {
        code = "LD";
        typeOf = type;
        cp = 5;
        initHp = 50;
        hp = initHp;
        attack = 30;
        mov = 1;
        range = 1;
        moveableOf = moveable;
        attackableOf = attackable;
    }
}
