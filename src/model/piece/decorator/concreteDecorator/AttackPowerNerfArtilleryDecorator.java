package model.piece.decorator.concreteDecorator;

import model.piece.AbtractPiece.PieceInterface;
import model.piece.abstractType.Artillery;
import model.piece.decorator.abstractDecorator.PieceInterfaceDecorator;

public class AttackPowerNerfArtilleryDecorator extends PieceInterfaceDecorator implements Artillery {

    private static final int NERF_ARTILLERY_AP = 40;

    public AttackPowerNerfArtilleryDecorator(PieceInterface piece) {
        super(piece);
        nerfAttackPower();
    }

    @Override
    public void nerfAttackPower() {
        setAttackPower(piece.getAttackPower() - NERF_ARTILLERY_AP);
    }
}
