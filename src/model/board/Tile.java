package model.board;

import model.pieces.Piece;

class Tile implements TileInt{

    //TODO change Tile class name to GrassTile

    private Piece piece;
    private int row, col;
    private String type;

    Tile() {
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public String getCoordinates() {
        String rowString = String.valueOf(row);
        String colString = String.valueOf(col);
        return rowString + "," + colString;
    }

    Piece getPiece() {
        return piece;
    }

    void setPiece( Piece piece) {
        this.piece = piece;
    }

    boolean hasPiece() {
        return piece != null;
    }

    void removePiece() {
        piece = null;
    }

}
