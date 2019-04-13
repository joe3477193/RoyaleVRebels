package model.board;

import model.pieces.Piece;

public class Tile {

    private Piece piece;

    public Tile() {
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Piece getPiece() {
        return piece;
    }

    public boolean hasPiece() {
        if (piece != null) {
            return true;
        }
        return false;
    }

    public void removePiece() {
        piece = null;
    }

}
