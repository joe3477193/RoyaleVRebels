package model.tile;

import model.piece.AbtractPiece.PieceInterface;

public class GrassTile implements TileInterface {

    private int row, col;

    GrassTile() {
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
        return null;
    }
}
