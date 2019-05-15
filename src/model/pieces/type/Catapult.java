package model.pieces.type;

import model.pieces.faction.Royale;
import model.pieces.typeInterface.Artillery;

public class Catapult extends Royale implements Artillery {
    public Catapult() {
        super("Catapult", type, "CT", 5, 50, 20, 0, 8, moveable, attackable);
    }
}
