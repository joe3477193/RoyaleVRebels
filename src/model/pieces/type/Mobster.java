package model.pieces.type;

import model.pieces.faction.Rebel;
import model.pieces.typeInterface.Troop;

public class Mobster extends Rebel implements Troop {
    public Mobster() {
        super("Mobster",type, "HB", 1, 20,20,3,1, moveable, attackable);
    }
}
