package model.board;

import model.pieces.Piece;
import view.GameFrameView;

class WallTile implements Tile {

    private int row, col;

    WallTile() {

    }

    public void draw(GameFrameView gfv, int row, int col) {
        gfv.drawWallTile(row, col);
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
