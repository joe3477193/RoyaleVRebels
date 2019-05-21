package model.piece.decorator.uselessDecorator;

import model.piece.AbtractPiece.PieceInterface;
import model.piece.decorator.abstractDecorator.PieceInterfaceDecorator;

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
