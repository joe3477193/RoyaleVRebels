package model.piece.concretePiece;

import model.piece.abstractType.Troop;
import model.piece.faction.Royale;

public class Infantry extends Royale implements Troop {

    private static final String NAME = "Infantry";

    private static final int CP = 3;
    private static final int HP = 20;
    private static final int DEFENCE = 10;
    private static final int ATTACK_POWER = 20;
    private static final int MOVE_SPEED = 3;
    private static final int ATTACK_RANGE = 3;

    public Infantry() {
        super(NAME, CP, HP, DEFENCE, ATTACK_POWER, MOVE_SPEED, ATTACK_RANGE, moveable, attackable);
    }
}
