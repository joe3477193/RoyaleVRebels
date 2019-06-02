package model.piece.concretePiece;

import model.piece.abstractType.Artillery;
import model.piece.faction.Royale;

public class Balista extends Royale implements Artillery {
    public Balista() {
        super("Balista", "BA", 8, 40, 10, 20, 0, 7, moveable, attackable);
    }
}
