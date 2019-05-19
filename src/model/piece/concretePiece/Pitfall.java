package model.piece.concretePiece;

import model.piece.abstractType.Obstacle;
import model.piece.faction.Royale;

public class Pitfall extends Royale implements Obstacle {
    public Pitfall() {
        super("Pitfall", type, "PF", 15, 1050, 0, 0, 0, moveable, attackable);
    }
}
