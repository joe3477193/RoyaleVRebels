package model.board;

import model.pieces.Piece;
import model.pieces.PieceInterface;

import javax.swing.*;

public interface GameEngine {

    boolean isMoving();

    boolean isAttacking();

    void resetAttacking();

    void setAttacking();

    void resetMoving();

    void setMoving();

    boolean getActionPerformed();

    void unsetActionPerformed();

    int[] getCoordinates();

    void clickTile(JButton tileBtn, int i, int j);

    void resetCoordinates();

    boolean hasCoordinates();

    int getRows();

    int getCols();

    int getTurn();

    PieceInterface getSummonedPiece();

    void removeSummonedPiece();

    boolean checkInit(int row, int tile);

    boolean checkMoveInit(int row, int tile);

    boolean checkAttackInit(int row, int tile);

    void createPiece(String name);

    void placeSummonedPiece(JButton tileBtn, int i, int j);

    void placeMovedPiece(JButton[][] tileBtns, int i, int j);

    boolean isWall(int i, int j);

    void placeAttackPiece(int i, int j);

    void setOffensive();

    void setDefensive();
}
