package model.pieces;

import model.interfaces.Troop;

public class Angryman extends Rebel implements Troop {
    Angryman() {
        code = "AG";
        typeOf = type;
        cp = 3;
        initHp = 20;
        hp = initHp;
        attack = 50;
        mov = 4;
        range = 3;
        moveableOf = moveable;
        attackableOf = attackable;
    }
}
