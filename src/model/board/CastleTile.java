package model.board;

import model.pieces.Piece;

class CastleTile implements TileInt{

    private int row, col;
    private String type;

    CastleTile() {
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
}
