package controller.commandPattern;

import model.piece.AbtractPiece.PieceInterface;

public interface Turn {

    TurnType returnLastMove();

    void executeTurn(String type, String image, int row, int col, PieceInterface pieceInterface);

    void undoTurn();
}
