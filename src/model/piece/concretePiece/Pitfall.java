package model.piece.concretePiece;

import model.piece.abstractType.Obstacle;
import model.piece.faction.Royale;

public class Pitfall extends Royale implements Obstacle {

    private static final String NAME = "Pitfall";

    private static final int CP = 15;
    private static final int HP = 1050;
    private static final int DEFENCE = 10;
    private static final int ATTACK_POWER = 0;
    private static final int MOVE_SPEED = 0;
    private static final int ATTACK_RANGE = 0;

    public Pitfall() {
        super(NAME, CP, HP, DEFENCE, ATTACK_POWER, MOVE_SPEED, ATTACK_RANGE, moveable, attackable);
    }
}
