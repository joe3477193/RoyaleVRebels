package model.pieces.decorator;

import model.pieces.Piece;
import model.pieces.PieceInterface;

public class setDefensiveDecorator extends PieceInterfaceDecorator {

    public setDefensiveDecorator(PieceInterface piece) {
        super(piece);
    }

    public void setDefensive() {
        super.setHP(super.getHp() + 100);
        super.setAttackPower(super.getAttackPower() - 10);
    }
}