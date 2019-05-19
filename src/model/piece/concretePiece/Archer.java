package model.piece.concretePiece;

import model.piece.abstractType.Troop;
import model.piece.faction.Rebel;

public class Archer extends Rebel implements Troop {
    public Archer() {
        super("Archer", type, "AC", 5, 40, 10, 30, 2, 6, moveable, attackable);
    }
}
