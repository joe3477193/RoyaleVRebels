package model.gameEngine;

import model.piece.AbtractPiece.PieceInterface;

import javax.swing.*;
import java.util.ArrayList;

public interface GameEngine {


    int getRebelTurn();

    int getRoyaleTurn();

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

    boolean placeSummonedPiece(JButton tileBtn, int i, int j);

    boolean placeMovedPiece(JButton[][] tileBtns, int i, int j);

    boolean isWall(int i, int j);

    void placeAttackPiece(int i, int j);

    void setOffensive();

    void setDefensive();

    Tile[][] getTiles();

    void setTileIcon(ArrayList<String[]> tileList);


    void undoTurn();

    int[] getInitTileCoord();

}
