package model.pieces.decorator;

import model.pieces.PieceInterface;

public class ResetDecorator extends PieceInterfaceDecorator {

    public ResetDecorator(PieceInterface piece) {
        super(piece);
    }

    public void resetStatus() {
        setAttackPower(piece.getInitAp());
        setHP(piece.getInitHp());
        setMoveSpeed(piece.getInitSpeed());
        isOffensive = false;
        isDefensive = false;
    }
}