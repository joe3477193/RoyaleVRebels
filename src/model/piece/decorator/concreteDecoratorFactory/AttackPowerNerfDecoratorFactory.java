package model.piece.decorator.concreteDecoratorFactory;

import model.piece.AbtractPiece.PieceInterface;
import model.piece.abstractType.Artillery;
import model.piece.abstractType.Troop;
import model.piece.decorator.abstractDecoratorFactory.AbstractDecoratorFactory;
import model.piece.decorator.concreteDecorator.AttackPowerNerfArtilleryDecorator;
import model.piece.decorator.concreteDecorator.AttackPowerNerfTroopDecorator;

public class AttackPowerNerfDecoratorFactory extends AbstractDecoratorFactory {

    public AttackPowerNerfDecoratorFactory(PieceInterface piece) {
        super(piece);
    }

    public PieceInterface getFactory() {
        if (piece instanceof Troop) {
            return new AttackPowerNerfTroopDecorator(piece);
        }
        else if (piece instanceof Artillery) {
            return new AttackPowerNerfArtilleryDecorator(piece);
        }
        return null;
    }
}
