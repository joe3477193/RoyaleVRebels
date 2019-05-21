package model.piece.decorator.concreteDecorator;

import model.piece.AbtractPiece.PieceInterface;
import model.piece.abstractType.Troop;
import model.piece.decorator.abstractDecorator.PieceInterfaceDecorator;

public class AttackPowerBuffTroopDecorator extends PieceInterfaceDecorator implements Troop {

    public AttackPowerBuffTroopDecorator(PieceInterface piece) {
        super(piece);
        buffAttackPower();
    }

    @Override
    public void buffAttackPower() {
        setAttackPower(piece.getAttackPower() + 30);
    }
}
