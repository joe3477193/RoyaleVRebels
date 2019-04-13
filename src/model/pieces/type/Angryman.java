package model.pieces.type;

import model.pieces.faction.Rebel;
import model.pieces.typeInterface.Troop;

public class Angryman extends Rebel implements Troop {

    public Angryman() {
        super(type, "AG", 3, 20,50,4,3, moveable, attackable);
    }
}
