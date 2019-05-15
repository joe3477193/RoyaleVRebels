package model.piece.concretePiece;

import model.piece.faction.Rebel;
import model.piece.typeInterface.Troop;

public class Mobster extends Rebel implements Troop {
    public Mobster() {
        super("Mobster", type, "HB", 1, 40, 20, 2, 3, moveable, attackable);
    }
}
