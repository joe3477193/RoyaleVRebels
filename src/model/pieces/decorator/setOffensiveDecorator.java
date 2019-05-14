package model.pieces.decorator;

import model.pieces.PieceInterface;

public class setOffensiveDecorator extends PieceInterfaceDecorator {

    public setOffensiveDecorator(PieceInterface piece) {
        super(piece);
    }

    public void setOffensive() {
        super.setOffensive();
        this.setAttackPower(this.piece.getAttackPower() + 20);
        this.setHP(this.piece.getHp() - 50);
    }
}
