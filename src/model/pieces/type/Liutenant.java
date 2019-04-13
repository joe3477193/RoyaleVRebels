package model.pieces.type;

import model.pieces.faction.Royale;
import model.pieces.typeInterface.Troop;

public class Liutenant extends Royale implements Troop {
    public Liutenant() {
        super("Liutenant",type, "LT", 3, 30,30,2,1, moveable, attackable);
    }
}
