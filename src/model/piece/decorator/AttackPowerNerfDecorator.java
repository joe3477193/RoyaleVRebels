package model.piece.decorator;

import model.piece.AbtractPiece.PieceInterface;

public class AttackPowerNerfDecorator extends PieceInterfaceDecorator {

    public AttackPowerNerfDecorator(PieceInterface piece) {
        super(piece);
        nerfAttackPower();
    }

    @Override
    public void nerfAttackPower() {
        setAttackPower(piece.getAttackPower() - 20);
    }
}
