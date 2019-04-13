package model.pieces.type;

import model.pieces.faction.Royale;
import model.pieces.typeInterface.Artillery;

public class Cannon extends Royale implements Artillery {
    public Cannon() {
        super(type, "CA", 10, 50,50,0,10, moveable, attackable);
    }
}
