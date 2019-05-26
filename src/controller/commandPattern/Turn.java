package controller.commandPattern;

import javax.swing.*;

import model.piece.AbtractPiece.PieceInterface;

public interface Turn {


    void executeTurn(String type, JButton[][] tileBtn,String image, int i, int j, PieceInterface p);
    
    void undoTurn();
}
