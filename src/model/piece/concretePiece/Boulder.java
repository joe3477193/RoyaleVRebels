package model.piece.concretePiece;

import model.piece.abstractType.Obstacle;
import model.piece.faction.Rebel;

public class Boulder extends Rebel implements Obstacle {

    private static final String NAME = "Boulder";

    private static final int CP = 3;
    private static final int HP = 150;
    private static final int DEFENCE = 10;
    private static final int ATTACK_POWER = 0;
    private static final int MOVE_SPEED = 0;
    private static final int ATTACK_RANGE = 0;

    public Boulder() {
        super(NAME, CP, HP, DEFENCE, ATTACK_POWER, MOVE_SPEED, ATTACK_RANGE, moveable, attackable);
    }
}
