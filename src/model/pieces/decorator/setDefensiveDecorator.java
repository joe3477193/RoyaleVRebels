package model.pieces.decorator;

import model.pieces.PieceInterface;

public class setDefensiveDecorator extends PieceInterfaceDecorator {

    public setDefensiveDecorator(PieceInterface piece) {
        super(piece);
    }

    public void setDefensive() {
        super.setDefensive();
        this.setAttackPower(this.piece.getAttackPower() - 30);
        this.setHP(this.piece.getHp() + 100);
    }
}