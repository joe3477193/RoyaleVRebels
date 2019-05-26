package model.piece.decorator.concreteDecorator;

import model.piece.AbtractPiece.PieceInterface;
import model.piece.abstractType.Troop;
import model.piece.decorator.abstractDecorator.PieceInterfaceDecorator;

public class DefenceNerfTroopDecorator extends PieceInterfaceDecorator implements Troop {

    public DefenceNerfTroopDecorator(PieceInterface piece) {
        super(piece);
        nerfDefence();
    }

    @Override
    public void nerfDefence() {
        setDefence(piece.getDefence() - 5);
    }
}
