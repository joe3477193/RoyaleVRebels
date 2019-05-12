package model.board;

import model.pieces.Piece;
import view.GameFrameView;

public interface Tile {

    void setRow(int row);
    void setCol(int col);
    boolean hasPiece();
    Piece getPiece();
    void setPiece(Piece piece);
    void removePiece();
    void draw(GameFrameView gfv, int row, int col);

}
