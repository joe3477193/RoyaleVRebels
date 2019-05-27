package model.piece.concretePiece;

import model.piece.abstractType.Troop;
import model.piece.faction.Rebel;

public class Angryman extends Rebel implements Troop {

    public Angryman() {
        super(3, 150, 10, 20, 2, 1, moveable, attackable);
    }
}
