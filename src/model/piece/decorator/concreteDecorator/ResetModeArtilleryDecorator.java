package model.piece.decorator.concreteDecorator;

import model.piece.AbtractPiece.PieceInterface;
import model.piece.abstractType.Artillery;
import model.piece.decorator.abstractDecorator.PieceInterfaceDecorator;

public class ResetModeArtilleryDecorator extends PieceInterfaceDecorator implements Artillery {

    public ResetModeArtilleryDecorator(PieceInterface piece) {
        super(piece);
        resetMode();
    }

    // reset the artillery's mode
    public void resetMode() {
        setAttackPower(piece.getInitAttackPower());
        setDefence(piece.getInitDefence());
        setAttackRange(piece.getInitAttackRange());
        setMoveSpeed(piece.getInitMoveSpeed());
        isOffensive = false;
        isDefensive = false;
    }
}
