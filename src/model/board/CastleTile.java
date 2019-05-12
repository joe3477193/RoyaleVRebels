package model.board;

import model.pieces.Piece;
import view.GameFrameView;

class CastleTile implements Tile {

    private int row, col;
    private double health;

    CastleTile() {

    }

    public void draw(GameFrameView gfv, int row, int col) {
        if (col%2!=0) {
            gfv.drawCastleTile(row, col);
        }
        if (col%4==0) {
            gfv.drawCastleLeftWallTile(row, col);
        }
        if (col%4==2) {
            gfv.drawCastleRightWallTile(row, col);
        }
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
