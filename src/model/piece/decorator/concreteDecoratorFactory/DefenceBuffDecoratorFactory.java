package model.piece.decorator.concreteDecoratorFactory;

import model.piece.AbtractPiece.PieceInterface;
import model.piece.abstractType.Artillery;
import model.piece.abstractType.Troop;
import model.piece.decorator.abstractDecoratorFactory.AbstractDecoratorFactory;
import model.piece.decorator.concreteDecorator.DefenceBuffArtilleryDecorator;
import model.piece.decorator.concreteDecorator.DefenceBuffTroopDecorator;

public class DefenceBuffDecoratorFactory extends AbstractDecoratorFactory {

    public DefenceBuffDecoratorFactory(PieceInterface piece) {
        super(piece);
    }

    public PieceInterface getFactory() {
        if (piece instanceof Troop) {
            return new DefenceBuffTroopDecorator(piece);
        } else if (piece instanceof Artillery) {
            return new DefenceBuffArtilleryDecorator(piece);
        }
        return null;
    }
}
