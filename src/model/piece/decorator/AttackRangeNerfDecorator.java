package model.piece.decorator;

import model.piece.AbtractPiece.PieceInterface;

public class AttackRangeNerfDecorator extends PieceInterfaceDecorator {

    public AttackRangeNerfDecorator(PieceInterface piece) {
        super(piece);
        nerfAttackRange();
    }

    @Override
    public void nerfAttackRange() {
        setAttackRange(piece.getAttackRange() - 1);
    }
}
