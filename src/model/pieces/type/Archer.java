package model.pieces.type;

import model.pieces.faction.Rebel;
import model.pieces.typeInterface.Troop;

public class Archer extends Rebel implements Troop {
    public Archer() {
        super("Archer",type, "AC", 5, 40,30,2,6, moveable, attackable);
    }
}
