package model.pieces.type;

import model.pieces.faction.Royale;
import model.pieces.typeInterface.Artillery;

public class Cannon extends Royale implements Artillery {
    public Cannon() {
        super("Cannon",type, "CA", 10, 50,60,0,6, moveable, attackable);
    }
}
