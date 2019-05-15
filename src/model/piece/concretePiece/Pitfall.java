package model.piece.concretePiece;

import model.piece.faction.Royale;
import model.piece.typeInterface.Obstacle;

public class Pitfall extends Royale implements Obstacle {
    public Pitfall() {
        super("Pitfall", type, "PF", 15, 1050, 0, 0, 0, moveable, attackable);
    }
}
