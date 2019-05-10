package model.board;

import model.pieces.Piece;

class Tile implements TileInterface{

    private Piece piece;
    private int row, col;

    Tile(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece( Piece piece) {
        this.piece = piece;
    }

    public boolean hasPiece() {
        return piece != null;
    }

    public void removePiece() {
        piece = null;
    }

}
