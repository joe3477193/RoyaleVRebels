package model.piece.concretePiece;

import model.piece.abstractType.Troop;
import model.piece.faction.Royale;

public class Liutenant extends Royale implements Troop {

    private static final String NAME = "Liutenant";

    private static final int CP = 3;
    private static final int HP = 50;
    private static final int DEFENCE = 10;
    private static final int ATTACK_POWER = 30;
    private static final int MOVE_SPEED = 2;
    private static final int ATTACK_RANGE = 1;

    public Liutenant() {
        super(NAME, CP, HP, DEFENCE, ATTACK_POWER, MOVE_SPEED, ATTACK_RANGE, moveable, attackable);
    }
}
