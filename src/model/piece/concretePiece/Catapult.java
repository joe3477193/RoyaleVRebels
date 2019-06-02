package model.piece.concretePiece;

import model.piece.abstractType.Artillery;
import model.piece.faction.Royale;

public class Catapult extends Royale implements Artillery {

    private static final String NAME = "Catapult";

    private static final int CP = 5;
    private static final int HP = 50;
    private static final int DEFENCE = 10;
    private static final int ATTACK_POWER = 20;
    private static final int MOVE_SPEED = 0;
    private static final int ATTACK_RANGE = 8;

    public Catapult() {
        super(NAME, CP, HP, DEFENCE, ATTACK_POWER, MOVE_SPEED, ATTACK_RANGE, moveable, attackable);
    }
}
