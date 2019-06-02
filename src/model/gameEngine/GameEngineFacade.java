package model.gameEngine;

import controller.commandPattern.*;
import model.piece.AbtractPiece.Piece;
import model.piece.AbtractPiece.PieceInterface;
import model.piece.PieceCache;
import model.piece.abstractType.Artillery;
import model.piece.abstractType.Obstacle;
import model.piece.decorator.concreteDecorator.ResetModeTroopDecorator;
import model.piece.decorator.concreteDecoratorFactory.*;
import model.piece.faction.Royale;
import model.player.Player;
import model.player.RebelPlayer;
import model.player.RoyalePlayer;
import model.tile.*;
import view.gameView.GameFrameView;
import view.mainView.MainMenuView;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import static java.lang.Math.abs;

public class GameEngineFacade implements GameEngine {

    public static final String FULL_SAVE_FILE_NAME = "gamedata.save";
    public static final int ROW_INDEX = 0;
    public static final int COL_INDEX = 1;
    public static final int REBEL_TURN = 0;

    private static final int REBEL_PIECE_CP_MIN = 1;
    private static final int ROYALE_TURN = 1;
    private static final int ROW_INDEX_LOADED = 0;
    private static final int COL_INDEX_LOADED = 1;
    private static final int IMG_NAME_INDEX_LOADED = 2;
    private static final int HP_INDEX_LOADED = 3;
    private static final int OFFENSIVE_INDEX_LOADED = 4;
    private static final int DEFENSIVE_INDEX_LOADED = 5;
    private static final int COORDINATE_LENGTH = 2;
    private static final int DEFAULT_EXTRA_MOVE = 0;
    private static final int ROYALE_SUMMON_NORTH_LIMIT = 1;
    private static final int OBSTACLE_EXTRA_SUMMON_LIMIT = 3;
    private static final int BRICK_ROW_FACTOR = 5;
    private static final int BRICK_ROW_LENGTH = 3;
    private static final int BRICK_COL_FACTOR = 4;
    private static final int BRICK_COL_INDEX = 3;
    private static final int NO_DAMAGE_DEALT = 0;
    private static final String CANNOT_ATTACK_OPPONENT_PIECE = "You cannot attack your opponent's piece.";
    private static final String CANNOT_MOVE_OPPONENT_PIECE = "You cannot move your opponent's piece.";
    private static final String PIECE_SELECTED = " selected.";
    private static final String HAS_PERFORMED = "You have already perform an action this turn.";
    private static final String INVALID_TILE = "You have not chosen a valid tile.";
    private static final String INVALID_PLACEMENT = "Invalid placement, the first two rows for Royales and the bottom three rows for Rebels.";
    private static final String TRUE_DAMAGE_DEALT = " true damage dealt! ";
    private static final String REMAINING_HP = "'s Remaining HP: ";
    private static final String SQUARE_BRACKET_LEFT = " [ ";
    private static final String SEPARATOR = "|";
    private static final String IS_DEAD = " is DEAD! ]";
    private static final String CANCEL_ATTACK = " Please click the attack button again to cancel.";
    private static final String CANNOT_STRENGTHEN_OPPONENT_PIECE = "You cannot strengthen opponent piece!";
    private static final String CANNOT_STRENGTHEN_OBSTACLE = "You cannot strengthen opponent piece!";
    private static final String UNDO_USED = "You have already used your Undo for this game!";
    private static final String UNDO_RULE = "You must wait until both players have moved before using Undo!";
    private static final String UNDO_NONE = "You have no undo's for this game!";
    private static final String OUTPUT_FORMAT = "%d|%d|%s|%d|%b|%b%n";
    private static final String PIECE_TILE = "PieceTile";
    private static final String WALL_TILE = "WallTile";
    private static final String CASTLE_TILE = "CastleTile";
    private static final String GRASS_TILE = "GrassTile";
    private static final String NOT_ENOUGH_CP = "You CP is not enough to summon this piece!";
    private static final String REBEL = "Rebel";
    private static final String ROYALE = "Royale";

    private static final int CASTLE_TILE_ROW = 0;
    private static final int CASTLE_TILE_COL = 0;

    private static final String SUMMON_TYPE = "summon";
    private static final String MOVEMENT_TYPE = "move";
    private static final String ATTACK_TYPE = "attack";
    private static final int[] turns = new int[]{REBEL_TURN, ROYALE_TURN};
    private static final int ROYALE_SUMMON_SOUTH_LIMIT = 3;
    private static final int REBEL_SUMMON_NORTH_LIMIT = 10;
    public static int BOARD_ROW_LENGTH; // increments in 5
    public static int BOARD_COL_LENGTH; // increments in 4
    // Stack for storing moves
    private GameFrameView gfv;
    private Piece summonedPiece;
    private RoyalePlayer royale;
    private RebelPlayer rebel;
    private TileInterface[][] tiles;
    private int turn;
    private int[] coordinate;
    private int[] initTileCoord;
    private int initUndo;
    private boolean isMoving;
    private boolean isAttacking;
    private boolean hasPerformed;
    private int rebelUndoRemain;
    private int royaleUndoRemain;
    private int boardRowLength;
    private int boardColLength;

    public GameEngineFacade(GameFrameView gfv, int undoMoves, RoyalePlayer royale, RebelPlayer rebel) {
        gameInit(gfv);
        hasPerformed = false;
        this.royale = royale;
        this.rebel = rebel;
        rebelUndoRemain = undoMoves;
        royaleUndoRemain = undoMoves;
        initUndo = undoMoves;
    }

    private void gameInit(GameFrameView gfv) {
        PieceCache.generatePieceMap(gfv.getRebelName(), gfv.getRoyaleName());
        this.gfv = gfv;
        tiles = new TileInterface[BOARD_ROW_LENGTH][BOARD_COL_LENGTH];
        for (int row = 0; row < BOARD_ROW_LENGTH; row++) {
            for (int col = 0; col < BOARD_COL_LENGTH; col++) {
                //creates new wall tile
                if ((row % BRICK_ROW_FACTOR <= BRICK_ROW_LENGTH) && col % BRICK_COL_FACTOR == BRICK_COL_INDEX) {
                    setTile(row, col, WALL_TILE);
                }
                //creates new castle tile
                else if (row == 0) {
                    setTile(row, col, CASTLE_TILE);
                }
                //creates new grass tile
                else {
                    setTile(row, col, GRASS_TILE);
                }
            }
        }
        initTileCoord = new int[COORDINATE_LENGTH];
        // Initialize current turn
        turn = REBEL_TURN;
        isMoving = false;
        isAttacking = false;
        boardRowLength = BOARD_ROW_LENGTH;
        boardColLength = BOARD_COL_LENGTH;
    }

    // Check if piece can attack target from current tile
    private boolean checkAttackTarget(PieceInterface piece, int tgRow, int tgTile) {
        TileInterface space = getTile(tgRow, tgTile);
        String inFaction = piece.getFaction();
        String outFaction = space.getPiece().getFaction();
        return (space instanceof PieceTile || space instanceof CastleTile) && !(inFaction.equals(outFaction));
    }

    public int getRebelTurn() {
        return REBEL_TURN;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    // Reset the attacking mode, the colour of the tiles and the cursor icon
    public void resetAttacking() {
        isAttacking = false;
        gfv.resetCursor();
        gfv.decolour();
        depaintAction();
    }

    // Set the mode for attacking
    public void setAttacking() {
        if (isFactionMatched(coordinate[ROW_INDEX], coordinate[COL_INDEX])) {
            isAttacking = true;
            setInit(coordinate[ROW_INDEX], coordinate[COL_INDEX]);
            gfv.setCursor(gfv.getTargetRed());
            int row = coordinate[ROW_INDEX];
            int col = coordinate[COL_INDEX];
            // Paint different colour for movement or attack range
            paintActionRange(row, col, ATTACK_TYPE);
        }
        // Attempt to attack opposite player's piece
        else {
            gfv.updateStatus(CANNOT_ATTACK_OPPONENT_PIECE);
        }
    }

    // Reset the moving mode, the colour of the tiles and the cursor icon
    public void resetMoving() {
        isMoving = false;
        gfv.decolour();
        depaintAction();
        gfv.resetCursor();
    }

    // Enter the moving mode
    public void setMoving() {
        if (isFactionMatched(coordinate[ROW_INDEX], coordinate[COL_INDEX])) {
            isMoving = true;
            setInit(coordinate[ROW_INDEX], coordinate[COL_INDEX]);
            gfv.setCursor(gfv.getImage());
            int row = coordinate[ROW_INDEX];
            int col = coordinate[COL_INDEX];
            paintActionRange(row, col, MOVEMENT_TYPE);
        }
        // Attempt to move opposite player's piece
        else {
            gfv.updateStatus(CANNOT_MOVE_OPPONENT_PIECE);
        }
    }

    public boolean getPerformed() {
        return hasPerformed;
    }

    //called when the end turn button is clicked and changes the turn, including repainting the GUI
    //with appropriate player and game info
    public void unsetPerformed() {
        hasPerformed = false;
        gfv.decolourEndTurn();
        gfv.decolour();
        cycleTurn();
        if (getTurn() == REBEL_TURN) {
            royale.increaseCP();
            gfv.updatePlayerInfo(rebel);
        }
        else if (getTurn() == ROYALE_TURN) {
            rebel.increaseCP();
            gfv.updatePlayerInfo(royale);
        }
        gfv.resetCursor();
        gfv.getAttackBtn().setVisible(true);
        gfv.updateStatus("");
        depaintAction();
        resetAttacking();
        resetMoving();
        reset();
    }

    public int[] getCoordinates() {
        return coordinate;
    }

    public void clickTile(int row, int col) {
        coordinate[ROW_INDEX] = row;
        coordinate[COL_INDEX] = col;
        // TODO: SHOULD MAKE A PIECE STATUS PANEL TO SHOW ALL INFO
        if (isPieceTile(row, col)) {
            gfv.updateStatus(getPiece(row, col).getName() + PIECE_SELECTED);
        }
        boolean match = isFactionMatched(row, col);
        if (match && !hasPerformed) {
            gfv.setImage(gfv.getTile(row, col).getName());
            gfv.colourTile(gfv.getTile(row, col));
            // Response for movement when a tile is clicked
            if (!isMoving() && hasCoordinates() && checkOnBoardPieceMoveable(getCoordinates()[ROW_INDEX], getCoordinates()[COL_INDEX])) {
                // Trigger movement for a piece
                resetAttacking();
                setMoving();
            }
            else if (isMoving() && !hasPerformed) {
                // Cancel movement (click move button twice)
                resetMoving();
                gfv.colourAttack();
            }
            else if (hasPerformed) {
                gfv.updateStatus(HAS_PERFORMED);
            }
            else {
                resetAttacking();
                if (getPiece(row, col) instanceof Artillery) {
                    gfv.updateStatus(getPiece(row, col).getName() + PIECE_SELECTED);
                }
                else {
                    gfv.updateStatus(INVALID_TILE);
                }
            }
            if (checkOnBoardPieceAttackable(row, col)) {
                gfv.colourAttack();
            }
            else {
                gfv.colourRedAttack();
            }
        }
        else if (hasPerformed) {
            gfv.decolour();
        }
        else {
            gfv.colourRed(gfv.getTile(row, col));
        }
    }

    public void resetCoordinates() {
        coordinate = null;
        coordinate = new int[COORDINATE_LENGTH];
    }

    public boolean hasCoordinates() {
        return coordinate != null;
    }

    public int getRowIndex() {
        return ROW_INDEX;
    }

    public int getColIndex() {
        return COL_INDEX;
    }

    public int getMaxRows() {
        return BOARD_ROW_LENGTH;
    }

    public int getMaxCols() {
        return BOARD_COL_LENGTH;
    }

    public int getTurn() {
        return turn;
    }

    public Piece getSummonedPiece() {
        return summonedPiece;
    }

    public void removeSummonedPiece() {
        summonedPiece = null;
    }

    // Check if piece in current tile can move
    public boolean checkOnBoardPieceMoveable(int row, int tile) {
        return isPieceTile(row, tile) && getTile(row, tile).getPiece().isMoveable();
    }

    // Check if piece in current tile can attack
    public boolean checkOnBoardPieceAttackable(int row, int tile) {
        return isPieceTile(row, tile) && getTile(row, tile).getPiece().isAttackable();
    }

    @Override
    public void createSummonedPiece(String pieceName) {
        summonedPiece = PieceCache.clonePiece(pieceName);
    }

    // TODO: If not enough CP, wrong status msg
    public boolean placeSummonedPiece(int row, int col) {
        if (checkSummonValid(getSummonedPiece(), row, col)) {
            gfv.setTileIcon(row, col, gfv.getImage());
            gfv.setTileName(row, col, gfv.getImage());
            if (getTurn() == REBEL_TURN) {
                if (rebel.reduceCP(getSummonedPiece().getCp())) {
                    gfv.updatePlayerInfo(rebel);
                }
                else {
                    gfv.updateStatus(NOT_ENOUGH_CP);
                    return false;
                }
            }
            else if (getTurn() == ROYALE_TURN) {
                if (royale.reduceCP(getSummonedPiece().getCp())) {
                    gfv.updatePlayerInfo(royale);
                }
                else {
                    gfv.updateStatus(NOT_ENOUGH_CP);
                    return false;
                }
            }
            removeSummonedPiece();
            gfv.resetCursor();
            setPerformed();
            changeButtonViews();
            depaintAction();
            return true;
        }
        else {
            gfv.updateStatus(INVALID_PLACEMENT);
            return false;
        }
    }

    public boolean placeMovedPiece(int row, int col) {
        // Target tile
        if (move(getInitTileCoord()[ROW_INDEX], getInitTileCoord()[COL_INDEX], row, col)) {
            gfv.decolour();
            gfv.setTileIcon(row, col, gfv.getImage());
            gfv.setTileIcon(getInitTileCoord()[ROW_INDEX], getInitTileCoord()[COL_INDEX], gfv.getGrass());
            resetMoving();
            setPerformed();
            changeButtonViews();
            gfv.setTileName(row, col, gfv.getImage());
            gfv.resetCursor();
            return true;
        }
        else {
            gfv.updateStatus(INVALID_TILE);
            gfv.decolour();
            resetMoving();
            return false;
        }
    }

    public boolean isCastleTile(int row, int col) {
        return getTile(row, col) instanceof CastleTile;
    }

    //checks if the target tile is valid to be attacked/moved into by the selected peicee
    private boolean isMovRangeValid(int inRow, int inTile, int tgRow, int tgTile, String type) {
        int rowDiff = abs(inRow - tgRow);
        int tileDiff = abs(inTile - tgTile);
        PieceInterface piece = getPiece(inRow, inTile);
        return piece.isActionValid(rowDiff, tileDiff, type);
    }

    public boolean isPieceTile(int row, int col) {
        return getTile(row, col) instanceof PieceTile;
    }

    // Check if piece hasPerformed from current tile to target tile
    private boolean move(int inRow, int inTile, int tgRow, int tgTile) {
        if (checkMoveTarget(tgRow, tgTile) && isMovRangeValid(inRow, inTile, tgRow, tgTile, MOVEMENT_TYPE)) {
            // Check if a piece will go across opposite piece no matter what directions, which is forbidden
            if (checkAcross(inRow, inTile, tgRow, tgTile)) {
                return false;
            }
            setTile(tgRow, tgTile, PIECE_TILE);
            ((PieceTile) getTile(tgRow, tgTile)).setPiece(getPiece(inRow, inTile));
            setTile(inRow, inTile, GRASS_TILE);
            return true;
        }
        return false;
    }

    // Check if piece hasAttacked target piece
    private boolean attack(int inRow, int inTile, int tgRow, int tgTile) {
        if ((isPieceTile(tgRow, tgTile) || isCastleTile(tgRow, tgTile)) && checkAttackTarget(getPiece(inRow, inTile), tgRow, tgTile) && isMovRangeValid(inRow, inTile, tgRow, tgTile, ATTACK_TYPE)) {
            getPiece(tgRow, tgTile).attackedBy(getPiece(inRow, inTile).getAttackPower());
            return true;
        }
        return false;
    }

    public void setOffensive() {
        if (coordinate != null) {
            setInit(coordinate[ROW_INDEX], coordinate[COL_INDEX]);
            int initTileRow = initTileCoord[ROW_INDEX];
            int initTileCol = initTileCoord[COL_INDEX];
            TileInterface currentTile = getTile(initTileRow, initTileCol);
            if (currentTile instanceof PieceTile) {
                if (isFactionMatched(initTileRow, initTileCol)) {
                    PieceInterface originalPiece = getPiece(initTileRow, initTileCol);
                    if (originalPiece instanceof Obstacle) {
                        gfv.updateStatus(CANNOT_STRENGTHEN_OBSTACLE);
                    }
                    else {
                        if (!originalPiece.isOffensive()) {
                            PieceInterface resetPiece = new ResetModeDecoratorFactory(originalPiece).getFactory();
                            PieceInterface offensivePiece = new AttackPowerBuffDecoratorFactory(new DefenceNerfDecoratorFactory(resetPiece).getFactory()).getFactory();
                            offensivePiece.isOffensive();
                            ((PieceTile) getTile(initTileRow, initTileCol)).setPiece(offensivePiece);
                        }
                        else {
                            PieceInterface resetPiece = new ResetModeTroopDecorator(originalPiece);
                            resetPiece.isOffensive();
                            ((PieceTile) getTile(initTileRow, initTileCol)).setPiece(resetPiece);
                        }
                    }
                }
                else {
                    gfv.updateStatus(CANNOT_STRENGTHEN_OPPONENT_PIECE);
                }
            }
            else {
                gfv.updateStatus(INVALID_TILE);
            }
        }
        else {
            gfv.updateStatus(INVALID_TILE);
        }
    }

    private void setInit(int row, int col) {
        initTileCoord[ROW_INDEX] = row;
        initTileCoord[COL_INDEX] = col;
    }

    // Check if the player and the piece on action is in the same team
    private boolean isFactionMatched(int row, int col) {
        return turn == REBEL_TURN && getPiece(row, col).getFaction().equals(REBEL) || turn == ROYALE_TURN && getPiece(row, col).getFaction().equals(ROYALE);
    }

    public void setDefensive() {
        if (coordinate != null) {
            setInit(coordinate[ROW_INDEX], coordinate[COL_INDEX]);
            int initTileRow = initTileCoord[ROW_INDEX];
            int initTileCol = initTileCoord[COL_INDEX];
            TileInterface currentTile = getTile(initTileRow, initTileCol);
            if (currentTile instanceof PieceTile) {
                if (isFactionMatched(initTileRow, initTileCol)) {
                    PieceInterface originalPiece = getPiece(initTileRow, initTileCol);
                    if (originalPiece instanceof Obstacle) {
                        gfv.updateStatus(CANNOT_STRENGTHEN_OBSTACLE);
                    }
                    else {
                        if (!originalPiece.isDefensive()) {
                            PieceInterface resetPiece = new ResetModeDecoratorFactory(originalPiece).getFactory();
                            PieceInterface defensivePiece = new DefenceBuffDecoratorFactory(new AttackPowerNerfDecoratorFactory(resetPiece).getFactory()).getFactory();
                            defensivePiece.isDefensive();
                            ((PieceTile) getTile(initTileRow, initTileCol)).setPiece(defensivePiece);
                        }
                        else {
                            PieceInterface resetPiece = new ResetModeTroopDecorator(originalPiece);
                            resetPiece.isDefensive();
                            ((PieceTile) getTile(initTileRow, initTileCol)).setPiece(resetPiece);
                        }
                    }
                }
                else {
                    gfv.updateStatus(CANNOT_STRENGTHEN_OPPONENT_PIECE);
                }
            }
            else {
                gfv.updateStatus(INVALID_TILE);
            }
        }
        else {
            gfv.updateStatus(INVALID_TILE);
        }
    }

    public TileInterface[][] getTiles() {
        return tiles;
    }

    public void setTileIcon(ArrayList<String[]> tileList) {
        for (String[] tile : tileList) {
            int row = Integer.valueOf(tile[ROW_INDEX_LOADED]);
            int col = Integer.valueOf(tile[COL_INDEX_LOADED]);
            String imageName = tile[IMG_NAME_INDEX_LOADED];
            gfv.setTileIcon(row, col, gfv.getImagePath(imageName));
        }
    }

    public int[] getInitTileCoord() {
        return initTileCoord;
    }

    // TODO: MAGIC NUM
    private boolean checkSummonValid(Piece piece, int row, int tile) {
        boolean isRowValid;
        int extraMove = DEFAULT_EXTRA_MOVE;
        int summonRange;
        int cp;
        if (piece instanceof Obstacle) {
            extraMove = OBSTACLE_EXTRA_SUMMON_LIMIT;
        }
        if (piece instanceof Royale) {
            summonRange = ROYALE_SUMMON_SOUTH_LIMIT + extraMove;
            isRowValid = row <= summonRange && row >= ROYALE_SUMMON_NORTH_LIMIT;
            cp = royale.getCP();
        }
        else {
            isRowValid = row >= REBEL_SUMMON_NORTH_LIMIT - extraMove;
            cp = rebel.getCP();
        }
        if (cp >= piece.getCp() && checkMoveTarget(row, tile) && isRowValid) {
            setTile(row, tile, PIECE_TILE);
            ((PieceTile) tiles[row][tile]).setPiece(piece);
            return true;
        }
        else {
            return false;
        }
    }

    // check if a certain tile should be repainted with the paintMovAttack
    private boolean checkMoveRepaint(int row, int col) {
        try {
            return !isWallTile(row, col) && isGrassTile(row, col) && !isCastleTile(row, col);
        } catch (RuntimeException e) {
            return true;
        }
    }

    @Override
    public int[] getUndoLevel() {
        return new int[]{rebelUndoRemain, royaleUndoRemain};
    }

    @Override
    public Player getRebelPlayer() {
        return rebel;
    }

    @Override
    public Player getRoyalePlayer() {
        return royale;
    }

    // Check if piece can move from current tile to target tile
    private boolean checkMoveTarget(int row, int tile) {
        return getTile(row, tile) instanceof GrassTile && !isCastleTile(row, tile);
    }

    public boolean saveGame() {
        try {
            PrintWriter output = new PrintWriter(new FileWriter(FULL_SAVE_FILE_NAME));
            output.println(getCastleHp());
            output.println(BOARD_ROW_LENGTH + SEPARATOR + BOARD_COL_LENGTH);
            for (String data : gfv.getPlayerData()) {
                output.print(data + SEPARATOR);
            }
            output.println();
            int[] undoLimit = getUndoLevel();
            output.println(undoLimit[REBEL_TURN] + SEPARATOR + undoLimit[ROYALE_TURN]);
            output.println(getTurn());
            output.println(getPerformed());
            for (TileInterface[] tileRow : getTiles()) {
                for (TileInterface tile : tileRow) {
                    if (tile instanceof PieceTile) {
                        PieceInterface piece = tile.getPiece();
                        output.printf(OUTPUT_FORMAT, tile.getRow(), tile.getCol(), piece.getName(), piece.getHp(), piece.isOffensive(), piece.isDefensive());
                    }
                }
            }
            output.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void loadGame(String castleHp, String[] undoLimit, String turn, String hasPerformed, ArrayList<String[]> tileList) {
        tiles[CASTLE_TILE_ROW][CASTLE_TILE_COL].getPiece().setHP(Integer.parseInt(castleHp));
        this.hasPerformed = Boolean.parseBoolean(hasPerformed);
        rebelUndoRemain = Integer.parseInt(undoLimit[REBEL_TURN]);
        royaleUndoRemain = Integer.parseInt(undoLimit[ROYALE_TURN]);
        this.turn = Integer.parseInt(turn);
        PieceInterface onBoardPiece;
        for (String[] tile : tileList) {
            int row = Integer.valueOf(tile[ROW_INDEX_LOADED]);
            int col = Integer.valueOf(tile[COL_INDEX_LOADED]);
            String imageName = tile[IMG_NAME_INDEX_LOADED];
            int hp = Integer.valueOf(tile[HP_INDEX_LOADED]);
            boolean isOffensive = Boolean.parseBoolean(tile[OFFENSIVE_INDEX_LOADED]);
            boolean isDefensive = Boolean.parseBoolean(tile[DEFENSIVE_INDEX_LOADED]);
            onBoardPiece = PieceCache.clonePiece(imageName);
            onBoardPiece.setHP(hp);
            PieceTile pieceTile = new PieceTile(row, col);
            pieceTile.setPiece(onBoardPiece);
            tiles[row][col] = pieceTile;
            if (isDefensive) {
                PieceInterface resetPiece = new ResetModeDecoratorFactory(onBoardPiece).getFactory();
                PieceInterface defensivePiece = new DefenceBuffDecoratorFactory(new AttackPowerNerfDecoratorFactory(resetPiece).getFactory()).getFactory();
                defensivePiece.isDefensive();
                ((PieceTile) tiles[row][col]).setPiece(defensivePiece);
            }
            else if (isOffensive) {
                PieceInterface resetPiece = new ResetModeDecoratorFactory(onBoardPiece).getFactory();
                PieceInterface offensivePiece = new AttackPowerBuffDecoratorFactory(new DefenceNerfDecoratorFactory(resetPiece).getFactory()).getFactory();
                offensivePiece.isOffensive();
                ((PieceTile) tiles[row][col]).setPiece(offensivePiece);
            }
        }
    }

    private void depaintAction() {
        for (int row = 0; row < BOARD_ROW_LENGTH; row++) {
            for (int col = 0; col < BOARD_COL_LENGTH; col++) {
                if (isGrassTile(row, col) && !isPieceTile(row, col)) {
                    gfv.setTileIcon(row, col, gfv.getGrass());
                }
            }
        }
    }

    public boolean isWallTile(int row, int col) {
        return getTile(row, col) instanceof WallTile;
    }

    public boolean isGrassTile(int row, int col) {
        return getTile(row, col) instanceof GrassTile;
    }

    public int getCastleHp() {
        return tiles[CASTLE_TILE_ROW][CASTLE_TILE_COL].getPiece().getHp();
    }

    public void changeAttackIconColor(TileInterface tile, int row, int col) {
        if (isPieceTile(row, col) || isCastleTile(row, col)) {
            if (!isFactionMatched(row, col)) {
                if (isMovRangeValid(initTileCoord[ROW_INDEX], initTileCoord[COL_INDEX], row, col, ATTACK_TYPE)) {
                    gfv.setCursor(gfv.getTargetGreen());
                }
                else {
                    gfv.setCursor(gfv.getTargetRed());
                }
            }
            else {
                gfv.setCursor(gfv.getTargetRed());
            }
        }
        else {
            gfv.setCursor(gfv.getTargetRed());
        }
    }

    public TurnType placeAttackPiece(int row, int col) {
        if (attack(getInitTileCoord()[ROW_INDEX], getInitTileCoord()[COL_INDEX], row, col)) {
            boolean death;
            PieceInterface p = getTile(row, col).getPiece();
            PieceInterface piece = getPiece(getInitTileCoord()[ROW_INDEX], getInitTileCoord()[COL_INDEX]);
            String statusMsg;
            int prevHp = getPiece(row, col).getHp();
            String pName = gfv.getTile(row, col).getName();
            int trueDamage = piece.getAttackPower() - getPiece(row, col).getDefence();
            if (trueDamage < NO_DAMAGE_DEALT) {
                trueDamage = NO_DAMAGE_DEALT;
            }
            statusMsg = trueDamage + TRUE_DAMAGE_DEALT + getPiece(row, col).getName() + REMAINING_HP + getPiece(row, col).getHp();
            death = getPiece(row, col).isDead();
            if (getTile(row, col) instanceof CastleTile) {
                gfv.updateCastleHp();
                if (death) {
                    gameOver(rebel.getName());
                }
            }
            else {
                if (death) {
                    statusMsg += SQUARE_BRACKET_LEFT + p.getName() + IS_DEAD;
                    setTile(row, col, GRASS_TILE);
                    gfv.setTileIcon(row, col, gfv.getGrass());
                }
            }
            gfv.decolour();
            resetAttacking();
            setPerformed();
            gfv.getAttackBtn().setVisible(false);
            gfv.resetCursor();
            gfv.updateStatus(statusMsg);
            return new TurnType(pName, getInitTileCoord()[ROW_INDEX], getInitTileCoord()[COL_INDEX], row, col, trueDamage, death, prevHp, p);
        }
        else {
            gfv.updateStatus(INVALID_TILE + CANCEL_ATTACK);
            return null;
        }
    }

    public void paintSummonRange(String pieceName) {
        int start;
        int finish;
        int extraMove = DEFAULT_EXTRA_MOVE;
        if (PieceCache.getPiece(pieceName) instanceof Obstacle) {
            extraMove = OBSTACLE_EXTRA_SUMMON_LIMIT;
        }
        if (turn == ROYALE_TURN) {
            start = ROYALE_SUMMON_NORTH_LIMIT;
            finish = ROYALE_SUMMON_SOUTH_LIMIT + extraMove;
        }
        else {
            start = REBEL_SUMMON_NORTH_LIMIT - extraMove;
            finish = boardRowLength - 1;
        }
        for (int row = start; row <= finish; row++) {
            for (int col = 0; col < boardColLength; col++) {
                if (checkMoveRepaint(row, col)) {
                    gfv.colourTile(row, col, SUMMON_TYPE);
                }
            }
        }
    }

    public TileInterface getTile(int row, int col) {
        return tiles[row][col];
    }

    // Gets a piece from a tile
    public PieceInterface getPiece(int row, int tile) {
        return getTile(row, tile).getPiece();
    }

    public boolean checkUndoRemain() {
        if (getTurn() == REBEL_TURN && rebelUndoRemain != 0) {
            rebelUndoRemain--;
            return true;
        }
        else if (getTurn() == ROYALE_TURN && royaleUndoRemain != 0) {
            royaleUndoRemain--;
            return true;
        }
        else if(initUndo > 0) {
        	gfv.updateStatus(UNDO_USED);
        }
        else {
        	gfv.updateStatus(UNDO_NONE);
        }
        return false;
    }

    public void notifyUndoRule() {
        if (initUndo > 0) {
            gfv.updateStatus(UNDO_RULE);
        }
    }

    public void undoTurn(CommandInterface cI) {
        TurnType turndetails = cI.returnTurnDetails();
        if (cI instanceof MoveCommand) {
            setTile(turndetails.fromRow(), turndetails.fromCol(), PIECE_TILE);
            ((PieceTile) getTile(turndetails.fromRow(), turndetails.fromCol())).setPiece(getPiece(turndetails.tooRow(), turndetails.tooCol()));
            setTile(turndetails.tooRow(), turndetails.tooCol(), GRASS_TILE);
            gfv.setTileIcon(turndetails.tooRow(), turndetails.tooCol(), gfv.getGrass());
            gfv.setTileIcon(turndetails.fromRow(), turndetails.fromCol(), turndetails.returnImage());
            gfv.setTileName(turndetails.fromRow(), turndetails.fromCol(), turndetails.returnImage());
        }
        else if (cI instanceof SummonCommand) {
            setTile(turndetails.tooRow(), turndetails.tooCol(), GRASS_TILE);
            gfv.setTileIcon(turndetails.tooRow(), turndetails.tooCol(), gfv.getGrass());
        }
        else if (cI instanceof AttackCommand) {
            if (turndetails.death()) {
                gfv.setTileIcon(turndetails.tooRow(), turndetails.tooCol(), turndetails.returnImage());
                gfv.setTileName(turndetails.tooRow(), turndetails.tooCol(), turndetails.returnImage());
                setTile(turndetails.tooRow(), turndetails.tooCol(), PIECE_TILE);
                ((PieceTile) getTile(turndetails.tooRow(), turndetails.tooCol())).setPiece(turndetails.returnPiece());
                getPiece(turndetails.tooRow(), turndetails.tooCol()).setHP(turndetails.prevHp());
            }
            else {
                getPiece(turndetails.tooRow(), turndetails.tooCol()).setHP(getPiece(turndetails.tooRow(), turndetails.tooCol()).getHp() + turndetails.damageDealt());
            }
        }
        gfv.decolour();
        hasPerformed = false;
        gfv.getAttackBtn().setVisible(true);
    }

    private void setTile(int row, int col, String type) {
        if (type.equals(PIECE_TILE)) {
            tiles[row][col] = new PieceTile(row, col);
        }
        else {
            tiles[row][col] = TileFactory.getTile(type);
        }
    }

    // TODO: MAGIC NUM
    @Override
    public boolean checkWin() {
        if (turn == REBEL_TURN) {
            if (rebel.getCP() < REBEL_PIECE_CP_MIN) {
                for (int row = 0; row < boardRowLength; row++) {
                    for (int col = 0; col < boardColLength; col++) {
                        if (getTile(row, col) instanceof PieceTile) {
                            if (isFactionMatched(row, col)) {
                                return false;
                            }
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    public void gameOver(String name) {
        gfv.gameOver(name);
        gfv.disposeFrame();
        new MainMenuView();
    }

    private void setPerformed() {
        hasPerformed = true;
        gfv.colourEndTurn();
    }

    private void changeButtonViews() {
        gfv.getAttackBtn().setVisible(false);
        gfv.getUndoBtn().setVisible(true);
    }

    // want to change colour for showing movement and attack range on tiles
    private void paintActionRange(int pieceRow, int pieceCol, String actionType) {
        PieceInterface piece = getPiece(coordinate[ROW_INDEX], coordinate[COL_INDEX]);
        int radius = piece.getActionRange(actionType);
        for (int row = -radius; row <= radius; row++) {
            for (int col = -radius; col <= radius; col++) {
                if (checkMoveRepaint(pieceRow + row, pieceCol + col) && piece.isActionValid(abs(pieceRow - abs(pieceRow + row)), abs(pieceCol - abs(pieceCol + col)), actionType)) {
                    if (!(actionType.equals(MOVEMENT_TYPE) && checkAcross(pieceRow, pieceCol, pieceRow + row, pieceCol + col))) {
                        gfv.colourTile(pieceRow + row, pieceCol + col, actionType);
                    }
                }
            }
        }
    }

    private boolean checkAcross(int inRow, int inTile, int tgRow, int tgTile) {
        TileInterface currTile = null;
        if (inTile == tgTile) {
            TileInterface initTile = getTile(inRow, inTile);
            for (int row = 1; row < Math.abs(inRow - tgRow) + 1; row++) {
                if (tgRow < inRow && inRow - row >= 0) {
                    currTile = getTile(inRow - row, inTile);
                }
                else if (inRow + row < boardRowLength) {
                    currTile = getTile(inRow + row, inTile);
                }
                if (currTile != null) {
                    if (currTile instanceof PieceTile) {
                        if (!currTile.getPiece().getFaction().equals(initTile.getPiece().getFaction())) {
                            return true;
                        }
                    }
                    else if (currTile instanceof WallTile) {
                        return true;
                    }
                }
            }
        }
        else if (inRow == tgRow) {
            TileInterface initTile = getTile(inRow, inTile);
            for (int col = 1; col < Math.abs(inTile - tgTile) + 1; col++) {
                if (tgTile < inTile && inTile - col >= 0) {
                    currTile = getTile(inRow, inTile - col);
                }
                else if (inTile + col < boardColLength) {
                    currTile = getTile(inRow, inTile + col);
                }
                if (currTile != null) {
                    if (currTile instanceof PieceTile) {
                        if (!currTile.getPiece().getFaction().equals(initTile.getPiece().getFaction())) {
                            return true;
                        }
                    }
                    else if (currTile instanceof WallTile) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //resets the saved coordinate of a tile and the summoned piece which are saved in the gameEngine
    private void reset() {
        resetCoordinates();
        initTileCoord = null;
        initTileCoord = new int[COORDINATE_LENGTH];
        removeSummonedPiece();
    }

    // To make sure the player's turn has cycled
    private void cycleTurn() {
        for (int i = 0; i < turns.length; i++) {
            if (turn == turns[i]) {
                if (turns[i] != turns[turns.length - 1]) {
                    turn = turns[i + 1];
                    return;
                }
                else {
                    turn = turns[REBEL_TURN];
                    return;
                }
            }
        }
    }
}
