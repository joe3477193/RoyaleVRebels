package model.pieces.type;

import model.pieces.faction.Rebel;
import model.pieces.typeInterface.Artillery;

public class Catapult extends Rebel implements Artillery {
    public Catapult() {
        super("Catapult",type, "CT", 5, 50,20,0,8, moveable, attackable);
    }
}
