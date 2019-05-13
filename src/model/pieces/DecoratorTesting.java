package model.pieces;

import model.pieces.decorator.setOffensiveDecorator;
import model.pieces.type.Angryman;

public class DecoratorTesting {

    public static void main (String[] args) {
        PieceInterface angry1 = new Angryman();
        System.out.println(angry1.getAttackPower());
        PieceInterface angry2 = new setOffensiveDecorator(angry1);
        System.out.println(angry2.getAttackPower());
    }
}
