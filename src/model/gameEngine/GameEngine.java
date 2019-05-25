package model.gameEngine;

import model.piece.AbtractPiece.PieceInterface;
import model.player.Player;

import javax.swing.*;

import controller.commandPattern.AbstractTurn;

import java.util.ArrayList;

public interface GameEngine {

    int getOriginalRow();

    int getOriginalCol();

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

    int getRow();

    int getCol();

    int getMaxRows();

    int getMaxCols();

    int getTurn();

    PieceInterface getSummonedPiece();

    void removeSummonedPiece();

    boolean checkInit(int row, int tile);

    boolean checkMoveInit(int row, int tile);

    boolean checkAttackInit(int row, int tile);

    void createSummonedPiece(String name);

    boolean placeSummonedPiece(JButton tileBtn, int i, int j);

    boolean placeMovedPiece(JButton[][] tileBtns, int i, int j);

    boolean isWall(int i, int j);

    boolean placeAttackPiece(JButton[][] tileBtns, int i, int j);

    void setOffensive();

    void setDefensive();

    Tile[][] getTiles();

    void setTileIcon(ArrayList<String[]> tileList);

    void pushTurnStack(AbstractTurn turn);
    
    void undoTurn();

    int[] getInitTileCoord();

    String whoseTurn();

    void paintSummonRange(String faction, String troopType);

    int[] getUndoLimit();

    Player getRebelPlayer();

    Player getRoyalePlayer();

    boolean isCastle(int i);
}
