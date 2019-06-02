package model.piece.concretePiece;

import model.piece.abstractType.Troop;
import model.piece.faction.Royale;

public class Infantry extends Royale implements Troop {
    public Infantry() {
        super("Infantry", "IF", 3, 20, 10, 20, 3, 3, moveable, attackable);
    }
}
