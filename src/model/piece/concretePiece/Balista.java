package model.piece.concretePiece;

import model.piece.abstractType.Artillery;
import model.piece.faction.Royale;

public class Balista extends Royale implements Artillery {

    private static final String NAME = "Balista";

    private static final int CP = 8;
    private static final int HP = 40;
    private static final int DEFENCE = 10;
    private static final int ATTACK_POWER = 20;
    private static final int MOVE_SPEED = 0;
    private static final int ATTACK_RANGE = 7;

    public Balista() {
        super(NAME, CP, HP, DEFENCE, ATTACK_POWER, MOVE_SPEED, ATTACK_RANGE, moveable, attackable);
    }
}
