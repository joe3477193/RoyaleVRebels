package model.gameEngine;

import controller.commandPattern.CommandInterface;
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

    void clickTile(int row, int col);

    void resetCoordinates();

    boolean hasCoordinates();

    int getRowIndex();

    int getColIndex();

    int getMaxRows();

    int getMaxCols();

    int getTurn();

    PieceInterface getSummonedPiece();

    void removeSummonedPiece();

    boolean checkOnBoardPieceMoveable(int row, int col);

    boolean checkOnBoardPieceAttackable(int row, int col);

    void createSummonedPiece(String name);

    boolean placeSummonedPiece(int row, int col);

    boolean placeMovedPiece(int row, int col);

    boolean isCastleTile(int row, int col);

    boolean isWallTile(int row, int col);

    boolean isPieceTile(int row, int col);

    boolean isGrassTile(int row, int col);

    TurnType placeAttackPiece(int row, int col);

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

    PieceInterface getPiece(int row, int col);

    boolean checkUndoRemain();

    void undoTurn(CommandInterface cI);

    int getCastleHp();

    void changeAttackIconColor(TileInterface tile, int row, int col);

    boolean checkWin();

    void notifyUndoRule();

    void gameOver(String name);
}
