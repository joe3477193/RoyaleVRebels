package model.tile;

import model.piece.AbtractPiece.PieceInterface;

public interface TileInterface {

    //void draw(GameFrameView gfv, int row, int col);
    int getRow();

    int getCol();

    PieceInterface getPiece();
}
