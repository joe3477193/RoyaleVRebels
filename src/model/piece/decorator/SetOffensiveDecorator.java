package model.piece.decorator;

import model.piece.AbtractPiece.PieceInterface;

public class SetOffensiveDecorator extends PieceInterfaceDecorator {

    public SetOffensiveDecorator(PieceInterface piece) {
        super(piece);
        setOffensive();
    }

    public void setOffensive() {
        isOffensive = true;
    }
}
