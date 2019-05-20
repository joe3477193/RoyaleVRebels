package model.piece.decorator;

import model.piece.AbtractPiece.PieceInterface;

public class SetOffensiveDecorator extends PieceInterfaceDecorator {

    public SetOffensiveDecorator(PieceInterface piece) {
        super(piece);
    }

    public void setOffensive() {
        super.setOffensive();
        setAttackPower(piece.getInitAttackPower() + 30);
        setDefence(piece.getInitDefence() - 10);
        setAttackRange(piece.getInitAttackRange() + 1);
        setMoveSpeed(piece.getInitMoveSpeed() - 1);
        isOffensive = true;
    }
}
