package model.piece.concretePiece;

import model.piece.abstractType.Troop;
import model.piece.faction.Rebel;

public class Scoundrel extends Rebel implements Troop {
    public Scoundrel() {
        super(3, 30, 10, 10, 6, 1, moveable, attackable);
    }
}
