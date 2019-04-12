package model.pieces;

import model.interfaces.Troop;

public class Infantry extends Royale implements Troop {
    Infantry() {
        code = "IF";
        typeOf = type;
        cp = 5;
        initHp = 20;
        hp = initHp;
        attack = 20;
        mov = 3;
        range = 1;
        moveableOf = moveable;
        attackableOf = attackable;
    }
}
