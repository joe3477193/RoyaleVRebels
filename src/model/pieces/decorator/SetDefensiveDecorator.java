package model.pieces.decorator;

import model.pieces.PieceInterface;

public class SetDefensiveDecorator extends PieceInterfaceDecorator {

    public SetDefensiveDecorator(PieceInterface piece) {
        super(piece);
    }

    public void setDefensive() {
        super.setDefensive();
        setAttackPower(piece.getAttackPower() - 30);
        setHP(piece.getHp() + 100);
        isDefensive = true;
    }
}