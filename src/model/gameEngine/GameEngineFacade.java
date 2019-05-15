package model.gameEngine;

import controller.commandPattern.AbstractTurn;
import controller.commandPattern.MoveCommand;
import controller.commandPattern.SummonCommand;
import model.piece.AbtractPiece.Piece;
import model.piece.AbtractPiece.PieceInterface;
import model.piece.decorator.ResetDecorator;
import model.piece.decorator.SetDefensiveDecorator;
import model.piece.decorator.SetOffensiveDecorator;
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

    public static final int BOARD_ROWS = 13; // increments in 5
    public static final int BOARD_COLS = 15; // increments in 4
    public static final int REBEL_TURN = 0;
    public static final int ROYALE_TURN = 1;
    private static final int COORDINATE_X = 0;
    private static final int COORDINATE_Y = 1;

    // TODO: Game model shouldn't have gui component such as icons
    private static final String IMAGE_PATH = "../../images/";

    public static Stack<AbstractTurn> moves;
    private GameFrameView gfv;
    private Piece summonedPiece;
    private Piece onBoardPiece;
    private Tile[][] tiles;
    private boolean rebelUndo = false;
    private boolean royaleUndo = false;

    private int turn;

    private int[] turns;
    private int[] coordinate;
    private int[] initTileCoord;
    private boolean isMoving;
    private boolean isAttacking;
    private boolean actionPerformed;

    public GameEngineFacade(GameFrameView gfv) {
        gameInit(gfv);
        actionPerformed = false;
        turn = REBEL_TURN;
    }

    public GameEngineFacade(GameFrameView gfv, String turn, String actionPerformed, ArrayList<String[]> tileList) {

        gameInit(gfv);

        this.actionPerformed = Boolean.parseBoolean(actionPerformed);
        this.turn = Integer.parseInt(turn);
        this.gfv = gfv;

        for (String[] tile : tileList) {
            int row = Integer.valueOf(tile[0]);
            int col = Integer.valueOf(tile[1]);
            String name = tile[2];
            int hp = Integer.valueOf(tile[3]);
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

    public void setTileIcon(ArrayList<String[]> tileList) {

        for (String[] tile : tileList) {
            int row = Integer.valueOf(tile[0]);
            int col = Integer.valueOf(tile[1]);
            String name = tile[2];
            gfv.setTileIcon(row, col, IMAGE_PATH + name + ".png");
        }
    }

    private void gameInit(GameFrameView gfv) {

        this.gfv = gfv;

        tiles = new Tile[BOARD_ROWS][BOARD_COLS];

        for (int i = 0; i < BOARD_ROWS; i++) {
            for (int j = 0; j < BOARD_COLS; j++) {
                tiles[i][j] = new Tile(i, j);
            }
        }

        initTileCoord = new int[2];
        moves = new Stack<AbstractTurn>();

        // Initialize number of player turns
        turns = new int[]{REBEL_TURN, ROYALE_TURN};

        isMoving = false;
        isAttacking = false;

        // Initialize current turn;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    //resets the attacking mode and resets the colour of the tiles + cursor
    public void resetAttacking() {
        isAttacking = false;
        gfv.decolour();
        gfv.getFrame().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        depaintAction();
    }

    //set the mode for attacking
    public void setAttacking() {
        if (isFactionMatched(coordinate[0], coordinate[1])) {
            isAttacking = true;
            setInit(coordinate[0], coordinate[1]);
            Image icon = new ImageIcon(this.getClass().getResource(IMAGE_PATH + "target.png")).getImage();
            gfv.getFrame().setCursor(Toolkit.getDefaultToolkit().createCustomCursor(icon, new Point(0, 0), "name"));

            int range = getPiece(coordinate[0], coordinate[1]).getAttackRange();
            int row = coordinate[0];
            int col = coordinate[1];

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
        if (isFactionMatched(coordinate[0], coordinate[1])) {
            isMoving = true;
            setInit(coordinate[0], coordinate[1]);
            Image icon = new ImageIcon(this.getClass().getResource(gfv.getImage())).getImage();
            gfv.getFrame().setCursor(Toolkit.getDefaultToolkit().createCustomCursor(icon, new Point(0, 0), "name"));

            int mov = getPiece(coordinate[0], coordinate[1]).getMoveSpeed();
            int row = coordinate[0];
            int col = coordinate[1];
            paintMovAttackRange(row, col, "moveSpeed");
        }

        // Attempt to move opposite player's piece
        else {
            gfv.getStatusLabel().setText(STATUS + "You cannot move your opponent's piece.");
        }
    }

    // want to change colour for showing movement and attack range on tiles
    private void paintMovAttackRange(int row, int col, String actionType) {
        PieceInterface piece = getPiece(coordinate[0], coordinate[1]);
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
        for (int i = 0; i < BOARD_ROWS; i++) {
            for (int j = 0; j < BOARD_COLS; j++) {
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
        gfv.updateBar(turn);
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
        coordinate[0] = i;
        coordinate[1] = j;
        if (getTile(i, j).hasPiece()) {
            gfv.getStatusLabel().setText(String.format(STATUS + " %s has %dHP remaining", getPiece(i, j).getName(), getPiece(i, j).getHp()));
        }
        System.out.println("TileButton Name: " + tileBtn.getName());
        System.out.println("Original AP: " + getTile(i, j).getPiece().getAttackPower());
        System.out.println("Original HP: " + getTile(i, j).getPiece().getHp());
        boolean match = isFactionMatched(i, j);
        if (match && !actionPerformed) {
            gfv.setImage(tileBtn.getName());
            gfv.colourTile(tileBtn);

            //Responses for movement when a tile is clicked
            if (!isMoving() && hasCoordinates() && checkMoveInit(getCoordinates()[0], getCoordinates()[1])) {    // Trigger movement for a piece
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
        coordinate = new int[2];
    }

    //resets the saved coordinate of a tile and the summoned piece which are saved in the gameEngine
    private void reset() {
        resetCoordinates();
        initTileCoord = null;
        initTileCoord = new int[2];
        removeSummonedPiece();
    }

    public boolean hasCoordinates() {
        return coordinate != null;
    }

    // Check if the player and the piece on action is in the same team
    private boolean isFactionMatched(int i, int j) {
        return turn == 0 && getPiece(i, j).getFaction().equals("Rebel") ||
                turn == 1 && getPiece(i, j).getFaction().equals("Royale");
    }

    public int[] getInitTileCoord() {
        return initTileCoord;
    }

    private void setInit(int i, int j) {
        initTileCoord[0] = i;
        initTileCoord[1] = j;
    }


    public int getRows() {
        return BOARD_ROWS;
    }

    public int getCols() {
        return BOARD_COLS;
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
                    turn = turns[0];
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

    private boolean checkSummonValid(Piece piece, int row, int tile) {
        boolean isRowValid;
        int extraMove = 0;

        if (piece.getType().equals("Obstacle")) {
            extraMove = 3;
        }

        if (piece.getFaction().equals("Royale")) {
            isRowValid = row < 3 + extraMove && row >= 1;
        } else {
            isRowValid = row > 9 - extraMove;
        }

        if (checkMoveTarget(row, tile) && isRowValid) {
            getTile(row, tile).setPiece(piece);
            return true;
        } else {
            return false;
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

            // Command is also added as an object to stack
            moves.push(new SummonCommand(gfv.getImage(), i, j));

            return true;
        } else {
            gfv.getStatusLabel().setText(STATUS + "Invalid placement, the first two rows for Royales and the bottom three rows for Rebels.");
            return false;

        }
    }

    public boolean placeMovedPiece(JButton[][] tileBtns, int i, int j) {
        // target tile
        JButton tileBtn = tileBtns[i][j];

        if (move(getInitTileCoord()[0], getInitTileCoord()[1], i, j)) {
            // Command is also added as an object to stack
            moves.push(new MoveCommand(gfv.getImage(), getInitTileCoord()[0], getInitTileCoord()[1], i, j));
            gfv.decolour();
            System.out.println("Image = " + gfv.getImage());
            tileBtn.setIcon(new ImageIcon(this.getClass().getResource(gfv.getImage())));
            tileBtns[getInitTileCoord()[0]][getInitTileCoord()[1]].setIcon(new ImageIcon(
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

    public boolean isWall(int i, int j) {
        return i % 5 <= 2 && j % 4 == 3;
    }

    private boolean isCastle(int i) {
        return i == 0;
    }

    public void placeAttackPiece(int i, int j) {
        JButton[][] tileBtns = gfv.getTileBtns();
        if (attack(getInitTileCoord()[0], getInitTileCoord()[1], i, j)) {
            PieceInterface piece = getPiece(getInitTileCoord()[0], getInitTileCoord()[1]);
            String message;
            message = String.format("%d damage dealt! Remaining HP: %d", piece.getAttackPower(),
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

            resetPiece(getInitTileCoord()[0], getInitTileCoord()[1]);
        } else {
            gfv.getStatusLabel().setText(STATUS + "Tile not valid, press the attack button again to cancel.");
        }
    }

    public void changeButtonViews() {

        gfv.getAttackBtn().setVisible(false);
        gfv.getUndoBtn().setVisible(true);
    }

    public void undoTurn() {

        if (getTurn() == 0 && !rebelUndo) {
            rebelUndo = true;
            accessStack();
        } else if (getTurn() != 0 && !royaleUndo) {
            royaleUndo = true;
            accessStack();
        } else {
            gfv.getStatusLabel().setText(STATUS + "You have already used your Undo for this game!");
        }

    }

    //Undo stack with all game movements excl attacks for now.
    public void accessStack() {

        JButton[][] tileBtns = gfv.getTileBtns();

        if (moves.peek().getClass() == MoveCommand.class) {
            MoveCommand mc = (MoveCommand) moves.pop();
            JButton tileBtn = tileBtns[mc.tooTileRow][mc.tooTileCol];
            getTile(mc.fromTileRow, mc.fromTileCol).setPiece(getPiece(mc.tooTileRow, mc.tooTileCol));
            getTile(mc.tooTileRow, mc.tooTileCol).removePiece();
            tileBtn.setIcon(new ImageIcon(this.getClass().getResource(gfv.getGrass())));
            tileBtns[mc.fromTileRow][mc.fromTileCol].setIcon(new ImageIcon(this.getClass().getResource(mc.image)));
            tileBtns[mc.fromTileRow][mc.fromTileCol].setName(gfv.getImage());

        } else {
            SummonCommand sc = (SummonCommand) moves.pop();
            JButton tileBtnSum = tileBtns[sc.initTileRow][sc.initTileCol];
            getTile(sc.initTileRow, sc.initTileCol).removePiece();
            tileBtnSum.setIcon(new ImageIcon(this.getClass().getResource(gfv.getGrass())));

        }
        gfv.decolour();
        actionPerformed = false;
        gfv.getAttackBtn().setVisible(true);
        gfv.getUndoBtn().setVisible(false);
    }

    private void resetPiece(int i, int j) {
        PieceInterface currentPiece = getPiece(i, j);
        if (currentPiece.isOffensive() || currentPiece.isDefensive()) {
            PieceInterface originalPiece = new ResetDecorator(currentPiece);
            originalPiece.resetStatus();
            getTile(i, j).setPiece(originalPiece);
            System.out.println("Reset!");
            System.out.println(originalPiece.getInitHp());
            System.out.println(originalPiece.getInitAp());
        }
    }

    public void setOffensive() {
        setInit(coordinate[0], coordinate[1]);
        System.out.println(getInitTileCoord()[0] + ", " + getInitTileCoord()[1]);
        if (checkInit(getInitTileCoord()[0], getInitTileCoord()[1])) {
            if (isFactionMatched(getInitTileCoord()[0], getInitTileCoord()[1])) {
                PieceInterface originalPiece = getPiece(getInitTileCoord()[0], getInitTileCoord()[1]);
                if (!originalPiece.isOffensive() && !originalPiece.isDefensive()) {
                    PieceInterface offensivePiece = new SetOffensiveDecorator(originalPiece);
                    offensivePiece.setOffensive();
                    getTile(getInitTileCoord()[0], getInitTileCoord()[1]).setPiece(offensivePiece);

                    System.out.println("Original AP: " + originalPiece.getAttackPower());
                    System.out.println("Original HP: " + originalPiece.getHp());
                    System.out.println("--------------------------------------------");
                    System.out.println("Offensive AP: " + offensivePiece.getAttackPower());
                    System.out.println("Offensive HP: " + offensivePiece.getHp());
                } else {
                    System.out.println("This piece has been strengthened already!");
                }
            } else {
                System.out.println("You cannot strengthen opponent piece!");
            }
        }
    }

    public void setDefensive() {
        setInit(coordinate[0], coordinate[1]);
        System.out.println(getInitTileCoord()[0] + ", " + getInitTileCoord()[1]);
        if (checkInit(getInitTileCoord()[0], getInitTileCoord()[1])) {
            PieceInterface originalPiece = getPiece(getInitTileCoord()[0], getInitTileCoord()[1]);
            if (isFactionMatched(getInitTileCoord()[0], getInitTileCoord()[1])) {
                if (!originalPiece.isOffensive() && !originalPiece.isDefensive()) {
                    PieceInterface defensivePiece = new SetDefensiveDecorator(originalPiece);
                    defensivePiece.setDefensive();
                    getTile(getInitTileCoord()[0], getInitTileCoord()[1]).setPiece(defensivePiece);

                    System.out.println("Original AP: " + originalPiece.getAttackPower());
                    System.out.println("Original HP: " + originalPiece.getHp());
                    System.out.println("--------------------------------------------");
                    System.out.println("Defensive AP: " + defensivePiece.getAttackPower());
                    System.out.println("Defensive HP: " + defensivePiece.getHp());
                } else {
                    System.out.println("This piece has been strengthened already!");
                }
            } else {
                System.out.println("You cannot strengthen opponent piece!");
            }
        }
    }


    public Tile[][] getTiles() {
        return tiles;
    }

}