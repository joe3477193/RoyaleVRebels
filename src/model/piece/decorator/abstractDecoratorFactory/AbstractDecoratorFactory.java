package model.piece.decorator.abstractDecoratorFactory;

import model.piece.AbtractPiece.PieceInterface;
import model.piece.decorator.abstractDecorator.PieceInterfaceDecorator;

public abstract class AbstractDecoratorFactory extends PieceInterfaceDecorator {

    public AbstractDecoratorFactory(PieceInterface piece) {
        super(piece);
    }

    public abstract PieceInterface getFactory();
}
