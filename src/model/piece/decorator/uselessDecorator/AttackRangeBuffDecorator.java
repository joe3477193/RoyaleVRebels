package model.piece.decorator.uselessDecorator;

import model.piece.AbtractPiece.PieceInterface;
import model.piece.decorator.abstractDecorator.PieceInterfaceDecorator;

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
