package model;

import java.util.ArrayList;

import static java.lang.Math.abs;

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

    private Tile getTile(int row, int tile){
        return gridrows.get(row).getTile(tile);
    }

    // Gets a piece from a tile
    public Piece getPiece(int row, int tile){
        return getTile(row, tile).getPiece();
    }

    public boolean checkSummon(Player player, Piece piece){
        if(player.getFaction().equals(piece.getFaction())){
            return true;
        }
        return false;
    }

    //summons a piece and returns true if successful
    public boolean summonPiece(Piece piece, int row, int tile){
        boolean isRowValid;
        if(piece.getFaction().equals("Royale")){
            isRowValid= row<3;
        }
        else{
            isRowValid= row>10;
        }

        if(checkMoveTarget(row, tile) && isRowValid){
            getTile(row, tile).setPiece(piece);
            return true;
        }
        else{
            return false;
        }
    }

    // Check if piece has been initialized successfully in current tile
    public boolean checkInit(int row, int tile) {
        return getTile(row, tile).hasPiece();
    }

    // Check if piece in current tile is moveable
    public boolean checkMoveInit(int row, int tile) {
        return getTile(row, tile).getPiece().isMoveable();
    }

    // Check if piece in current tile is attackable
    public boolean checkAttackInit(int row, int tile) {
        return getTile(row, tile).getPiece().isAttackable();
    }

    // Check if piece can move from current tile to target tile
    public boolean checkMoveTarget(int row, int tile) {
        return !getTile(row, tile).hasPiece();
    }

    // Check if piece can attack target from current tile
    public boolean checkAttackTarget(Piece piece, int tgRow, int tgTile) {
        Tile space = getTile(tgRow, tgTile);
        if (space.hasPiece() && !space.getPiece().getFaction().equals(piece.getFaction())) {
            return true;
        }
        return false;
    }

    private boolean isMovRangeValid(int inRow, int inTile, int tgRow, int tgTile, String type){
        int rowDiff= abs(inRow-tgRow);
        int tileDiff= abs(inTile-tgTile);
        Piece piece= getPiece(inRow, inTile);
        if(type.equals("mov")){
            return piece.getMov()<=rowDiff && piece.getMov()<=tileDiff;
        }
        else if(type.equals("range")){
            return piece.getRange()<=rowDiff && piece.getRange()<=tileDiff;
        }
        return false;
    }

    // Check if piece moved from current tile to target tile
    public boolean move(int inRow, int inTile, int tgRow, int tgTile) {
        if (checkMoveTarget(tgRow,tgTile) && isMovRangeValid(inRow,inTile,tgRow,tgTile,"mov")) {
                getTile(tgRow,tgTile).setPiece(getPiece(inRow,inTile));
                getTile(inRow,inTile).removePiece();
                return true;
            }
        return false;
        }


    // Check if piece attacked target piece
    public boolean attack(int inRow, int inTile, int tgRow, int tgTile) {
        if (checkAttackTarget(getPiece(inRow,inTile),tgRow,tgTile) &&
                isMovRangeValid(inRow,inTile,tgRow,tgTile,"attack")){
            getPiece(tgRow,tgTile).attackedBy(getPiece(inRow,inTile).getAttack());
            if (getPiece(tgRow, tgTile).isDead()) {
                getTile(tgRow, tgTile).removePiece();
            }
            return true;
        }
        return false;
    }

}
