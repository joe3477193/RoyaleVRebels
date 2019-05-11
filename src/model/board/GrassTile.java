package model.board;

import model.pieces.Piece;
import view.GameFrameView;

class GrassTile implements TileInterface{

    private Piece piece;
    private int row, col;

    GrassTile() {

    }

    public void draw(GameFrameView gfv, int row, int col) {
        gfv.drawGrassTile(row, col);
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
