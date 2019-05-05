package model.board;

import model.pieces.Piece;

class Tile {

    private Piece piece;

    Tile() {        //
    }

    Piece getPiece() {
        return piece;
    }

    void setPiece( Piece piece) {
        this.piece = piece;
    }

    boolean hasPiece() {
        return piece != null;
    }

    void removePiece() {
        piece = null;
    }

}
