package model.piece.decorator;

import model.piece.AbtractPiece.PieceInterface;

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