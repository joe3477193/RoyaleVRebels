package model.piece.decorator;

import model.piece.AbtractPiece.PieceInterface;

public class AttackRangeBuffDecorator extends PieceInterfaceDecorator {

    public AttackRangeBuffDecorator(PieceInterface piece) {
        super(piece);
        buffAttackRange();
    }

    @Override
    public void buffAttackRange() {
        setAttackRange(piece.getAttackRange() + 1);
    }
}
