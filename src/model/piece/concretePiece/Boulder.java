package model.piece.concretePiece;

import model.piece.abstractType.Obstacle;
import model.piece.faction.Rebel;

public class Boulder extends Rebel implements Obstacle {
    public Boulder() {
        super("Boulder", "BD", 3, 150, 10, 0, 0, 0, moveable, attackable);
    }
}
