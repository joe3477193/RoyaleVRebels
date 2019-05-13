package model.pieces.decorator;

import model.pieces.Piece;
import model.pieces.PieceInterface;

public class setOffensiveDecorator extends PieceInterfaceDecorator {

    public setOffensiveDecorator(PieceInterface piece) {
        super(piece);
    }

    public void setOffensive() {
        super.setAttackPower(super.getAttackPower() + 20);
        super.setHP(super.getHp() - 50);
    }
}
