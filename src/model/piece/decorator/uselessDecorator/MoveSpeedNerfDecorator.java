package model.piece.decorator.uselessDecorator;

import model.piece.AbtractPiece.PieceInterface;
import model.piece.decorator.abstractDecorator.PieceInterfaceDecorator;

public class MoveSpeedNerfDecorator extends PieceInterfaceDecorator {

    public MoveSpeedNerfDecorator(PieceInterface piece) {
        super(piece);
        nerfMoveSpeed();
    }

    @Override
    public void nerfMoveSpeed() {
        setMoveSpeed(piece.getMoveSpeed() - 1);
        System.out.println("nms");
    }
}
