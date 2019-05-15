package model.pieces.type;

import model.pieces.faction.Rebel;
import model.pieces.typeInterface.Troop;

public class Leader extends Rebel implements Troop {
    public Leader() {
        super("Leader", type, "LD", 5, 60, 30, 2, 2, moveable, attackable);
    }
}
