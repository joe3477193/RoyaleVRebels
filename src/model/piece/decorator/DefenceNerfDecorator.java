package model.piece.decorator;

import model.piece.AbtractPiece.PieceInterface;

public class DefenceNerfDecorator extends PieceInterfaceDecorator {

    public DefenceNerfDecorator(PieceInterface piece) {
        super(piece);
        nerfDefence();
    }

    @Override
    public void nerfDefence() {
        setDefence(piece.getDefence() - 10);
    }
}
