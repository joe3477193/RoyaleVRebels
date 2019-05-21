package model.piece.decorator;

import model.piece.AbtractPiece.PieceInterface;

public class MoveSpeedBuffDecorator extends PieceInterfaceDecorator {

    public MoveSpeedBuffDecorator(PieceInterface piece) {
        super(piece);
        buffMoveSpeed();
    }

    @Override
    public void buffMoveSpeed() {
        setMoveSpeed(piece.getMoveSpeed() + 1);
    }
}
