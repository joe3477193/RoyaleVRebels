package model.piece.concretePiece;

import model.piece.faction.Rebel;
import model.piece.typeInterface.Obstacle;

public class Boulder extends Rebel implements Obstacle {
    public Boulder() {
        super("Boulder", type, "BD", 3, 150, 0, 0, 0, moveable, attackable);
    }
}
