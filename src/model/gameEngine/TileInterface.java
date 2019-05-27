package model.gameEngine;

import model.piece.AbtractPiece.PieceInterface;
import view.gameView.GameFrameView;

public interface TileInterface {

    //void draw(GameFrameView gfv, int row, int col);
    int getRow();
    int getCol();

    PieceInterface getPiece();

}
