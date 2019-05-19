package model.piece.concretePiece;

import model.piece.abstractType.Troop;
import model.piece.faction.Rebel;

public class Leader extends Rebel implements Troop {
    public Leader() {
        super("Leader", type, "LD", 5, 60, 30, 2, 2, moveable, attackable);
    }
}
