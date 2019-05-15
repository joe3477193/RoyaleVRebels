package model.piece.concretePiece;

import model.piece.faction.Rebel;
import model.piece.typeInterface.Troop;

public class Scoundrel extends Rebel implements Troop {
    public Scoundrel() {
        super("Scoundrel", type, "SC", 3, 30, 10, 6, 1, moveable, attackable);
    }
}
