package model.piece.concretePiece;

import model.piece.abstractType.Artillery;
import model.piece.faction.Royale;

public class Cannon extends Royale implements Artillery {
    public Cannon() {
        super(10, 50, 10, 60, 0, 6, moveable, attackable);
    }
}
