package model.piece.faction;

import model.piece.AbtractPiece.Piece;

public abstract class Royale extends Piece {

    public Royale(String name, String type, String code, int cp, int hp, int defence, int attackPower, int moveSpeed, int attackRange,
                  boolean moveable, boolean attackable) {
        super(name, "Royale", type, code, cp, hp, defence, attackPower, moveSpeed, attackRange, moveable, attackable);
    }
}
