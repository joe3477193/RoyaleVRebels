package model.piece.concretePiece;

import model.piece.faction.Rebel;
import model.piece.typeInterface.Troop;

public class Angryman extends Rebel implements Troop {

    public Angryman() {
        super("Angryman", type, "AG", 3, 150, 20, 2, 1, moveable, attackable);
    }
}
