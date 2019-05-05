package model.board;

import model.pieces.Piece;
fgfg
class Tile {

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
