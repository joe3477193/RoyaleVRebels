package model.piece.decorator.concreteDecorator;

import model.piece.AbtractPiece.PieceInterface;
import model.piece.abstractType.Artillery;
import model.piece.decorator.abstractDecorator.PieceInterfaceDecorator;

public class DefenceBuffArtilleryDecorator extends PieceInterfaceDecorator implements Artillery {

    private static final int BUFF_ARTILLERY_DEFENCE = 30;

    public DefenceBuffArtilleryDecorator(PieceInterface piece) {
        super(piece);
        buffDefence();
    }

    @Override
    public void buffDefence() {
        setDefence(piece.getDefence() + BUFF_ARTILLERY_DEFENCE);
    }
}
