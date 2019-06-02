package model.piece.decorator.concreteDecorator;

import model.piece.AbtractPiece.PieceInterface;
import model.piece.abstractType.Troop;
import model.piece.decorator.abstractDecorator.PieceInterfaceDecorator;

public class DefenceBuffTroopDecorator extends PieceInterfaceDecorator implements Troop {

    private static final int BUFF_TROOP_DEFENCE = 20;

    public DefenceBuffTroopDecorator(PieceInterface piece) {
        super(piece);
        buffDefence();
    }

    @Override
    public void buffDefence() {
        setDefence(piece.getDefence() + BUFF_TROOP_DEFENCE);
    }
}
