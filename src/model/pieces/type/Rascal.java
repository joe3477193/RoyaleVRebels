package model.pieces.type;

import model.pieces.faction.Rebel;
import model.pieces.typeInterface.Troop;

public class Rascal extends Rebel implements Troop {
    public Rascal() {
        super("Rascal", type, "RC", 3, 40, 30, 3, 2, moveable, attackable);
    }
}
