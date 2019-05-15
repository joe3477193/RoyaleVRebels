package model.piece.concretePiece;

import model.piece.faction.Royale;
import model.piece.typeInterface.Troop;

public class Liutenant extends Royale implements Troop {
    public Liutenant() {
        super("Liutenant", type, "LT", 3, 50, 30, 2, 1, moveable, attackable);
    }
}
