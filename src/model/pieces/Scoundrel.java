package model.pieces;

import model.interfaces.Troop;

public class Scoundrel extends Rebel implements Troop {
    Scoundrel() {
        code = "SC";
        typeOf = type;
        cp = 3;
        initHp = 100;
        hp = initHp;
        attack = 10;
        mov = 1;
        range = 1;
        moveableOf = moveable;
        attackableOf = attackable;
    }
}
