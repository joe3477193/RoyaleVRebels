package model.tile;

import model.piece.AbtractPiece.PieceInterface;

public interface TileInterface {

    int getRow();

    int getCol();

    PieceInterface getPiece();
}
