package model.pieces.decorator;

import model.pieces.PieceInterface;

public class SetOffensiveDecorator extends PieceInterfaceDecorator {

    public SetOffensiveDecorator(PieceInterface piece) {
        super(piece);
    }

    public void setOffensive() {
        super.setOffensive();
        if (piece.getHp() - 50 > 0) {
            setAttackPower(piece.getAttackPower() + 20);
            setHP(piece.getHp() - 50);
            isOffensive = true;
        } else {
            System.out.println("This piece cannot be strengthened because its hp will be smaller than 0 after the action.");
        }
    }
}
