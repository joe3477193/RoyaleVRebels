package model.board;

import model.pieces.Piece;

public interface TileInterface {

    //TODO change TileInt class name to Tile
    public void setRow(int row);
    public void setCol(int col);
    public boolean hasPiece();
    public Piece getPiece();
    public void setPiece(Piece piece);
    public void removePiece();

}
