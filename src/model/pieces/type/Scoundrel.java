package model.pieces.type;

import model.pieces.faction.Rebel;
import model.pieces.typeInterface.Troop;

public class Scoundrel extends Rebel implements Troop {
    public Scoundrel() {
        super("Scoundrel",type, "SC", 3, 100,10,1,1, moveable, attackable);
    }
}
