package controller.commandPattern;

import javax.swing.*;

import model.piece.AbtractPiece.PieceInterface;

public interface Turn {

    TurnType returnLastMove();

    void executeTurn(String type,String image, int i, int j, PieceInterface p);
    
    void undoTurn();
}
