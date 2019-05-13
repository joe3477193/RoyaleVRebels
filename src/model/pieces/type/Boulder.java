package model.pieces.type;

import model.pieces.faction.Rebel;
import model.pieces.typeInterface.Obstacle;

public class Boulder extends Rebel implements Obstacle {
    public Boulder() {
        super("Boulder",type, "BD", 3, 150,0,0,0, moveable, attackable);
    }
}
