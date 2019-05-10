package model.board;

import model.pieces.Piece;

class WallTile implements TileInterface{

    private int row, col;

    WallTile(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public boolean hasPiece() {
        return false;
    }

    public Piece getPiece() {
        return null;
    }

    public void setPiece( Piece piece) {

    }

    public void removePiece() {

    }



}
