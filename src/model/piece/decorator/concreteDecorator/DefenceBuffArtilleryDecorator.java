package model.piece.decorator.concreteDecorator;

import model.piece.AbtractPiece.PieceInterface;
import model.piece.abstractType.Artillery;
import model.piece.decorator.abstractDecorator.PieceInterfaceDecorator;

public class DefenceBuffArtilleryDecorator extends PieceInterfaceDecorator implements Artillery {

    public DefenceBuffArtilleryDecorator(PieceInterface piece) {
        super(piece);
        buffDefence();
    }

    @Override
    public void buffDefence() {
        setDefence(piece.getDefence() + 30);
    }
}
