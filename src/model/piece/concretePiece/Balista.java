package model.piece.concretePiece;

import model.piece.faction.Royale;
import model.piece.typeInterface.Artillery;

public class Balista extends Royale implements Artillery {
    public Balista() {
        super("Balista", type, "BA", 8, 40, 20, 0, 7, moveable, attackable);
    }
}
