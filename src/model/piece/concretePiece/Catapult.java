package model.piece.concretePiece;

import model.piece.faction.Royale;
import model.piece.typeInterface.Artillery;

public class Catapult extends Royale implements Artillery {
    public Catapult() {
        super("Catapult", type, "CT", 5, 50, 20, 0, 8, moveable, attackable);
    }
}
