package model.pieces.decorator;

import model.pieces.PieceInterface;

public class SetOffensiveDecorator extends PieceInterfaceDecorator {

    public SetOffensiveDecorator(PieceInterface piece) {
        super(piece);
    }

    public void setOffensive() {
        super.setOffensive();
        this.setAttackPower(this.piece.getAttackPower() + 20);
        this.setHP(this.piece.getHp() - 50);
    }
}
