package model.pieces.decorator;

import model.pieces.PieceInterface;

public class SetDefensiveDecorator extends PieceInterfaceDecorator {

    public SetDefensiveDecorator(PieceInterface piece) {
        super(piece);
    }

    public void setDefensive() {
        super.setDefensive();
        this.setAttackPower(this.piece.getAttackPower() - 30);
        this.setHP(this.piece.getHp() + 100);
    }
}