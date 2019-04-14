package model.board;

import model.pieces.Piece;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Post;

class Tile {

    private Piece piece;

    Tile() {
    }

    Piece getPiece() {
        return piece;
    }

    void setPiece(@NotNull Piece piece) {
        this.piece = piece;
    }

    boolean hasPiece() {
        return piece != null;
    }

    @Post(expr = "_this.piece == null", lang = "groovy")
    void removePiece() {
        piece = null;
    }

}
