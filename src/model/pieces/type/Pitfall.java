package model.pieces.type;

import model.pieces.faction.Royale;
import model.pieces.typeInterface.Obstacle;

public class Pitfall extends Royale implements Obstacle {
    public Pitfall(){
        super("Pitfall",type, "PF", 15, 1050,0,0,0, moveable, attackable);
    }
}
