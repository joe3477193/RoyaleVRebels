package model.piece.decorator.concreteDecorator;

import model.piece.AbtractPiece.PieceInterface;
import model.piece.abstractType.Troop;
import model.piece.decorator.abstractDecorator.PieceInterfaceDecorator;

public class ResetModeTroopDecorator extends PieceInterfaceDecorator implements Troop {

    public ResetModeTroopDecorator(PieceInterface piece) {
        super(piece);
        resetMode();
    }

    public void resetMode() {
        setAttackPower(piece.getInitAttackPower());
        setDefence(piece.getInitDefence());
        setAttackRange(piece.getInitAttackRange());
        setMoveSpeed(piece.getInitMoveSpeed());
        isOffensive = false;
        isDefensive = false;
    }
}