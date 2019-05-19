package model.piece.decorator;

import model.piece.AbtractPiece.PieceInterface;

public class SetDefensiveDecorator extends PieceInterfaceDecorator {

    public SetDefensiveDecorator(PieceInterface piece) {
        super(piece);
    }

    public void setDefensive() {
        super.setDefensive();
        setAttackPower(piece.getInitAttackPower() - 20);
        setDefence(piece.getInitDefence() + 30);
        setAttackRange(piece.getInitAttackRange() - 1);
        setMoveSpeed(piece.getInitMoveSpeed() + 1);
        isDefensive = true;
    }
}