package model.pieces.faction;

import model.pieces.Piece;

public class Royale extends Piece {

    public Royale(String type, String code, int cp, int initHp, int attack, int mov, int range,
                 boolean moveable, boolean attackable) {
        super("Royale", type, code, cp, initHp, attack, mov, range, moveable, attackable);
    }
}
