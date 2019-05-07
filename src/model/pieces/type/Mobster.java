package model.pieces.type;

import model.pieces.faction.Rebel;
import model.pieces.typeInterface.Troop;

public class Mobster extends Rebel implements Troop {
    public Mobster() {
        super("Mobster",type, "HB", 1, 40,20,2,3, moveable, attackable);
    }
}
