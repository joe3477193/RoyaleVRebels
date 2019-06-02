package model.piece.concretePiece;

import model.piece.abstractType.Troop;
import model.piece.faction.Rebel;

public class Angryman extends Rebel implements Troop {

    private static final String NAME = "Angryman";

    private static final int CP = 3;
    private static final int HP = 150;
    private static final int DEFENCE = 10;
    private static final int ATTACK_POWER = 20;
    private static final int MOVE_SPEED = 2;
    private static final int ATTACK_RANGE = 1;

    public Angryman() {
        super(NAME, CP, HP, DEFENCE, ATTACK_POWER, MOVE_SPEED, ATTACK_RANGE, moveable, attackable);
    }
}
