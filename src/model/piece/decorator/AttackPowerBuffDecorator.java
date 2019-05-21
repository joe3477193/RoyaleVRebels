package model.piece.decorator;

import model.piece.AbtractPiece.PieceInterface;

public class AttackPowerBuffDecorator extends PieceInterfaceDecorator {

    public AttackPowerBuffDecorator(PieceInterface piece) {
        super(piece);
        buffAttackPower();
    }

    @Override
    public void buffAttackPower() {
        setAttackPower(piece.getAttackPower() + 30);
    }
}
