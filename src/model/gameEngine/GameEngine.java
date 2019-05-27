package model.gameEngine;

import controller.commandPattern.TurnType;
import model.piece.AbtractPiece.PieceInterface;
import model.player.Player;
import model.tile.TileInterface;

import java.util.ArrayList;

public interface GameEngine {

    int getRebelTurn();

    boolean isMoving();

    boolean isAttacking();

    void resetAttacking();

    void setAttacking();

    void resetMoving();

    void setMoving();

    boolean getPerformed();

    void unsetPerformed();

    int[] getCoordinates();

    void clickTile(int i, int j);

    void resetCoordinates();

    boolean hasCoordinates();

    int getRowIndex();

    int getColIndex();

    int getMaxRows();

    int getMaxCols();

    int getTurn();

    PieceInterface getSummonedPiece();

    void removeSummonedPiece();

    boolean checkOnBoardPieceMoveable(int row, int tile);

    boolean checkOnBoardPieceAttackable(int row, int tile);

    void createSummonedPiece(String name);

    boolean placeSummonedPiece(int i, int j);

    boolean placeMovedPiece(int i, int j);

    boolean isCastleTile(int i, int j);

    boolean isWallTile(int i, int j);

    boolean isPieceTile(int i, int j);

    boolean isGrassTile(int i, int j);

    TurnType placeAttackPiece(int i, int j);

    void setOffensive();

    void setDefensive();

    TileInterface[][] getTiles();

    void setTileIcon(ArrayList<String[]> tileList);

    int[] getInitTileCoord();

    void paintSummonRange(String troop);

    int[] getUndoLevel();

    Player getRebelPlayer();

    Player getRoyalePlayer();

    TileInterface getTile(int row, int col);

    boolean saveGame();

    void loadGame(String castleHp, String[] undoLimit, String turn, String hasPerformed, ArrayList<String[]> tileList);

    PieceInterface getPiece(int row, int tile);

    boolean checkUndoRemain();

    void undoTurn(TurnType tt);

    int getCastleHp();

    void changeAttackTarget(TileInterface tile, int i, int j);
}
