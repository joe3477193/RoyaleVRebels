package model.pieces.type;

import model.pieces.faction.Royale;
import model.pieces.typeInterface.Troop;

public class General extends Royale implements Troop {
    public General() {
        super(type, "GN", 5, 50,50,1,1, moveable, attackable);
    }
}
