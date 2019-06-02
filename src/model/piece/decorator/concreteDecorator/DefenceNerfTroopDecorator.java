package model.piece.decorator.concreteDecorator;

import model.piece.AbtractPiece.PieceInterface;
import model.piece.abstractType.Troop;
import model.piece.decorator.abstractDecorator.PieceInterfaceDecorator;

public class DefenceNerfTroopDecorator extends PieceInterfaceDecorator implements Troop {

    private static final int NERF_TROOP_DEFENCE = 5;

    public DefenceNerfTroopDecorator(PieceInterface piece) {
        super(piece);
        nerfDefence();
    }

    @Override
    public void nerfDefence() {
        setDefence(piece.getDefence() - NERF_TROOP_DEFENCE);
    }
}
