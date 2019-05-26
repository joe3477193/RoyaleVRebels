package model.piece.decorator.concreteDecorator;

import model.piece.AbtractPiece.PieceInterface;
import model.piece.abstractType.Artillery;
import model.piece.decorator.abstractDecorator.PieceInterfaceDecorator;

public class AttackPowerBuffArtilleryDecorator extends PieceInterfaceDecorator implements Artillery {

    public AttackPowerBuffArtilleryDecorator(PieceInterface piece) {
        super(piece);
        buffAttackPower();
    }

    @Override
    public void buffAttackPower() {
        setAttackPower(piece.getAttackPower() + 50);
    }
}
