package model.board;

import model.pieces.PieceInterface;

class Tile {

    private PieceInterface piece;
    private int row, col;

    Tile(int row, int col) {
        this.row = row;
        this.col = col;
    }

    PieceInterface getPiece() {
        return piece;
    }

    void setPiece(PieceInterface piece) {
        this.piece = piece;
    }

    boolean hasPiece() {
        return piece != null;
    }

    void removePiece() {
        piece = null;
    }

}
