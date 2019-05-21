package model.piece.decorator.concreteDecorator;

import model.piece.AbtractPiece.PieceInterface;
import model.piece.abstractType.Artillery;
import model.piece.decorator.abstractDecorator.PieceInterfaceDecorator;

public class DefenceNerfArtilleryDecorator extends PieceInterfaceDecorator implements Artillery {

    public DefenceNerfArtilleryDecorator(PieceInterface piece) {
        super(piece);
        nerfDefence();
    }

    @Override
    public void nerfDefence() {
        setDefence(piece.getDefence() - 10);
    }
}
