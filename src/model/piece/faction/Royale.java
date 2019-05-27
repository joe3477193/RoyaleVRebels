package model.piece.faction;

import model.piece.AbtractPiece.Piece;

public abstract class Royale extends Piece {

    public Royale(int cp, int hp, int defence, int attackPower, int moveSpeed, int attackRange,
                  boolean moveable, boolean attackable) {
        super(cp, hp, defence, attackPower, moveSpeed, attackRange, moveable, attackable);
    }
}
