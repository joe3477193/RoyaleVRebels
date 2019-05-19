package model.piece.concretePiece;

import model.piece.faction.Royale;
import model.piece.typeInterface.Troop;

public class Infantry extends Royale implements Troop {
    public Infantry() {
        super("Infantry", type, "IF", 3, 20, 20, 3, 3, moveable, attackable);
    }
}
