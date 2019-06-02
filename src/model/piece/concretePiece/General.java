package model.piece.concretePiece;

import model.piece.abstractType.Troop;
import model.piece.faction.Royale;

public class General extends Royale implements Troop {
    public General() {
        super("General", "GN", 5, 60, 10, 40, 2, 1, moveable, attackable);
    }
}
