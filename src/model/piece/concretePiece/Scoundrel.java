package model.piece.concretePiece;

import model.piece.abstractType.Troop;
import model.piece.faction.Rebel;

public class Scoundrel extends Rebel implements Troop {

    private static final String NAME = "Scoundrel";

    private static final int CP = 3;
    private static final int HP = 30;
    private static final int DEFENCE = 10;
    private static final int ATTACK_POWER = 15;
    private static final int MOVE_SPEED = 6;
    private static final int ATTACK_RANGE = 1;

    public Scoundrel() {
        super(NAME, CP, HP, DEFENCE, ATTACK_POWER, MOVE_SPEED, ATTACK_RANGE, moveable, attackable);
    }
}
