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
import static view.gameView.GameFrameView.STATUS;

public class GameEngineFacade implements GameEngine {

    public static final int REBEL_TURN = 0;
    public static final int ROW_INDEX = 0;
    public static final int COL_INDEX = 1;
    public static final String FULL_SAVE_FILE_NAME = "gamedata.save";
    private static final int ROYALE_TURN = 1;
    private static final int ROW_LOADED = 0;
    private static final int COL_LOADED = 1;
    private static final int NAME_LOADED = 2;
    private static final int HP_LOADED = 3;
    private static final int COORDINATE_NUM = 2;
    private static final int ORIGINAL_ROW = 0;
    private static final int ORIGINAL_COL = 0;
    private static final int ROYALE_SUMMON_NORTH_LIMIT = 1;
    private static final int ROYALE_SUMMON_SOUTH_LIMIT = 2;
    private static final int REBEL_SUMMON_NORTH_LIMIT =10;
    private static final int OBSTACLE_EXTRA_SUMMON_LIMIT = 3;
    // TODO: Game model shouldn't have gui component such as icons???
    public static int BOARD_MAX_ROWS; // increments in 5
    public static int BOARD_MAX_COLS; // increments in 4
    // Stack for storing moves
    private GameFrameView gfv;
    private Piece summonedPiece;
    private Piece onBoardPiece;
    private RoyalePlayer royale;
    private RebelPlayer rebel;
    private Tile[][] tiles;
    private int turn;
    private int[] turns;
    private int[] coordinate;
    private int[] initTileCoord;

    private boolean isMoving;
    private boolean isAttacking;
    private boolean hasPerformed;
    private int rebelUndoRem;
    private int royaleUndoRem;
    private int boardRowLength;
    private int boardColLength;

    public GameEngineFacade(GameFrameView gfv, int undoMoves, RoyalePlayer royale, RebelPlayer rebel) {
        gameInit(gfv);
        hasPerformed = false;
        this.royale = royale;
        this.rebel = rebel;
        rebelUndoRem = undoMoves;
        royaleUndoRem = undoMoves;
        turn = REBEL_TURN;
    }

    private void gameInit(GameFrameView gfv) {
        PieceCache.generatePieceMap(gfv.getRebelName(), gfv.getRoyaleName());
        this.gfv = gfv;

        tiles = new Tile[BOARD_MAX_ROWS][BOARD_MAX_COLS];

        for (int i = ORIGINAL_ROW; i < BOARD_MAX_ROWS; i++) {
            for (int j = ORIGINAL_COL; j < BOARD_MAX_COLS; j++) {
                tiles[i][j] = new Tile(i, j);
            }
        }

        initTileCoord = new int[COORDINATE_NUM];



        // Initialize number of player turns
        turns = new int[]{REBEL_TURN, ROYALE_TURN};

        isMoving = false;
        isAttacking = false;

        boardRowLength = BOARD_MAX_ROWS;
        boardColLength = BOARD_MAX_COLS;

        // Initialize current turn;
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

    public void setTileIcon(ArrayList<String[]> tileList) {

        for (String[] tile : tileList) {
            int row = Integer.valueOf(tile[ROW_LOADED]);
            int col = Integer.valueOf(tile[COL_LOADED]);
            String name = tile[NAME_LOADED];
            gfv.setTileIcon(row, col, gfv.getImagePath(name));
        }
    }

    public boolean isMoving() {
        return isMoving;
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    // reset the attacking mode and resets the colour of the tiles + cursor
    public void resetAttacking() {
        isAttacking = false;
        gfv.resetCursor();
        gfv.decolour();
        depaintAction();
    }

    // set the mode for attacking
    public void setAttacking() {
        if (isFactionMatched(coordinate[ROW_INDEX], coordinate[COL_INDEX])) {
            isAttacking = true;
            setInit(coordinate[ROW_INDEX], coordinate[COL_INDEX]);
            gfv.setCursor(gfv.getTargetRed());
            int range = getPiece(coordinate[ROW_INDEX], coordinate[COL_INDEX]).getAttackRange();
            int row = coordinate[ROW_INDEX];
            int col = coordinate[COL_INDEX];

            // paintAttack(); to make diff colour for attack range
            paintMovAttackRange(row, col, "attackRange");

        }

        // Attempt to move opposite player's piece
        else {
            gfv.getStatusLabel().setText(STATUS + "You cannot move your opponent's piece.");
        }
    }

    //reset the moving mode, and resets the colour of the tiles + cursor
    public void resetMoving() {
        isMoving = false;
        gfv.decolour();
        depaintAction();
        gfv.resetCursor();
    }

    //enter the moving mode
    public void setMoving() {
        if (isFactionMatched(coordinate[ROW_INDEX], coordinate[COL_INDEX])) {
            isMoving = true;
            setInit(coordinate[ROW_INDEX], coordinate[COL_INDEX]);
            gfv.setCursor(gfv.getImage());

            int mov = getPiece(coordinate[ROW_INDEX], coordinate[COL_INDEX]).getMoveSpeed();
            int row = coordinate[ROW_INDEX];
            int col = coordinate[COL_INDEX];
            paintMovAttackRange(row, col, "moveSpeed");
        }

        // Attempt to move opposite player's piece
        else {
            gfv.getStatusLabel().setText(STATUS + "You cannot move your opponent's piece.");
        }
    }

    // want to change colour for showing movement and attack range on tiles
    private void paintMovAttackRange(int row, int col, String actionType) {
        PieceInterface piece = getPiece(coordinate[ROW_INDEX], coordinate[COL_INDEX]);
        int radius = piece.getActionRange(actionType);
        for (int i = -radius; i <= radius; i++) {
            for (int j = -radius; j <= radius; j++) {
                if (checkMoveRepaint(row + i, col + j) &&
                        piece.isActionValid(abs(row - abs(row + i)), abs(col - abs(col + j)), actionType)) {
                    if (!(actionType.equals("moveSpeed") && checkAcross(row, col, row + i, col + j))) {
                        gfv.colourTile(row + i, col + j, actionType);
                    }
                }
            }
        }
    }

    //resets the colour of the tiles back
    private void depaintAction() {
        for (int i = ORIGINAL_ROW; i < BOARD_MAX_ROWS; i++) {
            for (int j = ORIGINAL_COL; j < BOARD_MAX_COLS; j++) {
                if (!isWall(i, j) && !getTile(i, j).hasPiece() && !isCastle(i)) {
                    gfv.setTileIcon(i, j, gfv.getGrass());
                }
            }
        }
    }

    // check if a certain tile should be repainted with the paintMovAttack
    private boolean checkMoveRepaint(int i, int j) {
        try {
            return !isWall(i, j) && !getTile(i, j).hasPiece() && !isCastle(i);
        } catch (RuntimeException e) {
            return true;
        }
    }

    public boolean getHasPerformed() {
        return hasPerformed;
    }

    //sets the variable that tells the game if a player has performed an action on his turn.
    private void setActionPerformed() {
        hasPerformed = true;
        gfv.colourEndTurn();
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
            gfv.updateBar(rebel);
        } else if (getTurn() == ROYALE_TURN) {
            rebel.increaseCP();
            gfv.updateBar(royale);
        }

        gfv.resetCursor();
        gfv.getAttackBtn().setVisible(true);
        gfv.getStatusLabel().setText(STATUS + "");
        depaintAction();
        resetAttacking();
        resetMoving();
        reset();
    }

    public int[] getCoordinates() {
        return coordinate;
    }

    public void clickTile(int i, int j) {
        coordinate[ROW_INDEX] = i;
        coordinate[COL_INDEX] = j;

        // TODO: SHOULD MAKE A PIECE STATUS PANEL TO SHOW ALL INFO
        if (getTile(i, j).hasPiece()) {
            gfv.getStatusLabel().setText(String.format(STATUS + " %s: %d HP remaining, %s, %d AP, %d DF, %d AR, %d MS. (OFFENSIVE %B, DEFENSIVE %B)", getPiece(i, j).getName(), getPiece(i, j).getHp(), getPiece(i, j).getType(),
                    getPiece(i, j).getAttackPower(), getPiece(i, j).getDefence(), getPiece(i, j).getAttackRange(), getPiece(i, j).getMoveSpeed(), getPiece(i, j).isOffensive(), getPiece(i, j).isDefensive()));
        }

        System.out.println("TileButton Name: " + gfv.getTile(i, j).getName());
        System.out.println("AP: " + getTile(i, j).getPiece().getAttackPower());
        System.out.println("DF: " + getTile(i, j).getPiece().getDefence());

        boolean match = isFactionMatched(i, j);
        if (match && !hasPerformed) {
            gfv.setImage(gfv.getTile(i, j).getName());
            gfv.colourTile(gfv.getTile(i, j));

            //Responses for movement when a tile is clicked
            if (!isMoving() && hasCoordinates() && checkMoveInit(getCoordinates()[ROW_INDEX], getCoordinates()[COL_INDEX])) {    // Trigger movement for a piece
                resetAttacking();
                setMoving();
            } else if (isMoving() && !getHasPerformed()) {    // Cancel movement (click move button twice)
                resetMoving();
                gfv.colourAttack();
            } else if (getHasPerformed()) {
                gfv.getStatusLabel().setText(STATUS + "You have already perform an action this turn.");
            } else {
                resetAttacking();
                gfv.getStatusLabel().setText(STATUS + "You have not chosen a valid tile.");
            }

            if (checkAttackInit(i, j)) {
                gfv.colourAttack();
            } else {
                gfv.colourRedAttack();
            }
        } else if (hasPerformed) {
            gfv.decolour();
        } else {
            gfv.colourRed(gfv.getTile(i, j));
        }
    }

    public void resetCoordinates() {
        coordinate = null;
        coordinate = new int[COORDINATE_NUM];
    }

    //resets the saved coordinate of a tile and the summoned piece which are saved in the gameEngine
    private void reset() {
        resetCoordinates();
        initTileCoord = null;
        initTileCoord = new int[COORDINATE_NUM];
        removeSummonedPiece();
    }

    public boolean hasCoordinates() {
        return coordinate != null;
    }

    // Check if the player and the piece on action is in the same team
    private boolean isFactionMatched(int i, int j) {
        return turn == REBEL_TURN && getPiece(i, j).getFaction().equals("Rebel") ||
                turn == ROYALE_TURN && getPiece(i, j).getFaction().equals("Royale");
    }

    public int[] getInitTileCoord() {
        return initTileCoord;
    }

    private void setInit(int i, int j) {
        initTileCoord[ROW_INDEX] = i;
        initTileCoord[COL_INDEX] = j;
    }

    public int getRow() {
        return ROW_INDEX;
    }

    public int getCol() {
        return COL_INDEX;
    }

    public int getMaxRows() {
        return BOARD_MAX_ROWS;
    }

    public int getMaxCols() {
        return BOARD_MAX_COLS;
    }

    public int getTurn() {
        return turn;
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

    @Override
    public void createSummonedPiece(String name) {
        summonedPiece = PieceCache.clonePiece(name);
    }

    public Piece getSummonedPiece() {
        return summonedPiece;
    }

    public void removeSummonedPiece() {
        summonedPiece = null;
    }

    public Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    // Gets a piece from a tile
    public PieceInterface getPiece(int row, int tile) {
        return getTile(row, tile).getPiece();
    }


    // Check if piece has been initialized successfully in current tile
    public boolean checkInit(int row, int tile) {
        return getTile(row, tile).hasPiece();
    }

    // Check if piece in current tile can move
    public boolean checkMoveInit(int row, int tile) {
        return checkInit(row, tile) && getTile(row, tile).getPiece().isMoveable();
    }

    // Check if piece in current tile can attack
    public boolean checkAttackInit(int row, int tile) {
        return checkInit(row, tile) && getTile(row, tile).getPiece().isAttackable();
    }

    // Check if piece can move from current tile to target tile
    private boolean checkMoveTarget(int row, int tile) {
        return !getTile(row, tile).hasPiece() && !isCastle(row);
    }

    // Check if piece can attack target from current tile
    private boolean checkAttackTarget(PieceInterface piece, int tgRow, int tgTile) {
        Tile space = getTile(tgRow, tgTile);
        String inFaction = piece.getFaction();
        String outFaction = space.getPiece().getFaction();
        return space.hasPiece() && !(inFaction.equals(outFaction));
    }

    //checks if the target tile is valid to be attacked/moved into by the selected peicee
    private boolean isMovRangeValid(int inRow, int inTile, int tgRow, int tgTile, String type) {
        int rowDiff = abs(inRow - tgRow);
        int tileDiff = abs(inTile - tgTile);

        PieceInterface piece = getPiece(inRow, inTile);

        return piece.isActionValid(rowDiff, tileDiff, type);
    }

    // Check if piece hasPerformed from current tile to target tile
    private boolean move(int inRow, int inTile, int tgRow, int tgTile) {

        if (checkMoveTarget(tgRow, tgTile) && isMovRangeValid(inRow, inTile, tgRow, tgTile, "moveSpeed")) {

            // Check if a piece will go across opposite piece no matter what directions, which is forbidden
            if (checkAcross(inRow, inTile, tgRow, tgTile)) {
                return false;
            }
            getTile(tgRow, tgTile).setPiece(getPiece(inRow, inTile));
            getTile(inRow, inTile).removePiece();
            return true;
        }
        return false;
    }

    private boolean checkAcross(int inRow, int inTile, int tgRow, int tgTile) {
        Tile currTile = null;
        if (inTile == tgTile) {
            Tile initTile = getTile(inRow, inTile);
            for (int i = 1; i < Math.abs(inRow - tgRow) + 1; i++) {
                if (tgRow < inRow && inRow - i >= 0) {
                    currTile = getTile(inRow - i, inTile);
                } else if (inRow + i < boardRowLength) {
                    currTile = getTile(inRow + i, inTile);
                }
                if (currTile == null) {
                    continue;
                } else if (currTile.hasPiece()) {
                    if (!currTile.getPiece().getFaction().equals(initTile.getPiece().getFaction())) {
                        return true;
                    }
                } else if (isWall(currTile.getRow(), currTile.getCol())) {
                    return true;
                }
            }
        } else if (inRow == tgRow) {
            Tile initTile = getTile(inRow, inTile);
            for (int j = 1; j < Math.abs(inTile - tgTile) + 1; j++) {
                if (tgTile < inTile && inTile - j >= 0) {
                    currTile = getTile(inRow, inTile - j);
                } else if (inTile + j < boardColLength) {
                    currTile = getTile(inRow, inTile + j);
                }
                if (currTile == null) {
                    continue;
                } else if (currTile.hasPiece()) {
                    if (!currTile.getPiece().getFaction().equals(initTile.getPiece().getFaction())) {
                        return true;
                    }
                } else if (isWall(currTile.getRow(), currTile.getCol())) {
                    return true;
                }
            }
        }
        return false;
    }

    // Check if piece hasAttacked target piece
    private boolean attack(int inRow, int inTile, int tgRow, int tgTile) {
        if (checkInit(tgRow, tgTile) && checkAttackTarget(getPiece(inRow, inTile), tgRow, tgTile) &&
                isMovRangeValid(inRow, inTile, tgRow, tgTile, "attackRange")) {
            getPiece(tgRow, tgTile).attackedBy(getPiece(inRow, inTile).getAttackPower());
            return true;
        }
        return false;
    }

    // TODO: MAGIC NUM
    private boolean checkSummonValid(Piece piece, int row, int tile) {
        boolean isRowValid;
        int extraMove = 0;
        int summonRange;

        if (piece.getType().equals("Obstacle")) {
            extraMove = OBSTACLE_EXTRA_SUMMON_LIMIT;
        }

        if (piece.getFaction().equals("Royale")) {
            summonRange = ROYALE_SUMMON_SOUTH_LIMIT + extraMove;
            isRowValid = row <= summonRange && row >= ROYALE_SUMMON_NORTH_LIMIT;
        } else {
            isRowValid = row >= REBEL_SUMMON_NORTH_LIMIT - extraMove;
        }

        if (checkMoveTarget(row, tile) && isRowValid) {
            getTile(row, tile).setPiece(piece);
            return true;
        } else {
            return false;
        }
    }

    public void paintSummonRange(String troop) {
        int start;
        int finish;
        int extraMove = 0;

        if (PieceCache.getPiece(troop) instanceof Obstacle) {
            extraMove = OBSTACLE_EXTRA_SUMMON_LIMIT;
        }

        if (turn==ROYALE_TURN) {
            start = ROYALE_SUMMON_NORTH_LIMIT;
            finish = ROYALE_SUMMON_SOUTH_LIMIT + extraMove;
        } else {
            start = REBEL_SUMMON_NORTH_LIMIT - extraMove;
            finish = boardRowLength - 1;
        }

        for (int i = start; i <= finish; i++) {
            for (int j = 0; j < boardColLength; j++) {
                if (checkMoveRepaint(i, j)) {
                    gfv.colourTile(i, j, "summon");
                }
            }
        }
    }

    public boolean placeSummonedPiece(int i, int j) {
        if (checkSummonValid(getSummonedPiece(), i, j)) {

            // TODO: Game model shouldn't have gui component such as icons
            System.out.println(gfv.getImage());
            gfv.setTileIcon(i, j, gfv.getImage());
            gfv.setTileName(i, j, gfv.getImage());

            if (getTurn() == REBEL_TURN) {
                rebel.reduceCP(getSummonedPiece().getCp());
                gfv.updateBar(rebel);
            } else if (getTurn() == ROYALE_TURN) {
                royale.reduceCP(getSummonedPiece().getCp());
                gfv.updateBar(royale);
            }

            removeSummonedPiece();
            gfv.resetCursor();
            setActionPerformed();
            changeButtonViews();
            depaintAction();
            return true;
        } else {
            gfv.getStatusLabel().setText(STATUS + "Invalid placement, the first two rows for Royales and the bottom three rows for Rebels.");
            return false;

        }
    }

    public boolean placeMovedPiece(int i, int j) {
        // target tile
        if (move(getInitTileCoord()[ROW_INDEX], getInitTileCoord()[COL_INDEX], i, j)) {
            gfv.decolour();
            System.out.println("Image = " + gfv.getImage());
            gfv.setTileIcon(i, j, gfv.getImage());
            gfv.setTileIcon(getInitTileCoord()[ROW_INDEX], getInitTileCoord()[COL_INDEX], gfv.getGrass());
            resetMoving();
            setActionPerformed();
            changeButtonViews();
            gfv.setTileName(i, j, gfv.getImage());
            gfv.resetCursor();

            // TODO: we want to keep the piece's buff if player doesn't remove it
            // resetPiece(i, j);
            return true;
        } else {
            gfv.getStatusLabel().setText(STATUS + "Tile not valid");
            gfv.decolour();
            resetMoving();
            return false;
        }
    }

    // TODO: MAGIC NUM (USE FLYWEIGHT)
    public boolean isWall(int i, int j) {
        return i % 5 <= 2 && j % 4 == 3;
    }

    // TODO: MAGIC NUM
    public boolean isCastle(int i) {
        return i == ORIGINAL_ROW;
    }

    public TurnType placeAttackPiece(int i, int j) {
        //initrow/initcol -trow/tcol
    	
        if (attack(getInitTileCoord()[ROW_INDEX], getInitTileCoord()[COL_INDEX], i, j)) {
        	boolean death;
        	PieceInterface p = null;
        	System.out.print(gfv.getImage());
            PieceInterface piece = getPiece(getInitTileCoord()[ROW_INDEX], getInitTileCoord()[COL_INDEX]);
            String message;
            int prevHp = getPiece(i, j).getHp();
            
            String pName = gfv.getTile(i, j).getName();
            
            int trueDamage = piece.getAttackPower() - getPiece(i, j).getDefence();
            if (trueDamage < 0) {
                trueDamage = 0;
            }
            message = String.format("%d true damage dealt! Remaining HP: %d", trueDamage,
                    getPiece(i, j).getHp());
            death = getPiece(i, j).isDead();
            if (death) {
            	p = getTile(i, j).getPiece();
                message += String.format(", %s is dead!", getPiece(i, j).getName());
                getTile(i, j).removePiece();
                gfv.setTileIcon(i, j, gfv.getGrass());
            }
            gfv.decolour();
            resetAttacking();
            setActionPerformed();

            gfv.getAttackBtn().setVisible(false);
            gfv.resetCursor();
            gfv.getStatusLabel().setText(STATUS + message);

            resetPiece(getInitTileCoord()[ROW_INDEX], getInitTileCoord()[COL_INDEX]);
            return new TurnType("Attack", pName, getInitTileCoord()[ROW_INDEX], getInitTileCoord()[COL_INDEX], i, j, trueDamage, death, prevHp,p);
        } else {

            gfv.getStatusLabel().setText(STATUS + "Tile not valid, press the attack button again to cancel.");
            return null;
        }
    }

    private void changeButtonViews() {

        gfv.getAttackBtn().setVisible(false);
        gfv.getUndoBtn().setVisible(true);
    }

    public boolean checkUndoRem() {
    	
    	if(getTurn() == REBEL_TURN && rebelUndoRem != 0) {
    		rebelUndoRem -=1;
    		return true;
    	}
    	else if(getTurn() == ROYALE_TURN && royaleUndoRem != 0) {
    		royaleUndoRem -= 1;
    		return true;
    	}
    	else
    		gfv.getStatusLabel().setText(STATUS+ "You have already used up your undo limit for the game!");
    	return false;
    }
    
    public void undoTurn(TurnType tt) {
    	JButton[][] tileBtns = gfv.getTileBtns();

    	
    	switch(tt.MoveType) {
    	
    	case "Move":
            getTile(tt.fromRow, tt.fromCol).setPiece(getPiece(tt.tooRow, tt.tooCol));
            getTile(tt.tooRow, tt.tooCol).removePiece();
            tileBtns[tt.tooRow][tt.tooCol].setIcon(new ImageIcon(this.getClass().getResource(gfv.getGrass())));
            tileBtns[tt.fromRow][tt.fromCol].setIcon(new ImageIcon(this.getClass().getResource(tt.image)));
            tileBtns[tt.fromRow][tt.fromCol].setName(tt.image);
            break;
            
    	case "Summon":
            JButton tileBtnSum = tileBtns[tt.tooRow][tt.tooCol];
            getTile(tt.tooRow, tt.tooCol).removePiece();
            tileBtnSum.setIcon(new ImageIcon(this.getClass().getResource(gfv.getGrass())));
            break;
    	
    	case "Attack":
    		System.out.print(tt.death);
    		if(tt.death) {
    			
                tileBtns[tt.tooRow][tt.tooCol].setIcon(new ImageIcon(this.getClass().getResource(tt.image)));
                tileBtns[tt.tooRow][tt.tooCol].setName(tt.image);
                getTile(tt.tooRow,tt.tooCol).setPiece(tt.p);
                
                getPiece(tt.tooRow,tt.tooCol).setHP(tt.prevHp);
    		}
    		else {
    			getPiece(tt.tooRow, tt.tooCol).addHP(tt.damageDealt);
    		}
    		break;
    	}
    	
        gfv.decolour();
        hasPerformed = false;
        gfv.getAttackBtn().setVisible(true);

    }

    private void resetPiece(int i, int j) {
        PieceInterface currentPiece = getPiece(i, j);
        if (currentPiece.isOffensive() || currentPiece.isDefensive()) {
            PieceInterface originalPiece = new ResetModeDecoratorFactory(currentPiece).getFactory();
            getTile(i, j).setPiece(originalPiece);
            System.out.println("Reset!");
            System.out.println(originalPiece.getInitHp());
            System.out.println(originalPiece.getInitAttackPower());
        }
    }

    public void setOffensive() {
        if (coordinate != null) {
            setInit(coordinate[ROW_INDEX], coordinate[COL_INDEX]);
            System.out.println(getInitTileCoord()[ROW_INDEX] + ", " + getInitTileCoord()[COL_INDEX]);
            if (checkInit(getInitTileCoord()[ROW_INDEX], getInitTileCoord()[COL_INDEX])) {
                if (!(getPiece(getInitTileCoord()[ROW_INDEX], getInitTileCoord()[COL_INDEX]) instanceof Obstacle)) {
                    if (isFactionMatched(getInitTileCoord()[ROW_INDEX], getInitTileCoord()[COL_INDEX])) {
                        PieceInterface originalPiece = getPiece(getInitTileCoord()[ROW_INDEX], getInitTileCoord()[COL_INDEX]);
                        if (!originalPiece.isOffensive()) {
                            PieceInterface resetPiece = new ResetModeDecoratorFactory(originalPiece).getFactory();
                            PieceInterface offensivePiece = new AttackPowerBuffDecoratorFactory(new DefenceNerfDecoratorFactory(resetPiece).getFactory()).getFactory();
                            offensivePiece.isOffensive();

                            getTile(getInitTileCoord()[ROW_INDEX], getInitTileCoord()[COL_INDEX]).setPiece(offensivePiece);

                            System.out.println("Original AP: " + originalPiece.getAttackPower());
                            System.out.println("Original DF: " + originalPiece.getDefence());
                            System.out.println("--------------------------------------------");
                            System.out.println("Offensive AP: " + offensivePiece.getAttackPower());
                            System.out.println("Offensive DF: " + offensivePiece.getDefence());
                        } else {
                            PieceInterface resetPiece = new ResetModeTroopDecorator(originalPiece);
                            resetPiece.isOffensive();

                            getTile(getInitTileCoord()[ROW_INDEX], getInitTileCoord()[COL_INDEX]).setPiece(resetPiece);

                            System.out.println("Original AP: " + resetPiece.getAttackPower());
                            System.out.println("Original DF: " + resetPiece.getDefence());
                        }
                    } else {
                        System.out.println("You cannot strengthen opponent piece!");
                    }
                } else {
                    System.out.println("You cannot strengthen obstacles!");
                }
            }
        } else {
            gfv.getStatusLabel().setText(STATUS + "You have not chosen a valid tile.");
        }
    }


    public void setDefensive() {
        if (coordinate != null) {
            setInit(coordinate[ROW_INDEX], coordinate[COL_INDEX]);
            System.out.println(getInitTileCoord()[ROW_INDEX] + ", " + getInitTileCoord()[COL_INDEX]);
            if (checkInit(getInitTileCoord()[ROW_INDEX], getInitTileCoord()[COL_INDEX])) {
                if (!(getPiece(getInitTileCoord()[ROW_INDEX], getInitTileCoord()[COL_INDEX]) instanceof Obstacle)) {
                    PieceInterface originalPiece = getPiece(getInitTileCoord()[ROW_INDEX], getInitTileCoord()[COL_INDEX]);
                    if (isFactionMatched(getInitTileCoord()[ROW_INDEX], getInitTileCoord()[COL_INDEX])) {
                        if (!originalPiece.isDefensive()) {
                            PieceInterface resetPiece = new ResetModeDecoratorFactory(originalPiece).getFactory();
                            PieceInterface defensivePiece = new DefenceBuffDecoratorFactory(new AttackPowerNerfDecoratorFactory(resetPiece).getFactory()).getFactory();
                            defensivePiece.isDefensive();

                            getTile(getInitTileCoord()[ROW_INDEX], getInitTileCoord()[COL_INDEX]).setPiece(defensivePiece);

                            System.out.println("Original AP: " + originalPiece.getAttackPower());
                            System.out.println("Original DF: " + originalPiece.getDefence());
                            System.out.println("--------------------------------------------");
                            System.out.println("Defensive AP: " + defensivePiece.getAttackPower());
                            System.out.println("Defensive DF: " + defensivePiece.getDefence());
                        } else {
                            PieceInterface resetPiece = new ResetModeTroopDecorator(originalPiece);
                            resetPiece.isDefensive();

                            getTile(getInitTileCoord()[ROW_INDEX], getInitTileCoord()[COL_INDEX]).setPiece(resetPiece);

                            System.out.println("Original AP: " + resetPiece.getAttackPower());
                            System.out.println("Original DF: " + resetPiece.getDefence());
                        }
                    } else {
                        System.out.println("You cannot strengthen opponent piece!");
                    }
                } else {
                    System.out.println("You cannot strengthen obstacles!");
                }
            }
        } else {
            gfv.getStatusLabel().setText(STATUS + "You have not chosen a valid tile.");
        }
    }


    public Tile[][] getTiles() {
        return tiles;
    }

    public String whoseTurn() {
        if (turn == 0)
            return "Rebel";
        else {
            return "Royale";
        }
    }


    @Override
    public int[] getUndoLimit() {
        return new int[]{rebelUndoRem, royaleUndoRem};
    }

    @Override
    public Player getRebelPlayer() {
        return rebel;
    }

    @Override
    public Player getRoyalePlayer() {
        return royale;
    }

    public boolean saveGame() {

        try {
            PrintWriter output = new PrintWriter(new FileWriter(FULL_SAVE_FILE_NAME));
            output.println(BOARD_MAX_ROWS + "|" + BOARD_MAX_COLS);
            for (String data : gfv.getPlayerData()) {
                output.print(data + "|");
            }
            output.println();
            int[] undoLimit = getUndoLimit();
            output.println(undoLimit[0] + "|" + undoLimit[1]);
            output.println(getTurn());
            output.println(getHasPerformed());

            for (Tile[] tileRow : getTiles()) {
                for (Tile tile : tileRow) {
                    if (tile.hasPiece()) {
                        PieceInterface piece = tile.getPiece();
                        output.printf("%d|%d|%s|%d|%n", tile.getRow(), tile.getCol(), piece.getName(), piece.getHp());
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
        rebelUndoRem = Integer.parseInt(undoLimit[0]);
        royaleUndoRem = Integer.parseInt(undoLimit[1]);
        this.turn = Integer.parseInt(turn);

        for (String[] tile : tileList) {
            int row = Integer.valueOf(tile[ROW_LOADED]);
            int col = Integer.valueOf(tile[COL_LOADED]);
            String name = tile[NAME_LOADED];
            int hp = Integer.valueOf(tile[HP_LOADED]);
            onBoardPiece = PieceCache.clonePiece(name);
            onBoardPiece.setHP(hp);
            tiles[row][col].setPiece(onBoardPiece);
        }
        onBoardPiece = null;
    }


    public void changeAttackTarget(Tile tile, int i, int j) {

//        if (isAttacking) {
//            if (tile.hasPiece() && tile.getRow() == i && tile.getCol() == j) {
//                if (!isFactionMatched(i, j)) {
//                    System.out.println(initTileCoord[0] + ", " + initTileCoord[1]);
//                    System.out.println(i + ", " + j);
//                    if (isMovRangeValid(initTileCoord[0], initTileCoord[1], i, j, "attackRange")) {
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


}
