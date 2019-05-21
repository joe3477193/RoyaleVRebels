package model.piece.decorator.concreteDecoratorFactory;

import model.piece.AbtractPiece.PieceInterface;
import model.piece.abstractType.Artillery;
import model.piece.abstractType.Troop;
import model.piece.decorator.abstractDecoratorFactory.AbstractDecoratorFactory;
import model.piece.decorator.concreteDecorator.ResetModeArtilleryDecorator;
import model.piece.decorator.concreteDecorator.ResetModeTroopDecorator;

public class ResetModeDecoratorFactory extends AbstractDecoratorFactory {

    public ResetModeDecoratorFactory(PieceInterface piece) {
        super(piece);
    }

    public PieceInterface getFactory() {

        if (piece instanceof Troop) {
            return new ResetModeTroopDecorator(piece);
        } else if (piece instanceof Artillery) {
            return new ResetModeArtilleryDecorator(piece);
        }
        return null;
    }
}
