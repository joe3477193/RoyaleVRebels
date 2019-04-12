package model.pieces;

import model.interfaces.Troop;

public class Archer extends Royale implements Troop {
    Archer() {
        code = "AC";
        typeOf = type;
        cp = 5;
        initHp = 50;
        hp = initHp;
        attack = 30;
        mov = 2;
        range = 6;
        moveableOf = moveable;
        attackableOf = attackable;
    }
}
