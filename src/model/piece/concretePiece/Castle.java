package model.piece.concretePiece;

import model.piece.abstractType.property.UnAttackable;
import model.piece.abstractType.property.UnMoveable;
import model.piece.faction.Royale;

public class Castle extends Royale implements UnMoveable, UnAttackable {

    private static final String NAME = "Castle";

    private static final int CP = 0;
    private static final int HP = 500;
    private static final int DEFENCE = 0;
    private static final int ATTACK_POWER = 0;
    private static final int MOVE_SPEED = 0;
    private static final int ATTACK_RANGE = 0;

    public Castle() {
        super(NAME, CP, HP, DEFENCE, ATTACK_POWER, MOVE_SPEED, ATTACK_RANGE, moveable, attackable);
    }
}
