package model.pieces;

import model.interfaces.Troop;

public class Mobster extends Rebel implements Troop {
    Mobster() {
        code = "MB";
        typeOf = type;
        cp = 1;
        initHp = 20;
        hp = initHp;
        attack = 20;
        mov = 3;
        range = 1;
        moveableOf = moveable;
        attackableOf = attackable;
    }
}
