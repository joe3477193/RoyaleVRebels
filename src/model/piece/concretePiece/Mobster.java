package model.piece.concretePiece;

import model.piece.abstractType.Troop;
import model.piece.faction.Rebel;

public class Mobster extends Rebel implements Troop {
    public Mobster() {
        super("Mobster", type, "HB", 1, 40, 10, 20, 2, 3, moveable, attackable);
    }
}
