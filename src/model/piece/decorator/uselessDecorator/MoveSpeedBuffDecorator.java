package model.piece.decorator.uselessDecorator;

import model.piece.AbtractPiece.PieceInterface;
import model.piece.decorator.abstractDecorator.PieceInterfaceDecorator;

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
