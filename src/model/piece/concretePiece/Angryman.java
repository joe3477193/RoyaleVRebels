package model.piece.concretePiece;

import model.piece.abstractType.Troop;
import model.piece.faction.Rebel;

// TODO: Magic nums in concrete pieces
public class Angryman extends Rebel implements Troop {

    public Angryman() {
        super("Angryman", "AG", 3, 150, 10, 20, 2, 1, moveable, attackable);
    }
}
