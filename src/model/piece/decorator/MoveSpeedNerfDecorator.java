package model.piece.decorator;

import model.piece.AbtractPiece.PieceInterface;

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
