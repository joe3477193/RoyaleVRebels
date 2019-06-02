package model.piece.faction;

import model.piece.AbtractPiece.Piece;

public abstract class Rebel extends Piece {

    private static final String FACTION = "Rebel";

    public Rebel(String name, String code, int cp, int hp, int defence, int attackPower, int moveSpeed, int attackRange, boolean moveable, boolean attackable) {
        super(name, FACTION, code, cp, hp, defence, attackPower, moveSpeed, attackRange, moveable, attackable);
    }
}
