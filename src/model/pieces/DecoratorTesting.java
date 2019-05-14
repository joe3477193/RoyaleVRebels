package model.pieces;

import model.pieces.decorator.setDefensiveDecorator;
import model.pieces.decorator.setOffensiveDecorator;
import model.pieces.type.Angryman;
import model.pieces.type.General;

public class DecoratorTesting {

    public static void main (String[] args) {
        PieceInterface angry1 = new Angryman();
        System.out.println("Original AP:" + angry1.getAttackPower());
        System.out.println("Original HP:" + angry1.getHp());
        PieceInterface angry2 = new setOffensiveDecorator(angry1);
        angry2.setOffensive();
        System.out.println("Offensive AP:" + angry2.getAttackPower());
        System.out.println("Offensive HP:" + angry2.getHp());

        System.out.println("---------------------------------------------");

        PieceInterface gen1 = new General();
        System.out.println("Original AP:" + gen1.getAttackPower());
        System.out.println("Original HP:" + gen1.getHp());
        PieceInterface gen2 = new setDefensiveDecorator(gen1);
        gen2.setDefensive();
        System.out.println("Defensive AP:" + gen2.getAttackPower());
        System.out.println("Defensive HP:" + gen2.getHp());
    }
}
