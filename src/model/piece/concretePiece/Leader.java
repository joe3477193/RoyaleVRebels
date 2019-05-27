package model.piece.concretePiece;

import model.piece.abstractType.Troop;
import model.piece.faction.Rebel;

public class Leader extends Rebel implements Troop {
    public Leader() {
        super(5, 60, 10, 30, 2, 2, moveable, attackable);
    }
}
