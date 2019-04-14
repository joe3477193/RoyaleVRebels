package model.pieces.type;

import model.pieces.faction.Royale;
import model.pieces.typeInterface.Troop;

public class General extends Royale implements Troop {
    public General() {
        super("General",type, "GN", 5, 60,40,2,1, moveable, attackable);
    }
}
