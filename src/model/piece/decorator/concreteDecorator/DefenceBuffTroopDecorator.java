package model.piece.decorator.concreteDecorator;

import model.piece.AbtractPiece.PieceInterface;
import model.piece.abstractType.Troop;
import model.piece.decorator.abstractDecorator.PieceInterfaceDecorator;

public class DefenceBuffTroopDecorator extends PieceInterfaceDecorator implements Troop {

    public DefenceBuffTroopDecorator(PieceInterface piece) {
        super(piece);
        buffDefence();
    }

    @Override
    public void buffDefence() {
        setDefence(piece.getDefence() + 30);
    }
}
