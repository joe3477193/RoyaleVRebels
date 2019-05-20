package model.piece.concretePiece;

import model.piece.abstractType.Troop;
import model.piece.faction.Royale;

public class Liutenant extends Royale implements Troop {
    public Liutenant() {
        super("Liutenant", type, "LT", 3, 50, 10, 30, 2, 1, moveable, attackable);
    }
}
