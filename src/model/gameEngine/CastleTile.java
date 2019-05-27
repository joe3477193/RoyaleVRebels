package model.gameEngine;

import model.piece.AbtractPiece.PieceInterface;
import model.piece.concretePiece.Castle;

public class CastleTile implements TileInterface {

    private int row, col;
    private PieceInterface piece;

    CastleTile() {
        piece = new Castle();
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public PieceInterface getPiece() {
        return piece;
    }
}
