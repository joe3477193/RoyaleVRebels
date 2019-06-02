package model.tile;

import model.piece.AbtractPiece.PieceInterface;

public class PieceTile implements TileInterface {

    private PieceInterface piece;
    private int row, col;

    public PieceTile(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public PieceInterface getPiece() {
        return piece;
    }

    public void setPiece(PieceInterface piece) {
        this.piece = piece;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
