package model.gameEngine;

import controller.commandPattern.AbstractTurn;
import controller.commandPattern.MoveCommand;
import controller.commandPattern.SummonCommand;
import controller.commandPattern.TurnType;
import model.piece.AbtractPiece.Piece;
import model.piece.AbtractPiece.PieceInterface;
import model.piece.decorator.ResetDecorator;
import model.piece.decorator.SetDefensiveDecorator;
import model.piece.decorator.SetOffensiveDecorator;
import model.player.Player;
import model.player.RebelPlayer;
import model.player.RoyalePlayer;
import view.gameView.GameFrameView;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Stack;

import static java.awt.Cursor.DEFAULT_CURSOR;
import static java.lang.Math.abs;
import static view.gameView.GameFrameView.STATUS;

public class GameEngineFacade implements GameEngine {

    public static final int BOARD_MAX_ROWS = 13; // increments in 5
    public static final int BOARD_MAX_COLS = 15; // increments in 4
    private static final int REBEL_TURN = 0;
    private static final int ROYALE_TURN = 1;
    private static final int ROW_LOADED = 0;
    private static final int COL_LOADED = 1;
    private static final int NAME_LOADED = 2;
    private static final int HP_LOADED = 3;
    private static final int COORDINATE_NUM = 2;
    private static final int ROW = 0;
    private static final int COL = 1;
    private static final int ORIGINAL_ROW = 0;
    private static final int ORIGINAL_COL = 0;
    private static final int ROYALE_SUMMON_TOP_LIMIT= 1;
    private static final int ROYALE_SUMMON_BOTTOM_LIMIT= 3;
    private static final int REBEL_SUMMON_TOP_LIMIT= 9;
    private static final int OBSTACLE_EXTRA_SUMMON_LIMIT= 3;

    // TODO: Game model shouldn't have gui component such as icons
    private static final String IMAGE_PATH = "../../images/";
    // Stack for storing moves
    private static Stack<AbstractTurn> moves;
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
    private boolean actionPerformed;
    private int rebelUndoRem;
    private int royaleUndoRem;
    private int boardRowLength;
    private int boardColLength;

    public GameEngineFacade(GameFrameView gfv, int undoMoves, RoyalePlayer royale,RebelPlayer rebel ) {
        gameInit(gfv);
        actionPerformed = false;
        this.royale = royale;
        this.rebel = rebel;
        rebelUndoRem = undoMoves;
        royaleUndoRem = undoMoves;
        turn = REBEL_TURN;
    }

    public GameEngineFacade(GameFrameView gfv, String turn, String actionPerformed, ArrayList<String[]> tileList) {

        gameInit(gfv);

        this.actionPerformed = Boolean.parseBoolean(actionPerformed);
        this.turn = Integer.parseInt(turn);
        this.gfv = gfv;

        for (String[] tile : tileList) {
            int row = Integer.valueOf(tile[ROW_LOADED]);
            int col = Integer.valueOf(tile[COL_LOADED]);
            String name = tile[NAME_LOADED];
            int hp = Integer.valueOf(tile[HP_LOADED]);
            try {
                Class pieceCls = Class.forName("model.piece.concretePiece." + name);
                onBoardPiece = (Piece) pieceCls.getDeclaredConstructor().newInstance();

            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException ex) {
                ex.printStackTrace();
            }
            onBoardPiece.setHP(hp);
            tiles[row][col].setPiece(onBoardPiece);
        }

        onBoardPiece = null;
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
            gfv.setTileIcon(row, col, IMAGE_PATH + name + ".png");
        }
    }

    private void gameInit(GameFrameView gfv) {

        this.gfv = gfv;

        tiles = new Tile[BOARD_MAX_ROWS][BOARD_MAX_COLS];

        for (int i = ORIGINAL_ROW; i < BOARD_MAX_ROWS; i++) {
            for (int j = ORIGINAL_COL; j < BOARD_MAX_COLS; j++) {
                tiles[i][j] = new Tile(i, j);
            }
        }

        initTileCoord = new int[COORDINATE_NUM];

        moves = new Stack<>();

        // Initialize number of player turns
        turns = new int[]{REBEL_TURN, ROYALE_TURN};

        isMoving = false;
        isAttacking = false;

        boardRowLength= BOARD_MAX_ROWS;
        boardColLength= BOARD_MAX_COLS;

        // Initialize current turn;
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
        gfv.decolour();
        gfv.getFrame().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        depaintAction();
    }

    // set the mode for attacking
    public void setAttacking() {
        if (isFactionMatched(coordinate[ROW], coordinate[COL])) {
            isAttacking = true;
            setInit(coordinate[ROW], coordinate[COL]);
            Image icon = new ImageIcon(this.getClass().getResource(IMAGE_PATH + "target.png")).getImage();
            gfv.getFrame().setCursor(Toolkit.getDefaultToolkit().createCustomCursor(icon, new Point(ORIGINAL_ROW, ORIGINAL_COL), "name"));

            int range = getPiece(coordinate[ROW], coordinate[COL]).getAttackRange();
            int row = coordinate[ROW];
            int col = coordinate[COL];

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
        gfv.getFrame().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    //enter the moving mode
    public void setMoving() {
        if (isFactionMatched(coordinate[ROW], coordinate[COL])) {
            isMoving = true;
            setInit(coordinate[ROW], coordinate[COL]);
            Image icon = new ImageIcon(this.getClass().getResource(gfv.getImage())).getImage();
            gfv.getFrame().setCursor(Toolkit.getDefaultToolkit().createCustomCursor(icon, new Point(ORIGINAL_ROW, ORIGINAL_COL), "name"));

            int mov = getPiece(coordinate[ROW], coordinate[COL]).getMoveSpeed();
            int row = coordinate[ROW];
            int col = coordinate[COL];
            paintMovAttackRange(row, col, "moveSpeed");
        }

        // Attempt to move opposite player's piece
        else {
            gfv.getStatusLabel().setText(STATUS + "You cannot move your opponent's piece.");
        }
    }

    // want to change colour for showing movement and attack range on tiles
    private void paintMovAttackRange(int row, int col, String actionType) {
        PieceInterface piece = getPiece(coordinate[ROW], coordinate[COL]);
        int radius = piece.getActionRange(actionType);
        for (int i = -radius; i <= radius; i++) {
            for (int j = -radius; j <= radius; j++) {
                if (checkMoveRepaint(row + i, col + j) &&
                        piece.isActionValid(abs(row - abs(row + i)), abs(col - abs(col + j)), actionType)) {
                    gfv.colourTile(row + i, col + j, actionType);
                }
            }
        }
    }

    //resets the colour of the tiles back
    private void depaintAction() {
        JButton[][] tileBtns = gfv.getTileBtns();
        for (int i = ORIGINAL_ROW; i < BOARD_MAX_ROWS; i++) {
            for (int j = ORIGINAL_COL; j < BOARD_MAX_COLS; j++) {
                if (!isWall(i, j) && !getTile(i, j).hasPiece() && !isCastle(i)) {
                    tileBtns[i][j].setIcon(new ImageIcon(this.getClass().getResource(IMAGE_PATH + "grass.png")));
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

    public boolean getActionPerformed() {
        return actionPerformed;
    }

    //sets the variable that tells the game if a player has performed an action on his turn.
    private void setActionPerformed() {
        actionPerformed = true;
        gfv.colourEndTurn();
    }

    //called when the end turn button is clicked and changes the turn, including repainting the GUI
    //with appropriate player and game info
    public void unsetActionPerformed() {
        actionPerformed = false;
        gfv.decolourEndTurn();
        gfv.decolour();
        cycleTurn();
        if(getTurn() == REBEL_TURN) {
        	royale.increaseCP();
        	gfv.updateBar(rebel);
        }
        else {
        	rebel.increaseCP();
        	gfv.updateBar(royale);
        }
        
        gfv.getFrame().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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

    public void clickTile(JButton tileBtn, int i, int j) {
        coordinate[ROW] = i;
        coordinate[COL] = j;

        // TODO: SHOULD MAKE A PIECE STATUS PANEL TO SHOW ALL INFO
        if (getTile(i, j).hasPiece()) {
            gfv.getStatusLabel().setText(String.format(STATUS + " %s: %d HP remaining, %d AP, %d DF, %d AR, %d MS. (OFFENSIVE %B, DEFENSIVE %B)", getPiece(i, j).getName(), getPiece(i, j).getHp(),
                    getPiece(i, j).getAttackPower(), getPiece(i, j).getDefence(), getPiece(i, j).getAttackRange(), getPiece(i, j).getMoveSpeed(), getPiece(i, j).isOffensive(), getPiece(i, j).isDefensive()));
        }

        System.out.println("TileButton Name: " + tileBtn.getName());
        System.out.println("AP: " + getTile(i, j).getPiece().getAttackPower());
        System.out.println("DF: " + getTile(i, j).getPiece().getDefence());
        System.out.println("AR: " + getTile(i, j).getPiece().getAttackRange());
        System.out.println("MS: " + getTile(i, j).getPiece().getMoveSpeed());

        boolean match = isFactionMatched(i, j);
        if (match && !actionPerformed) {
            gfv.setImage(tileBtn.getName());
            gfv.colourTile(tileBtn);

            //Responses for movement when a tile is clicked
            if (!isMoving() && hasCoordinates() && checkMoveInit(getCoordinates()[ROW], getCoordinates()[COL])) {    // Trigger movement for a piece
                resetAttacking();
                setMoving();
            } else if (isMoving() && !getActionPerformed()) {    // Cancel movement (click move button twice)
                resetMoving();
                gfv.colourAttack();
            } else if (getActionPerformed()) {
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
        } else if (actionPerformed) {
            gfv.decolour();
        } else {
            gfv.colourRed(tileBtn);
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
        initTileCoord[ROW] = i;
        initTileCoord[COL] = j;
    }

    public int getRow() {
        return ROW;
    }

    public int getCol() {
        return COL;
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

    public Piece getSummonedPiece() {
        return summonedPiece;
    }

    public void removeSummonedPiece() {
        summonedPiece = null;
    }

    private Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    // Gets a piece from a tile
    private PieceInterface getPiece(int row, int tile) {
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

        /*for(int i= -rowDiff; i<=rowDiff;i++){
            if(i!=inRow && (isWall(inRow+i,inTile) || getTile(inRow+i, inTile).hasPiece())){
                return false;
            }
        }
        for (int i= -tileDiff;i>=tileDiff;i++){
            if(i!=inTile && (isWall(inRow,inTile+i) || getTile(inRow, inTile+i).hasPiece())){
                return false;
            }


        }*/
        return piece.isActionValid(rowDiff, tileDiff, type);
    }

    // Check if piece actionPerformed from current tile to target tile
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

    boolean checkAcross(int inRow, int inTile, int tgRow, int tgTile) {
        Tile currTile;
        if (inTile == tgTile) {
            Tile initTile = getTile(inRow, inTile);
            for (int i = 1; i < Math.abs(inRow - tgRow) + 1; i++) {
                if (tgRow < inRow) {
                    currTile = getTile(inRow - i, inTile);
                } else {
                    currTile = getTile(inRow + i, inTile);
                }
                if (currTile.hasPiece()) {
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
                if (tgTile < inTile) {
                    currTile = getTile(inRow, inTile - j);
                } else {
                    currTile = getTile(inRow, inTile + j);
                }
                if (currTile.hasPiece()) {
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

    public void createPiece(String name) {
        try {
            Class pieceCls = Class.forName("model.piece.concretePiece." + name);
            this.summonedPiece = (Piece) pieceCls.getDeclaredConstructor().newInstance();

        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException ex) {
            ex.printStackTrace();
        }

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
            summonRange= ROYALE_SUMMON_BOTTOM_LIMIT + extraMove;
            isRowValid = row <= summonRange && row >= ROYALE_SUMMON_TOP_LIMIT;
        } else {
            isRowValid = row >= REBEL_SUMMON_TOP_LIMIT - extraMove;
        }

        if (checkMoveTarget(row, tile) && isRowValid) {
            getTile(row, tile).setPiece(piece);
            return true;
        } else {
            return false;
        }
    }

    public void paintSummonRange(String faction, String troopType){
        int start;
        int finish;
        int extraMove= 0;

        if(troopType.equals("Obstacle")){
            extraMove= OBSTACLE_EXTRA_SUMMON_LIMIT;
        }

        if(faction.equals("Royale")){
            start= ROYALE_SUMMON_TOP_LIMIT;
            finish= ROYALE_SUMMON_BOTTOM_LIMIT + extraMove;
        }
        else{
            start= REBEL_SUMMON_TOP_LIMIT - extraMove;
            finish= boardRowLength - 1;
        }

        for(int i=start;i<=finish;i++){
            for(int j=0;j<boardColLength;j++){
                if(checkMoveRepaint(i, j)) {
                    gfv.colourTile(i, j, "summon");
                }
            }
        }
    }

    public boolean placeSummonedPiece(JButton tileBtn, int i, int j) {
        if (checkSummonValid(getSummonedPiece(), i, j)) {

            // TODO: Game model shouldn't have gui component such as icons
            System.out.println(gfv.getImage());
            tileBtn.setIcon(new ImageIcon(this.getClass().getResource(gfv.getImage())));
            tileBtn.setName(gfv.getImage());
            removeSummonedPiece();
            gfv.getFrame().setCursor(new Cursor(DEFAULT_CURSOR));
            setActionPerformed();
            changeButtonViews();
            depaintAction();
            return true;
        } else {
            gfv.getStatusLabel().setText(STATUS + "Invalid placement, the first two rows for Royales and the bottom three rows for Rebels.");
            return false;

        }
    }

    public boolean placeMovedPiece(JButton[][] tileBtns, int i, int j) {
        // target tile
        JButton tileBtn = tileBtns[i][j];

        if (move(getInitTileCoord()[ROW], getInitTileCoord()[COL], i, j)) {
            gfv.decolour();
            System.out.println("Image = " + gfv.getImage());
            tileBtn.setIcon(new ImageIcon(this.getClass().getResource(gfv.getImage())));
            tileBtns[getInitTileCoord()[ROW]][getInitTileCoord()[COL]].setIcon(new ImageIcon(
                    this.getClass().getResource(gfv.getGrass())));
            resetMoving();
            setActionPerformed();
            changeButtonViews();
            tileBtn.setName(gfv.getImage());
            gfv.getFrame().setCursor(new Cursor(DEFAULT_CURSOR));
            resetPiece(i, j);
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
    private boolean isCastle(int i) {
        return i == ORIGINAL_ROW;
    }

    public void placeAttackPiece(JButton[][] tileBtns,int i, int j) {
        
        if (attack(getInitTileCoord()[ROW], getInitTileCoord()[COL], i, j)) {
            PieceInterface piece = getPiece(getInitTileCoord()[ROW], getInitTileCoord()[COL]);
            String message;
            message = String.format("%d true damage dealt! Remaining HP: %d", piece.getAttackPower() - getPiece(i, j).getDefence(),
                    getPiece(i, j).getHp());
            if (getPiece(i, j).isDead()) {
                message += String.format(", %s is dead!", getPiece(i, j).getName());
                getTile(i, j).removePiece();
                tileBtns[i][j].setIcon(new ImageIcon(this.getClass().getResource(IMAGE_PATH + "grass.png")));
            }
            gfv.decolour();
            resetAttacking();
            setActionPerformed();

            gfv.getAttackBtn().setVisible(false);
            gfv.getFrame().setCursor(new Cursor(DEFAULT_CURSOR));
            gfv.getStatusLabel().setText(STATUS + message);

            resetPiece(getInitTileCoord()[ROW], getInitTileCoord()[COL]);
        } else {
            gfv.getStatusLabel().setText(STATUS + "Tile not valid, press the attack button again to cancel.");
        }
    }

    public void changeButtonViews() {

        gfv.getAttackBtn().setVisible(false);
        gfv.getUndoBtn().setVisible(true);
    }

    public void undoTurn() {

        if (getTurn() == REBEL_TURN && rebelUndoRem != 0) {
        	rebelUndoRem -=1;
            accessStack();
        } else if (getTurn() == ROYALE_TURN && royaleUndoRem != 0) {
        	royaleUndoRem -=1;
            accessStack();
        } else {
            gfv.getStatusLabel().setText(STATUS + "You have already used your Undo for this game!");
        }

    }
    
    public void pushTurnStack(AbstractTurn turn) {
    	moves.push(turn);
    }

    //Undo stack with all game movements excl attacks for now.
    public void accessStack() {

        JButton[][] tileBtns = gfv.getTileBtns();
        for(int i =0; i<2; i++) {				//Loop occurs as the round is undone as opposed to each players turn
        	if(moves.size()>0) {
                if (moves.peek().getClass() == MoveCommand.class) {
                    MoveCommand mc = (MoveCommand) moves.pop();
                    TurnType lm = mc.lastMove;
                    
                    JButton tileBtn = tileBtns[lm.tooRow][lm.tooRow];
                    getTile(lm.fromRow, lm.fromCol).setPiece(getPiece(lm.tooRow, lm.tooCol));
                    getTile(lm.tooRow, lm.tooCol).removePiece();
                    tileBtn.setIcon(new ImageIcon(this.getClass().getResource(gfv.getGrass())));
                    tileBtns[lm.fromRow][lm.fromCol].setIcon(new ImageIcon(this.getClass().getResource(lm.image)));
                    tileBtns[lm.fromRow][lm.fromCol].setName(gfv.getImage());

                } else  {
                    SummonCommand sc = (SummonCommand) moves.pop();
                    TurnType lm = sc.lastMove;
                    JButton tileBtnSum = tileBtns[lm.tooRow][lm.tooCol];
                    getTile(lm.tooRow, lm.tooCol).removePiece();
                    tileBtnSum.setIcon(new ImageIcon(this.getClass().getResource(gfv.getGrass())));

                }
        			
        	}
        }

        gfv.decolour();
        actionPerformed = false;
        gfv.getAttackBtn().setVisible(true);
        
    }

    private void resetPiece(int i, int j) {
        PieceInterface currentPiece = getPiece(i, j);
        if (currentPiece.isOffensive() || currentPiece.isDefensive()) {
            PieceInterface originalPiece = new ResetDecorator(currentPiece);
            originalPiece.resetMode();
            getTile(i, j).setPiece(originalPiece);
            System.out.println("Reset!");
            System.out.println(originalPiece.getInitHp());
            System.out.println(originalPiece.getInitAttackPower());
        }
    }

    public void setOffensive() {
        setInit(coordinate[ROW], coordinate[COL]);
        System.out.println(getInitTileCoord()[ROW] + ", " + getInitTileCoord()[COL]);
        if (checkInit(getInitTileCoord()[ROW], getInitTileCoord()[COL])) {
            if (isFactionMatched(getInitTileCoord()[ROW], getInitTileCoord()[COL])) {
                PieceInterface originalPiece = getPiece(getInitTileCoord()[ROW], getInitTileCoord()[COL]);
                if (!originalPiece.isOffensive()) {
                    PieceInterface resetPiece = new ResetDecorator(originalPiece);
                    resetPiece.resetMode();
                    PieceInterface offensivePiece = new SetOffensiveDecorator(resetPiece);
                    offensivePiece.setOffensive();
                    getTile(getInitTileCoord()[ROW], getInitTileCoord()[COL]).setPiece(offensivePiece);

                    System.out.println("Original AP: " + originalPiece.getAttackPower());
                    System.out.println("Original DF: " + originalPiece.getDefence());
                    System.out.println("Original AR: " + originalPiece.getAttackRange());
                    System.out.println("Original MS: " + originalPiece.getMoveSpeed());
                    System.out.println("--------------------------------------------");
                    System.out.println("Offensive AP: " + offensivePiece.getAttackPower());
                    System.out.println("Offensive DF: " + offensivePiece.getDefence());
                    System.out.println("Offensive AR: " + offensivePiece.getAttackRange());
                    System.out.println("Offensive MS: " + offensivePiece.getMoveSpeed());
                } else {
                    PieceInterface resetPiece = new ResetDecorator(originalPiece);
                    resetPiece.resetMode();
                    getTile(getInitTileCoord()[ROW], getInitTileCoord()[COL]).setPiece(resetPiece);
                    System.out.println("Original AP: " + resetPiece.getAttackPower());
                    System.out.println("Original DF: " + resetPiece.getDefence());
                    System.out.println("Original AR: " + resetPiece.getAttackRange());
                    System.out.println("Original MS: " + resetPiece.getMoveSpeed());
                }
            } else {
                System.out.println("You cannot strengthen opponent piece!");
            }
        }
    }

    public void setDefensive() {
        setInit(coordinate[ROW], coordinate[COL]);
        System.out.println(getInitTileCoord()[ROW] + ", " + getInitTileCoord()[COL]);
        if (checkInit(getInitTileCoord()[ROW], getInitTileCoord()[COL])) {
            PieceInterface originalPiece = getPiece(getInitTileCoord()[ROW], getInitTileCoord()[COL]);
            if (isFactionMatched(getInitTileCoord()[ROW], getInitTileCoord()[COL])) {
                if (!originalPiece.isDefensive()) {
                    PieceInterface resetPiece = new ResetDecorator(originalPiece);
                    resetPiece.resetMode();
                    PieceInterface defensivePiece = new SetDefensiveDecorator(resetPiece);
                    defensivePiece.setDefensive();
                    getTile(getInitTileCoord()[ROW], getInitTileCoord()[COL]).setPiece(defensivePiece);

                    System.out.println("Original AP: " + originalPiece.getAttackPower());
                    System.out.println("Original DF: " + originalPiece.getDefence());
                    System.out.println("Original AR: " + originalPiece.getAttackRange());
                    System.out.println("Original MS: " + originalPiece.getMoveSpeed());
                    System.out.println("--------------------------------------------");
                    System.out.println("Defensive AP: " + defensivePiece.getAttackPower());
                    System.out.println("Defensive DF: " + defensivePiece.getDefence());
                    System.out.println("Defensive AR: " + defensivePiece.getAttackRange());
                    System.out.println("Defensive MS: " + defensivePiece.getMoveSpeed());
                } else {
                    PieceInterface resetPiece = new ResetDecorator(originalPiece);
                    resetPiece.resetMode();
                    getTile(getInitTileCoord()[ROW], getInitTileCoord()[COL]).setPiece(resetPiece);
                    System.out.println("Original AP: " + resetPiece.getAttackPower());
                    System.out.println("Original DF: " + resetPiece.getDefence());
                    System.out.println("Original AR: " + resetPiece.getAttackRange());
                    System.out.println("Original MS: " + resetPiece.getMoveSpeed());
                }
            } else {
                System.out.println("You cannot strengthen opponent piece!");
            }
        }
    }


    public Tile[][] getTiles() {
        return tiles;
    }

    public String whoseTurn(){
        if (turn==0)
                return "Rebel";
        else{
            return "Royale";
        }
    }

	@Override
	public Player returnRoyale() {
		// TODO Auto-generated method stub
		return royale;
	}

	@Override
	public Player returnRebel() {
		// TODO Auto-generated method stub
		return rebel;
	}

	@Override
	public void placeAttackPiece(int i, int j) {
		// TODO Auto-generated method stub
		
	}

}
