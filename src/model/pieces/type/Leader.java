package model.pieces.type;

import model.pieces.faction.Rebel;
import model.pieces.typeInterface.Troop;

public class Leader extends Rebel implements Troop {
    public Leader() {
        super(type, "LD", 5, 50,30,1,1, moveable, attackable);
    }
}
