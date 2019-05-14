package model.board;

import model.pieces.Piece;

import java.io.Serializable;

public class Tile implements Serializable {
    private static final long serialVersionUID = 3L;

    private Piece piece;
    private int row, col;

    public Tile(int row, int col) {
        this.row = row;
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

    void removePiece() {
        piece = null;
    }

    public int getRow(){
        return row;
    }

    public int getCol() {
        return col;
    }
}
