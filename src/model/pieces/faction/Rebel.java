package model.pieces.faction;

import model.pieces.Piece;

public class Rebel extends Piece {

    public Rebel(String name, String type, String code, int cp, int initHp, int attack, int mov, int range,
                 boolean moveable, boolean attackable) {
        super(name,"Rebel", type, code, cp, initHp, attack, mov, range, moveable, attackable);
    }
}