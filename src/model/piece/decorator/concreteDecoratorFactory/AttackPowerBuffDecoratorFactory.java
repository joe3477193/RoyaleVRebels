package model.piece.decorator.concreteDecoratorFactory;

import model.piece.AbtractPiece.PieceInterface;
import model.piece.abstractType.Artillery;
import model.piece.abstractType.Troop;
import model.piece.decorator.abstractDecoratorFactory.AbstractDecoratorFactory;
import model.piece.decorator.concreteDecorator.AttackPowerBuffArtilleryDecorator;
import model.piece.decorator.concreteDecorator.AttackPowerBuffTroopDecorator;

public class AttackPowerBuffDecoratorFactory extends AbstractDecoratorFactory {

    public AttackPowerBuffDecoratorFactory(PieceInterface piece) {
        super(piece);
    }

    public PieceInterface getFactory() {
        if (piece instanceof Troop) {
            return new AttackPowerBuffTroopDecorator(piece);
        } else if (piece instanceof Artillery) {
            return new AttackPowerBuffArtilleryDecorator(piece);
        }
        return null;
    }
}
