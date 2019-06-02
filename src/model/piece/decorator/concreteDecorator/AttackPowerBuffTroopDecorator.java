package model.piece.decorator.concreteDecorator;

import model.piece.AbtractPiece.PieceInterface;
import model.piece.abstractType.Troop;
import model.piece.decorator.abstractDecorator.PieceInterfaceDecorator;

public class AttackPowerBuffTroopDecorator extends PieceInterfaceDecorator implements Troop {

    private static final int BUFF_TROOP_AP = 30;

    public AttackPowerBuffTroopDecorator(PieceInterface piece) {
        super(piece);
        buffAttackPower();
    }

    @Override
    public void buffAttackPower() {
        setAttackPower(piece.getAttackPower() + BUFF_TROOP_AP);
    }
}
