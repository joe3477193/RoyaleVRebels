package model.piece.concretePiece;

import model.piece.faction.Rebel;
import model.piece.typeInterface.Troop;

public class Leader extends Rebel implements Troop {
    public Leader() {
        super("Leader", type, "LD", 5, 60, 30, 2, 2, moveable, attackable);
    }
}
