package model.piece.decorator.concreteDecoratorFactory;

import model.piece.AbtractPiece.PieceInterface;
import model.piece.abstractType.Artillery;
import model.piece.abstractType.Troop;
import model.piece.decorator.abstractDecoratorFactory.AbstractDecoratorFactory;
import model.piece.decorator.concreteDecorator.DefenceNerfArtilleryDecorator;
import model.piece.decorator.concreteDecorator.DefenceNerfTroopDecorator;

public class DefenceNerfDecoratorFactory extends AbstractDecoratorFactory {

    public DefenceNerfDecoratorFactory(PieceInterface piece) {
        super(piece);
    }

    public PieceInterface getFactory() {
        if (piece instanceof Troop) {
            return new DefenceNerfTroopDecorator(piece);
        }
        else if (piece instanceof Artillery) {
            return new DefenceNerfArtilleryDecorator(piece);
        }
        return null;
    }
}
