package model.piece.decorator;

import model.piece.AbtractPiece.PieceInterface;

public class ResetDecorator extends PieceInterfaceDecorator {

    public ResetDecorator(PieceInterface piece) {
        super(piece);
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