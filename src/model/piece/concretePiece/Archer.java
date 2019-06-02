package model.piece.concretePiece;

import model.piece.abstractType.Troop;
import model.piece.faction.Rebel;

public class Archer extends Rebel implements Troop {

    private static final String NAME = "Archer";

    private static final int CP = 5;
    private static final int HP = 40;
    private static final int DEFENCE = 10;
    private static final int ATTACK_POWER = 30;
    private static final int MOVE_SPEED = 2;
    private static final int ATTACK_RANGE = 6;

    public Archer() {
        super(NAME, CP, HP, DEFENCE, ATTACK_POWER, MOVE_SPEED, ATTACK_RANGE, moveable, attackable);
    }
}
