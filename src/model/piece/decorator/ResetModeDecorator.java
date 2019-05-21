package model.piece.decorator;

import model.piece.AbtractPiece.PieceInterface;

public class ResetModeDecorator extends PieceInterfaceDecorator {

    public ResetModeDecorator(PieceInterface piece) {
        super(piece);
        resetMode();
    }

    public void resetMode() {
        setAttackPower(piece.getInitAttackPower());
        setDefence(piece.getInitDefence());
        setAttackRange(piece.getInitAttackRange());
        setMoveSpeed(piece.getInitMoveSpeed());
        isOffensive = false;
        isDefensive = false;
    }
}