package model.piece.concretePiece;

import model.piece.faction.Royale;
import model.piece.typeInterface.Troop;

public class General extends Royale implements Troop {
    public General() {
        super("General", type, "GN", 5, 60, 40, 2, 1, moveable, attackable);
    }
}
