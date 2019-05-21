package model.piece.decorator;

import model.piece.AbtractPiece.PieceInterface;

public class DefenceBuffDecorator extends PieceInterfaceDecorator {

    public DefenceBuffDecorator(PieceInterface piece) {
        super(piece);
        buffDefence();
    }

    @Override
    public void buffDefence() {
        setDefence(piece.getDefence() + 30);
    }
}
