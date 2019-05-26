package model.gameEngine;

import model.piece.AbtractPiece.PieceInterface;

import java.io.Serializable;

public class PieceTile implements Serializable, TileInterface {
    private static final long serialVersionUID = 3L;

    private PieceInterface piece;
    private int row, col;

    public PieceTile(int row, int col) {
        this.row = row;
        this.col = col;
        System.out.printf("PieceTile created at row %d , column %d", row, col);
    }

    public PieceInterface getPiece() {
        return piece;
    }

    void setPiece(PieceInterface piece) {
        this.piece = piece;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
