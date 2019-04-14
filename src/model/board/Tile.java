package model.board;

import model.pieces.Piece;

class Tile {

    private Piece piece;

    Tile() {
    }

    void setPiece(Piece piece) {
        this.piece = piece;
    }

    Piece getPiece() {
        return piece;
    }

    boolean hasPiece() {
        return piece != null;
    }

    void removePiece() {
        piece = null;
    }

}
