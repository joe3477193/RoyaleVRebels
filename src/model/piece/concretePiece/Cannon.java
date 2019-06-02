package model.piece.concretePiece;

import model.piece.abstractType.Artillery;
import model.piece.faction.Royale;

public class Cannon extends Royale implements Artillery {

    private static final String NAME = "Cannon";

    private static final int CP = 10;
    private static final int HP = 50;
    private static final int DEFENCE = 10;
    private static final int ATTACK_POWER = 60;
    private static final int MOVE_SPEED = 0;
    private static final int ATTACK_RANGE = 6;

    public Cannon() {
        super(NAME, CP, HP, DEFENCE, ATTACK_POWER, MOVE_SPEED, ATTACK_RANGE, moveable, attackable);
    }
}
