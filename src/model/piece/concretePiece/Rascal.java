package model.piece.concretePiece;

import model.piece.faction.Rebel;
import model.piece.typeInterface.Troop;

public class Rascal extends Rebel implements Troop {
    public Rascal() {
        super("Rascal", type, "RC", 3, 40, 30, 3, 2, moveable, attackable);
    }
}
