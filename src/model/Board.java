package model;

import java.util.ArrayList;

public class Board {



    protected static ArrayList<BoardRows> gridrows;
    public static final int BOARD_ROWS = 13; // increments in 5
    public static final int BOARD_COLS = 15; // increments in 4

    public Board() {

        Board.gridrows = new ArrayList<>(BOARD_ROWS);

        for (int i = 0; i < BOARD_ROWS; i++) {
            gridrows.add(new BoardRows());
        }
    }

    public int getRows() {
    	return BOARD_ROWS;
    }
    
    public int getCols() {
    	return BOARD_COLS;
    }
    
    // Check if piece has been initialized successfully in current tile
    public boolean checkInit(int row, int tile) {
        return gridrows.get(row).getTile(tile).hasPiece();
    }

    // Check if piece in current tile is moveable
    public boolean checkMoveInit(int row, int tile) {
        return checkInit(row, tile) && gridrows.get(row).getTile(tile).getPiece().isMoveable();
    }

    // Check if piece in current tile is attackable
    public boolean checkAttackInit(int row, int tile) {
        return checkInit(row, tile) && gridrows.get(row).getTile(tile).getPiece().isAttackable();
    }

    // Check if piece can move from current tile to target tile
    public boolean checkMoveTarget(int row, int tile) {
        return !gridrows.get(row).getTile(tile).hasPiece();
    }

    // Check if piece can attack target from current tile
    public boolean checkAttackTarget(Piece piece, int row, int tile) {
        Tile space = gridrows.get(row).getTile(tile);
        if (space.hasPiece() && !space.getPiece().getFaction().equals(piece.getFaction())) {
            return true;
        }
        return false;
    }

    // Check if piece moved from current tile to target tile
    public boolean move(int inRow, int inTile, int tgRow, int tgTile) {
        Tile inSpace = gridrows.get(inRow).getTile(inTile);
        Tile tgSpace = gridrows.get(tgRow).getTile(tgTile);
        if (inSpace.getPiece() != null) {
            // check if it can move or not
            if (inSpace.getPiece().isMoveable() && !tgSpace.hasPiece()) {

                tgSpace.setPiece(inSpace.getPiece());
                inSpace.removePiece();
                return true;
            }
        }
        return false;
    }

    // Check if piece attacked target piece
    public boolean attack(Piece piece, int row, int tile) {
        Tile space = gridrows.get(row).getTile(tile);
        Piece tgPiece = space.getPiece();
        if (piece != null && tgPiece != null) {
            // Check if piece can attack target piece
            if (piece.isAttackable() && !tgPiece.getFaction().equals(piece.getFaction())) {
                tgPiece.attackBy(piece.getAttack());
                if (tgPiece.isDead()) {
                    space.removePiece();
                }
                return true;
            }
        }
        return false;
    }

}
