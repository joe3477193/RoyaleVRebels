package model.piece.decorator.concreteDecorator;

import model.piece.AbtractPiece.PieceInterface;
import model.piece.abstractType.Troop;
import model.piece.decorator.abstractDecorator.PieceInterfaceDecorator;

public class AttackPowerNerfTroopDecorator extends PieceInterfaceDecorator implements Troop {

    public AttackPowerNerfTroopDecorator(PieceInterface piece) {
        super(piece);
        nerfAttackPower();
    }

    @Override
    public void nerfAttackPower() {
        setAttackPower(piece.getAttackPower() - 20);
    }
}
