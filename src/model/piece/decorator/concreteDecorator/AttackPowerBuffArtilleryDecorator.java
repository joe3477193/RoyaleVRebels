package model.piece.decorator.concreteDecorator;

import model.piece.AbtractPiece.PieceInterface;
import model.piece.abstractType.Artillery;
import model.piece.decorator.abstractDecorator.PieceInterfaceDecorator;

public class AttackPowerBuffArtilleryDecorator extends PieceInterfaceDecorator implements Artillery {

    private static final int BUFF_ARTILLERY_AP = 50;

    public AttackPowerBuffArtilleryDecorator(PieceInterface piece) {
        super(piece);
        buffAttackPower();
    }

    @Override
    public void buffAttackPower() {
        setAttackPower(piece.getAttackPower() + BUFF_ARTILLERY_AP);
    }
}
