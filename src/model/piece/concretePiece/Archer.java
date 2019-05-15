package model.piece.concretePiece;

import model.piece.faction.Rebel;
import model.piece.typeInterface.Troop;

public class Archer extends Rebel implements Troop {
    public Archer() {
        super("Archer", type, "AC", 5, 40, 30, 2, 6, moveable, attackable);
    }
}
