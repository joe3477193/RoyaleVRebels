package model.pieces.type;

import model.pieces.faction.Royale;
import model.pieces.typeInterface.Troop;

public class Infantry extends Royale implements Troop {
    public Infantry() {
        super("Infantry",type, "IF", 3, 20,20,3,3, moveable, attackable);
    }
}
