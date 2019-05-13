package model.board;

import model.pieces.Piece;

import java.io.Serializable;

class Tile implements Serializable {
    private static final long serialVersionUID = 3L;

    private Piece piece;
    private int row, col;

    Tile(int row, int col) {
        this.row = row;
        this.col = col;
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
