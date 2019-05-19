package model.piece.concretePiece;

import model.piece.abstractType.Troop;
import model.piece.faction.Rebel;

public class Rascal extends Rebel implements Troop {
    public Rascal() {
        super("Rascal", type, "RC", 3, 40, 10, 30, 3, 2, moveable, attackable);
    }
}
