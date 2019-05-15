package model.pieces.type;

import model.pieces.faction.Rebel;
import model.pieces.typeInterface.Troop;

public class Angryman extends Rebel implements Troop {

    public Angryman() {
        super("Angryman", type, "AG", 3, 150, 20, 2, 1, moveable, attackable);
    }
}
