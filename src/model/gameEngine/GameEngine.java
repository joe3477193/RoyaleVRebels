package model.gameEngine;

import controller.commandPattern.AbstractTurn;
import model.piece.AbtractPiece.PieceInterface;
import model.player.Player;

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

    boolean getHasPerformed();

    void unsetActionPerformed();

    int[] getCoordinates();

    void clickTile(int i, int j);

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

    boolean placeSummonedPiece(int i, int j);

    boolean placeMovedPiece(int i, int j);

    boolean isWall(int i, int j);

    boolean placeAttackPiece(int i, int j);

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

    Tile getTile(int row, int col);

    boolean saveGame();

    void loadGame(String[] undoLimit, String turn, String actionPerformed, ArrayList<String[]> tileList);

    void changeAttackTarget(Tile tile, int i, int j);
}
