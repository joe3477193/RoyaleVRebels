package model.piece.decorator;

import model.piece.AbtractPiece.PieceInterface;

public class SetDefensiveDecorator extends PieceInterfaceDecorator {

    public SetDefensiveDecorator(PieceInterface piece) {
        super(piece);
        setDefensive();
    }

    public void setDefensive() {
        isDefensive = true;
    }
}