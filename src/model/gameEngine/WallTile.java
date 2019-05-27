package model.gameEngine;

import model.piece.AbtractPiece.PieceInterface;

public class WallTile implements TileInterface {

    private int row, col;

    public WallTile () {
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

    public PieceInterface getPiece(){
        return null;
    }
}
