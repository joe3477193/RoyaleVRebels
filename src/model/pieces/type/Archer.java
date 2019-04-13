package model.pieces.type;

import model.pieces.faction.Royale;
import model.pieces.typeInterface.Troop;

public class Archer extends Royale implements Troop {
    public Archer() {
        super("Archer",type, "AC", 5, 50,30,2,6, moveable, attackable);
    }
}
