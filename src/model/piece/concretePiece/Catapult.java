package model.piece.concretePiece;

import model.piece.abstractType.Artillery;
import model.piece.faction.Royale;

public class Catapult extends Royale implements Artillery {
    public Catapult() {
        super(5, 50, 10, 20, 0, 8, moveable, attackable);
    }
}
