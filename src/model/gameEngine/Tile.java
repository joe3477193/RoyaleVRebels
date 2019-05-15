package model.gameEngine;

import model.piece.AbtractPiece.PieceInterface;

import java.io.Serializable;

public class Tile implements Serializable {
    private static final long serialVersionUID = 3L;

    private PieceInterface piece;
    private int row, col;

    public Tile(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public PieceInterface getPiece() {
        return piece;
    }

    void setPiece(PieceInterface piece) {
        this.piece = piece;
    }

    public boolean hasPiece() {
        return piece != null;
    }

    void removePiece() {
        piece = null;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
