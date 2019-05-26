package model.gameEngine;

import controller.commandPattern.AbstractTurn;
import controller.commandPattern.MoveCommand;
import controller.commandPattern.SummonCommand;
import controller.commandPattern.TurnType;
import model.piece.AbtractPiece.Piece;
import model.piece.AbtractPiece.PieceInterface;
import model.piece.PieceCache;
import model.piece.abstractType.Obstacle;
import model.piece.decorator.concreteDecorator.ResetModeTroopDecorator;
import model.piece.decorator.concreteDecoratorFactory.*;
import model.piece.faction.Royale;
import model.player.Player;
import model.player.RebelPlayer;
import model.player.RoyalePlayer;
import view.gameView.GameFrameView;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Stack;

import static java.lang.Math.abs;

public class GameEngineFacade implements GameEngine {

    public static final String FULL_SAVE_FILE_NAME = "gamedata.save";
    public static final int ROW_INDEX = 0;
    public static final int COL_INDEX = 1;
    public static final int REBEL_TURN = 0;

    private static final int ROYALE_TURN = 1;
    private static final int ROW_INDEX_LOADED = 0;
    private static final int COL_INDEX_LOADED = 1;
    private static final int IMG_NAME_INDEX_LOADED = 2;
    private static final int HP_INDEX_LOADED = 3;
    private static final int COORDINATE_LENGTH = 2;
    private static final int ORIGINAL_ROW = 0;
    private static final int ORIGINAL_COL = 0;
    private static final int DEFAULT_EXTRA_MOVE = 0;
    private static final int ROYALE_SUMMON_NORTH_LIMIT = 1;
    private static final int ROYALE_SUMMON_SOUTH_LIMIT = 3;
    private static final int REBEL_SUMMON_NORTH_LIMIT = 9;
    private static final int OBSTACLE_EXTRA_SUMMON_LIMIT = 3;
    private static final int BRICK_ROW_FACTOR = 5;
    private static final int BRICK_ROW_LENGTH = 3;
    private static final int BRICK_COL_FACTOR = 4;
    private static final int BRICK_COL_INDEX = 3;
    private static final int NO_DAMAGE_DEALT = 0;
    private static final int DEFAULT_UNDO_LEVEL = 0;
    private static final String CANNOT_ATTACK_OPPONENT_PIECE = "You cannot attack your opponent's piece.";
    private static final String CANNOT_MOVE_OPPONENT_PIECE = "You cannot move your opponent's piece.";
    private static final String PIECE_SELECTED = " selected.";
    private static final String HAS_PERFORMED = "You have already perform an action this turn.";
    private static final String INVALID_TILE = "You have not chosen a valid tile.";
    private static final String INVALID_PLACEMENT = "Invalid placement, the first two rows for Royales and the bottom three rows for Rebels.";
    private static final String TRUE_DAMAGE_DEALT = " true damage dealt!";
    private static final String REMAINING_HP = "Remaining HP: ";
    private static final String INFER = " -> ";
    private static final String SEPARATOR = "|";
    private static final String IS_DEAD = " is DEAD!";
    private static final String CANCEL_ATTACK = " Please click the attack button again to cancel.";
    private static final String CANNOT_STRENGTHEN_OPPONENT_PIECE = "You cannot strengthen opponent piece!";
    private static final String CANNOT_STRENGTHEN_OBSTACLE = "You cannot strengthen opponent piece!";
    private static final String CANNOT_STRENGTHEN_CASTLE = "You cannot strengthen CASTLES!";
    private static final String UNDO_USED = "You have already used your Undo for this game!";
    private static final String OUTPUT_FORMAT = "%d|%d|%s|%d|%n";

    private static final String SUMMON_TYPE = "summon";
    private static final String MOVEMENT_TYPE = "move";
    private static final String ATTACK_TYPE = "attack";
    private static final int[] turns = new int[]{REBEL_TURN, ROYALE_TURN};

    public static int BOARD_ROW_LENGTH; // increments in 5
    public static int BOARD_COL_LENGTH; // increments in 4

    // Stack for storing moves
    private static Stack<AbstractTurn> moves;
    private GameFrameView gfv;
    private Piece summonedPiece;
    private RoyalePlayer royale;
    private RebelPlayer rebel;
    private Tile[][] tiles;
    private int turn;
    private int[] coordinate;
    private int[] initTileCoord;

    private boolean isMoving;
    private boolean isAttacking;
    private boolean hasPerformed;
    private int rebelUndoLv;
    private int royaleUndoLv;
    private int boardRowLength;
    private int boardColLength;

    public GameEngineFacade(GameFrameView gfv, int undoMoves, RoyalePlayer royale, RebelPlayer rebel) {

        gameInit(gfv);
        hasPerformed = false;
        this.royale = royale;
        this.rebel = rebel;
        rebelUndoLv = undoMoves;
        royaleUndoLv = undoMoves;
    }

    private void gameInit(GameFrameView gfv) {

        PieceCache.generatePieceMap(gfv.getRebelName(), gfv.getRoyaleName());
        this.gfv = gfv;

        tiles = new Tile[BOARD_ROW_LENGTH][BOARD_COL_LENGTH];

        for (int row = 0; row < BOARD_ROW_LENGTH; row++) {
            for (int col = 0; col < BOARD_COL_LENGTH; col++) {
                tiles[row][col] = new Tile(row, col);
            }
        }

        initTileCoord = new int[COORDINATE_LENGTH];

        moves = new Stack<>();

        // Initialize current turn
        turn = REBEL_TURN;

        isMoving = false;
        isAttacking = false;

        boardRowLength = BOARD_ROW_LENGTH;
        boardColLength = BOARD_COL_LENGTH;
    }

    public int getOriginalRow() {

        return ORIGINAL_ROW;
    }

    public int getOriginalCol() {

        return ORIGINAL_COL;
    }

    public int getRebelTurn() {

        return REBEL_TURN;
    }

    public int getRoyaleTurn() {

        return ROYALE_TURN;
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

    public boolean getHasPerformed() {

        return hasPerformed;
    }

    //called when the end turn button is clicked and changes the turn, including repainting the GUI
    //with appropriate player and game info
    public void unsetActionPerformed() {

        hasPerformed = false;
        gfv.decolourEndTurn();
        gfv.decolour();
        cycleTurn();
        if (getTurn() == REBEL_TURN) {
            royale.increaseCP();
            gfv.updatePlayerInfo(rebel);
        } else if (getTurn() == ROYALE_TURN) {
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
        if (getTile(row, col).hasPiece()) {
            gfv.updateStatus(getPiece(row, col).getName() + PIECE_SELECTED);
        }

        boolean match = isFactionMatched(row, col);
        if (match && !hasPerformed) {

            gfv.setImage(gfv.getTile(row, col).getName());
            gfv.colourTile(gfv.getTile(row, col));

            // Response for movement when a tile is clicked
            if (!isMoving() && hasCoordinates() && checkMoveInit(getCoordinates()[ROW_INDEX], getCoordinates()[COL_INDEX])) {

                // Trigger movement for a piece
                resetAttacking();
                setMoving();
            } else if (isMoving() && !getHasPerformed()) {

                // Cancel movement (click move button twice)
                resetMoving();
                gfv.colourAttack();
            } else if (getHasPerformed()) {
                gfv.updateStatus(HAS_PERFORMED);
            } else {
                resetAttacking();
                gfv.updateStatus(INVALID_TILE);
            }

            if (checkAttackInit(row, col)) {
                gfv.colourAttack();
            } else {
                gfv.colourRedAttack();
            }
        } else if (hasPerformed) {
            gfv.decolour();
        } else {
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

    public int getRow() {

        return ROW_INDEX;
    }

    public int getCol() {

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

    // Check if piece has been initialized successfully in current tile
    public boolean checkInit(int row, int col) {

        return getTile(row, col).hasPiece();
    }

    // Check if piece in current tile can move
    public boolean checkMoveInit(int row, int col) {

        return checkInit(row, col) && getTile(row, col).getPiece().isMoveable();
    }

    // Check if piece in current tile can attack
    public boolean checkAttackInit(int row, int col) {

        return checkInit(row, col) && getTile(row, col).getPiece().isAttackable();
    }

    @Override
    public void createSummonedPiece(String pieceName) {

        summonedPiece = PieceCache.clonePiece(pieceName);
    }

    public boolean placeSummonedPiece(int row, int col) {

        if (checkSummonValid(getSummonedPiece(), row, col)) {

            gfv.setTileIcon(row, col, gfv.getImage());
            gfv.setTileName(row, col, gfv.getImage());

            if (getTurn() == REBEL_TURN) {
                rebel.reduceCP(getSummonedPiece().getCp());
                gfv.updatePlayerInfo(rebel);
            } else if (getTurn() == ROYALE_TURN) {
                royale.reduceCP(getSummonedPiece().getCp());
                gfv.updatePlayerInfo(royale);
            }

            removeSummonedPiece();
            gfv.resetCursor();
            setPerformed();
            changeButtonViews();
            depaintAction();
            return true;
        } else {
            gfv.updateStatus(INVALID_PLACEMENT);
            return false;
        }
    }

    private boolean checkSummonValid(Piece piece, int row, int col) {

        boolean isRowValid;
        int extraMove = DEFAULT_EXTRA_MOVE;
        int summonRange;

        if (piece instanceof Obstacle) {
            extraMove = OBSTACLE_EXTRA_SUMMON_LIMIT;
        }

        if (piece instanceof Royale) {
            summonRange = ROYALE_SUMMON_SOUTH_LIMIT + extraMove;
            isRowValid = row <= summonRange && row >= ROYALE_SUMMON_NORTH_LIMIT;
        } else {
            isRowValid = row >= REBEL_SUMMON_NORTH_LIMIT - extraMove;
        }

        if (checkMoveTarget(row, col) && isRowValid) {
            getTile(row, col).setPiece(piece);
            return true;
        } else {
            return false;
        }
    }

    // Set the variable that tells the game if a player has performed an action on his turn.
    private void setPerformed() {

        hasPerformed = true;
        gfv.colourEndTurn();
    }

    private void changeButtonViews() {

        gfv.getAttackBtn().setVisible(false);
        gfv.getUndoBtn().setVisible(true);
    }

    // Reset the colour of the tiles back
    private void depaintAction() {

        for (int row = 0; row < BOARD_ROW_LENGTH; row++) {
            for (int col = 0; col < BOARD_COL_LENGTH; col++) {
                if (!isWall(row, col) && !getTile(row, col).hasPiece() && !isCastle(row)) {
                    gfv.setTileIcon(row, col, gfv.getGrass());
                }
            }
        }
    }

    // Check if piece can move from current tile to target tile
    private boolean checkMoveTarget(int row, int col) {

        return !getTile(row, col).hasPiece() && !isCastle(row);
    }

    public boolean placeMovedPiece(int rol, int col) {

        // Target tile
        if (move(getInitTileCoord()[ROW_INDEX], getInitTileCoord()[COL_INDEX], rol, col)) {

            gfv.decolour();
            gfv.setTileIcon(rol, col, gfv.getImage());
            gfv.setTileIcon(getInitTileCoord()[ROW_INDEX], getInitTileCoord()[COL_INDEX], gfv.getGrass());

            resetMoving();
            setPerformed();
            changeButtonViews();
            gfv.setTileName(rol, col, gfv.getImage());
            gfv.resetCursor();

            return true;
        } else {

            gfv.updateStatus(INVALID_TILE);
            gfv.decolour();
            resetMoving();

            return false;
        }
    }

    // TODO: MAGIC NUM (USE FLYWEIGHT)
    public boolean isWall(int row, int col) {

        return row % BRICK_ROW_FACTOR < BRICK_ROW_LENGTH && col % BRICK_COL_FACTOR == BRICK_COL_INDEX;
    }

    public boolean placeAttackPiece(int row, int col) {

        if (attack(getInitTileCoord()[ROW_INDEX], getInitTileCoord()[COL_INDEX], row, col)) {

            PieceInterface piece = getPiece(getInitTileCoord()[ROW_INDEX], getInitTileCoord()[COL_INDEX]);
            String statusMsg;
            int trueDamage = piece.getAttackPower() - getPiece(row, col).getDefence();

            if (trueDamage < NO_DAMAGE_DEALT) {
                trueDamage = NO_DAMAGE_DEALT;
            }

            statusMsg = trueDamage + TRUE_DAMAGE_DEALT + REMAINING_HP + getPiece(row, col).getHp();

            if (getPiece(row, col).isDead()) {
                statusMsg += INFER + getPiece(row, col).getName() + IS_DEAD;
                getTile(row, col).removePiece();
                gfv.setTileIcon(row, col, gfv.getGrass());
            }

            gfv.decolour();
            resetAttacking();
            setPerformed();
            gfv.getAttackBtn().setVisible(false);
            gfv.resetCursor();
            gfv.updateStatus(statusMsg);

            return true;
        } else {
            gfv.updateStatus(INVALID_TILE + CANCEL_ATTACK);

            return false;
        }
    }

    public void setOffensive() {

        if (coordinate != null) {
            setInit(coordinate[ROW_INDEX], coordinate[COL_INDEX]);
            if (checkInit(getInitTileCoord()[ROW_INDEX], getInitTileCoord()[COL_INDEX])) {
                if (!(getPiece(getInitTileCoord()[ROW_INDEX], getInitTileCoord()[COL_INDEX]) instanceof Obstacle)) {
                    if (isFactionMatched(getInitTileCoord()[ROW_INDEX], getInitTileCoord()[COL_INDEX])) {
                        PieceInterface originalPiece = getPiece(getInitTileCoord()[ROW_INDEX], getInitTileCoord()[COL_INDEX]);
                        if (!originalPiece.isOffensive()) {
                            PieceInterface resetPiece = new ResetModeDecoratorFactory(originalPiece).getFactory();
                            PieceInterface offensivePiece = new AttackPowerBuffDecoratorFactory(new DefenceNerfDecoratorFactory(resetPiece).getFactory()).getFactory();
                            offensivePiece.isOffensive();
                            getTile(getInitTileCoord()[ROW_INDEX], getInitTileCoord()[COL_INDEX]).setPiece(offensivePiece);
                        } else {
                            PieceInterface resetPiece = new ResetModeTroopDecorator(originalPiece);
                            resetPiece.isOffensive();
                            getTile(getInitTileCoord()[ROW_INDEX], getInitTileCoord()[COL_INDEX]).setPiece(resetPiece);
                        }
                    } else {
                        gfv.updateStatus(CANNOT_STRENGTHEN_OPPONENT_PIECE);
                    }
                } else {
                    gfv.updateStatus(CANNOT_STRENGTHEN_OBSTACLE);
                }
            }
        } else {
            gfv.updateStatus(INVALID_TILE);
        }
    }

    private void setInit(int row, int col) {

        initTileCoord[ROW_INDEX] = row;
        initTileCoord[COL_INDEX] = col;
    }

    // Gets a piece from a tile
    private PieceInterface getPiece(int row, int col) {

        return getTile(row, col).getPiece();
    }

    // Check if the player and the piece on action is in the same team
    private boolean isFactionMatched(int i, int j) {

        return turn == REBEL_TURN && getPiece(i, j).getFaction().equals("Rebel") || turn == ROYALE_TURN && getPiece(i, j).getFaction().equals("Royale");
    }

    public void setDefensive() {

        if (coordinate != null) {
            setInit(coordinate[ROW_INDEX], coordinate[COL_INDEX]);
            if (checkInit(getInitTileCoord()[ROW_INDEX], getInitTileCoord()[COL_INDEX])) {
                if (!(getPiece(getInitTileCoord()[ROW_INDEX], getInitTileCoord()[COL_INDEX]) instanceof Obstacle)) {
                    PieceInterface originalPiece = getPiece(getInitTileCoord()[ROW_INDEX], getInitTileCoord()[COL_INDEX]);
                    if (isFactionMatched(getInitTileCoord()[ROW_INDEX], getInitTileCoord()[COL_INDEX])) {
                        if (!originalPiece.isDefensive()) {
                            PieceInterface resetPiece = new ResetModeDecoratorFactory(originalPiece).getFactory();
                            PieceInterface defensivePiece = new DefenceBuffDecoratorFactory(new AttackPowerNerfDecoratorFactory(resetPiece).getFactory()).getFactory();
                            defensivePiece.isDefensive();
                            getTile(getInitTileCoord()[ROW_INDEX], getInitTileCoord()[COL_INDEX]).setPiece(defensivePiece);
                        } else {
                            PieceInterface resetPiece = new ResetModeTroopDecorator(originalPiece);
                            resetPiece.isDefensive();
                            getTile(getInitTileCoord()[ROW_INDEX], getInitTileCoord()[COL_INDEX]).setPiece(resetPiece);
                        }
                    } else {
                        gfv.updateStatus(CANNOT_STRENGTHEN_OPPONENT_PIECE);
                    }
                } else {
                    gfv.updateStatus(CANNOT_STRENGTHEN_OBSTACLE);
                }
            }
        } else {
            gfv.updateStatus(INVALID_TILE);
        }
    }

    public Tile[][] getTiles() {

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

    public void pushTurnStack(AbstractTurn turn) {

        moves.push(turn);
    }

    public void undoTurn() {

        if (getTurn() == REBEL_TURN && rebelUndoLv != DEFAULT_UNDO_LEVEL) {
            rebelUndoLv--;
            accessStack();
        } else if (getTurn() == ROYALE_TURN && royaleUndoLv != DEFAULT_UNDO_LEVEL) {
            royaleUndoLv--;
            accessStack();
        } else {
            gfv.updateStatus(UNDO_USED);
        }
    }

    public int[] getInitTileCoord() {

        return initTileCoord;
    }

    public void paintSummonRange(int turn, String pieceName) {

        int startRow;
        int finishRow;
        int extraMove = DEFAULT_EXTRA_MOVE;

        if (PieceCache.getPiece(pieceName) instanceof Obstacle) {
            extraMove = OBSTACLE_EXTRA_SUMMON_LIMIT;
        }

        if (PieceCache.getPiece(pieceName) instanceof Royale) {
            startRow = ROYALE_SUMMON_NORTH_LIMIT;
            finishRow = ROYALE_SUMMON_SOUTH_LIMIT + extraMove;
        } else {
            startRow = REBEL_SUMMON_NORTH_LIMIT - extraMove;
            finishRow = boardRowLength - 1;
        }

        for (int row = startRow; row <= finishRow; row++) {
            for (int col = 0; col < boardColLength; col++) {
                if (checkMoveRepaint(row, col)) {
                    gfv.colourTile(row, col, SUMMON_TYPE);
                }
            }
        }
    }

    // check if a certain tile should be repainted with the paintMovAttack
    private boolean checkMoveRepaint(int row, int col) {

        try {
            return !isWall(row, col) && !getTile(row, col).hasPiece() && !isCastle(row);
        } catch (RuntimeException e) {
            return true;
        }
    }

    @Override
    public int[] getUndoLevel() {

        return new int[]{rebelUndoLv, royaleUndoLv};
    }

    @Override
    public Player getRebelPlayer() {

        return rebel;
    }

    @Override
    public Player getRoyalePlayer() {

        return royale;
    }

    public boolean isCastle(int i) {

        return i == ORIGINAL_ROW;
    }

    public Tile getTile(int row, int col) {

        return tiles[row][col];
    }

    public boolean saveGame() {

        try {
            PrintWriter output = new PrintWriter(new FileWriter(FULL_SAVE_FILE_NAME));
            output.println(BOARD_ROW_LENGTH + SEPARATOR + BOARD_COL_LENGTH);
            for (String data : gfv.getPlayerData()) {
                output.print(data + SEPARATOR);
            }
            output.println();
            int[] undoLimit = getUndoLevel();
            output.println(undoLimit[REBEL_TURN] + SEPARATOR + undoLimit[ROYALE_TURN]);
            output.println(getTurn());
            output.println(getHasPerformed());

            for (Tile[] tileRow : getTiles()) {
                for (Tile tile : tileRow) {
                    if (tile.hasPiece()) {
                        PieceInterface piece = tile.getPiece();
                        output.printf(OUTPUT_FORMAT, tile.getRow(), tile.getCol(), piece.getName(), piece.getHp());
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

    public void loadGame(String[] undoLimit, String turn, String actionPerformed, ArrayList<String[]> tileList) {

        this.hasPerformed = Boolean.parseBoolean(actionPerformed);
        rebelUndoLv = Integer.parseInt(undoLimit[REBEL_TURN]);
        royaleUndoLv = Integer.parseInt(undoLimit[ROYALE_TURN]);
        this.turn = Integer.parseInt(turn);

        Piece onBoardPiece;
        for (String[] tile : tileList) {
            int row = Integer.valueOf(tile[ROW_INDEX_LOADED]);
            int col = Integer.valueOf(tile[COL_INDEX_LOADED]);
            String imageName = tile[IMG_NAME_INDEX_LOADED];
            int hp = Integer.valueOf(tile[HP_INDEX_LOADED]);
            onBoardPiece = PieceCache.clonePiece(imageName);
            onBoardPiece.setHP(hp);
            tiles[row][col].setPiece(onBoardPiece);
        }
    }

    public void changeAttackTarget(Tile tile, int row, int col) {

//        if (isAttacking) {
//            if (tile.hasPiece() && tile.getRow() == i && tile.getCol() == j) {
//                if (!isFactionMatched(i, j)) {
//                    System.out.println(initTileCoord[0] + ", " + initTileCoord[1]);
//                    System.out.println(i + ", " + j);
//                    if (isMovRangeValid(initTileCoord[0], initTileCoord[1], i, j, ATTACK_TYPE)) {
//                        System.out.print("Green");
//                        gfv.setCursor(gfv.getTargetGreen());
//                    } else {
//                        System.out.print("red1");
//                        gfv.setCursor(gfv.getTargetRed());
//                    }
//                } else {
//                    //gfv.setCursor(gfv.getTargetRed());
//                }
//            } else if (!tile.hasPiece() && tile.getRow() == i && tile.getCol() == j) {
//                gfv.setCursor(gfv.getTargetRed());
//            }
//        }
    }

    //Undo stack with all game movements excl attacks for now.
    private void accessStack() {

        for (int i = 0; i < 2; i++) {                //Loop occurs as the round is undone as opposed to each players turn
            if (moves.size() > 0) {
                if (moves.peek().getClass() == MoveCommand.class) {
                    MoveCommand mc = (MoveCommand) moves.pop();
                    TurnType lm = mc.lastMove;
                    int row = lm.tooRow;
                    int col = lm.tooCol;
                    getTile(lm.fromRow, lm.fromCol).setPiece(getPiece(lm.tooRow, lm.tooCol));
                    getTile(lm.tooRow, lm.tooCol).removePiece();
                    gfv.setTileIcon(row, col, gfv.getGrass());
                    gfv.setTileIcon(lm.fromRow, lm.fromCol, lm.image);
                    gfv.setTileName(lm.fromRow, lm.fromCol, gfv.getImage());

                } else {
                    SummonCommand sc = (SummonCommand) moves.pop();
                    TurnType lm = sc.lastMove;
                    int row = lm.tooRow;
                    int col = lm.tooCol;
                    getTile(lm.tooRow, lm.tooCol).removePiece();
                    gfv.setTileIcon(row, col, gfv.getGrass());
                }

            }
        }

        gfv.decolour();
        hasPerformed = false;
        gfv.getAttackBtn().setVisible(true);

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
                } else {
                    turn = turns[REBEL_TURN];
                    return;
                }
            }
        }
    }

    // Check if piece can attack target from current tile
    private boolean checkAttackTarget(PieceInterface piece, int targetRow, int targetCol) {

        Tile space = getTile(targetRow, targetCol);
        String inFaction = piece.getFaction();
        String outFaction = space.getPiece().getFaction();
        return space.hasPiece() && !(inFaction.equals(outFaction));
    }

    //checks if the target tile is valid to be attacked/moved into by the selected peicee
    private boolean isMovRangeValid(int initRow, int initCol, int targetRow, int targetCol, String actionType) {

        int rowDiff = abs(initRow - targetRow);
        int tileDiff = abs(initCol - targetCol);

        PieceInterface piece = getPiece(initRow, initCol);

        return piece.isActionValid(rowDiff, tileDiff, actionType);
    }

    // Check if piece hasPerformed from current tile to target tile
    private boolean move(int initRow, int initCol, int tragetRow, int targetCol) {

        if (checkMoveTarget(tragetRow, targetCol) && isMovRangeValid(initRow, initCol, tragetRow, targetCol, MOVEMENT_TYPE)) {

            // Check if a piece will go across opposite piece no matter what directions, which is forbidden
            if (checkAcross(initRow, initCol, tragetRow, targetCol)) {
                return false;
            }
            getTile(tragetRow, targetCol).setPiece(getPiece(initRow, initCol));
            getTile(initRow, initCol).removePiece();
            return true;
        }
        return false;
    }

    private boolean checkAcross(int initRow, int initCol, int targetRow, int targetCol) {

        Tile currTile = null;
        if (initCol == targetCol) {
            Tile initTile = getTile(initRow, initCol);
            for (int row = 1; row < Math.abs(initRow - targetRow) + 1; row++) {
                if (targetRow < initRow && initRow - row >= 0) {
                    currTile = getTile(initRow - row, initCol);
                } else if (initRow + row < boardRowLength) {
                    currTile = getTile(initRow + row, initCol);
                }
                if (currTile != null) {
                    if (currTile.hasPiece()) {
                        if (!currTile.getPiece().getFaction().equals(initTile.getPiece().getFaction())) {
                            return true;
                        }
                    } else if (isWall(currTile.getRow(), currTile.getCol())) {
                        return true;
                    }
                }
            }
        } else if (initRow == targetRow) {
            Tile initTile = getTile(initRow, initCol);
            for (int col = 1; col < Math.abs(initCol - targetCol) + 1; col++) {
                if (targetCol < initCol && initCol - col >= 0) {
                    currTile = getTile(initRow, initCol - col);
                } else if (initCol + col < boardColLength) {
                    currTile = getTile(initRow, initCol + col);
                }
                if (currTile != null) {
                    if (currTile.hasPiece()) {
                        if (!currTile.getPiece().getFaction().equals(initTile.getPiece().getFaction())) {
                            return true;
                        }
                    } else if (isWall(currTile.getRow(), currTile.getCol())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Check if piece hasAttacked target piece
    private boolean attack(int initRow, int initCol, int targetRow, int targetCol) {

        if (checkInit(targetRow, targetCol) && checkAttackTarget(getPiece(initRow, initCol), targetRow, targetCol) && isMovRangeValid(initRow, initCol, targetRow, targetCol, ATTACK_TYPE)) {
            getPiece(targetRow, targetCol).attackedBy(getPiece(initRow, initCol).getAttackPower());
            return true;
        }
        return false;
    }
}
