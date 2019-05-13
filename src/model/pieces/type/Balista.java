package model.pieces.type;

import model.pieces.faction.Royale;
import model.pieces.typeInterface.Artillery;

public class Balista extends Royale implements Artillery {
    public Balista() {
        super("Balista",type, "BA", 8, 40,20,0,7, moveable, attackable);
    }
}
