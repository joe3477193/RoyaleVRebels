package model.piece.concretePiece;

import model.piece.faction.Royale;
import model.piece.typeInterface.Artillery;

public class Cannon extends Royale implements Artillery {
    public Cannon() {
        super("Cannon", type, "CA", 10, 50, 60, 0, 6, moveable, attackable);
    }
}
