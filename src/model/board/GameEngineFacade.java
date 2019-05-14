package model.board;

import model.pieces.Piece;
import model.pieces.PieceInterface;
import model.pieces.decorator.SetDefensiveDecorator;
import model.pieces.decorator.SetOffensiveDecorator;
import view.GameFrameView;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;

import static java.awt.Cursor.DEFAULT_CURSOR;
import static java.lang.Math.abs;
import static view.GameFrameView.STATUS;

public class GameEngineFacade implements GameEngine{

    public static final int BOARD_ROWS = 13; // increments in 5
    public static final int BOARD_COLS = 15; // increments in 4
    private GameFrameView gfv;
    private Piece summonedPiece;
    private Tile[][] tiles;

    // Initialize current turn;

    private int turn;

    private int[] turns;
    private int[] coordinate;
    private int[] initTileCoord;
    private boolean isMoving;
    private boolean isAttacking;
    private boolean actionPerformed;

    public GameEngineFacade(GameFrameView gfv) {
        this.gfv = gfv;

        tiles = new Tile[BOARD_ROWS][BOARD_COLS];

        for (int i = 0; i < BOARD_ROWS; i++) {
            for (int j = 0; j < BOARD_COLS; j++) {
                if ((i % 5 <= 2) && j % 4 == 3) {
                    tiles[i][j] = new WallTile();
                    Tile tile = TileFactory.getWallTile();
                    tile.draw(gfv, i, j);
                }
                else if (i == 0) {
                    tiles[i][j] = new CastleTile();
                    Tile tile = TileFactory.getCastleTile();
                    tile.draw(gfv, i, j);
                }
                else {
                    tiles[i][j] = new GrassTile();
                    Tile tile = TileFactory.getGrassTile();
                    tile.draw(gfv, i, j);
                }
            }
        }

        initTileCoord = new int[2];

        // Initialize number of player turns
        turns = new int[]{0, 1};

        // Initialize current turn;
        turn = 0;

        actionPerformed = false;
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
            Image icon = new ImageIcon(this.getClass().getResource("../../images/target.png")).getImage();
            gfv.getFrame().setCursor(Toolkit.getDefaultToolkit().createCustomCursor(icon, new Point(0, 0), "name"));

            int range = getPiece(coordinate[0], coordinate[1]).getAttackRange();
            int row = coordinate[0];
            int col = coordinate[1];

            // paintAttack(); to make diff colour for attack range
            paintMovAttackRange(row, col, "attackRange");

        }

        // Attempt to move opposite player's piece
        else {
            gfv.getStatusLabel().setText(STATUS + "You cannot move or attack using your opponent's pieces.");
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
            Image icon = new ImageIcon(this.getClass().getResource("../" + gfv.getImage())).getImage();
            gfv.getFrame().setCursor(Toolkit.getDefaultToolkit().createCustomCursor(icon, new Point(0, 0), "name"));

            int mov = getPiece(coordinate[0], coordinate[1]).getMoveSpeed();
            int row = coordinate[0];
            int col = coordinate[1];
            paintMovAttackRange(row, col, "moveSpeed");
        }

        // Attempt to move opposite player's piece
        else {
            gfv.getStatusLabel().setText(STATUS + "You cannot move your opponent's pieces.");
        }
    }

    // want to change colour for showing movement and attack range on tiles
    private void paintMovAttackRange(int row, int col, String actionType) {
        Piece piece = getPiece(coordinate[0], coordinate[1]);
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
                    tileBtns[i][j].setIcon(new ImageIcon(this.getClass().getResource("../../images/grass.png")));
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
        gfv.getMoveBtn().setVisible(true);
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
        System.out.println("TileButton Name: " + tileBtn.getName());
        boolean match = isFactionMatched(i, j);
        if (match && !actionPerformed) {
            gfv.setImage(tileBtn.getName());
            gfv.colourTile(tileBtn);
            if (checkMoveInit(i, j)) {
                gfv.colourMove();
            } else {
                gfv.colourRedMove();
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

    //resets the saved coordinate of a tile and the summoned piece which are saved in the board
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

    private int[] getInitTileCoord() {
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

    // Gets a pieces from a tile
    private Piece getPiece(int row, int col) {
        return getTile(row, col).getPiece();
    }


    // Check if pieces has been initialized successfully in current tile
    public boolean checkInit(int row, int col) {
        return getTile(row, col).hasPiece();
    }

    // Check if pieces in current tile can move
    public boolean checkMoveInit(int row, int col) {
        return checkInit(row, col) && getTile(row, col).getPiece().isMoveable();
    }

    // Check if pieces in current tile can attack
    public boolean checkAttackInit(int row, int col) {
        return checkInit(row, col) && getTile(row, col).getPiece().isAttackable();
    }

    // Check if pieces can move from current tile to target tile
    private boolean checkMoveTarget(int row, int col) {
        return !getTile(row, col).hasPiece() && !isCastle(row);
    }

    // Check if pieces can attack target from current tile
    private boolean checkAttackTarget(Piece piece, int tgRow, int tgCol) {
        Tile space = getTile(tgRow, tgCol);
        String inFaction = piece.getFaction();
        String outFaction = space.getPiece().getFaction();
        return space.hasPiece() && !(inFaction.equals(outFaction));
    }

    //checks if the target tile is valid to be attacked/moved into by the selected peicee
    private boolean isMovRangeValid(int inRow, int inCol, int tgRow, int tgCol, String type) {
        int rowDiff = abs(inRow - tgRow);
        int tileDiff = abs(inCol - tgCol);

        Piece piece = getPiece(inRow, inCol);

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

    // Check if pieces actionPerformed from current tile to target tile
    private boolean move(int inRow, int inCol, int tgRow, int tgCol) {

        if (checkMoveTarget(tgRow, tgCol) && isMovRangeValid(inRow, inCol, tgRow, tgCol, "moveSpeed")) {

            // Check if a piece will go across opposite piece no matter what directions, which is forbidden
            if (checkAcross(inRow, inCol, tgRow, tgCol)) {
                return false;
            }
            getTile(tgRow, tgCol).setPiece(getPiece(inRow, inCol));
            getTile(inRow, inCol).removePiece();
            return true;
        }
        return false;
    }

    private boolean checkAcross(int inRow, int inCol, int tgRow, int tgCol) {
        Tile currTile;
        if (inCol == tgCol) {
            Tile initTile = getTile(inRow, inCol);
            for (int i = 1; i < Math.abs(inRow - tgRow) + 1; i++) {
                if (tgRow < inRow) {
                    currTile = getTile(inRow-i, inCol);
                } else {
                    currTile = getTile(inRow+i, inCol);
                }
                if (currTile.hasPiece()) {
                    if (!currTile.getPiece().getFaction().equals(initTile.getPiece().getFaction())) {
                        return true;
                    }
                }
            }
        } else if (inRow == tgRow) {
            Tile initTile = getTile(inRow, inCol);
            for (int j = 1; j < Math.abs(inCol - tgCol) + 1; j++) {
                if (tgCol < inCol) {
                    currTile = getTile(inRow, inCol-j);
                } else {
                    currTile = getTile(inRow, inCol+j);
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

    // Check if pieces hasAttacked target pieces
    private boolean attack(int inRow, int inCol, int tgRow, int tgCol) {
        if (checkInit(tgRow, tgCol) && checkAttackTarget(getPiece(inRow, inCol), tgRow, tgCol) &&
                isMovRangeValid(inRow, inCol, tgRow, tgCol, "attackRange")) {
            getPiece(tgRow, tgCol).attackedBy(getPiece(inRow, inCol).getAttackPower());
            return true;
        }
        return false;
    }

    public void createPiece(String name) {
        try {
            Class pieceCls = Class.forName("model.pieces.type." + name);
            this.summonedPiece = (Piece) pieceCls.getDeclaredConstructor().newInstance();

        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException ex) {
            ex.printStackTrace();
        }

    }

    private boolean checkSummonValid(Piece piece, int row, int col) {
        boolean isRowValid;
        int extraMove= 0;

        if(piece.getType().equals("Obstacle")){
            extraMove= 3;
        }

        if (piece.getFaction().equals("Royale")) {
            isRowValid = row < 3 + extraMove && row >= 1;
        } else {
            isRowValid = row > 9 - extraMove;
        }

        if (checkMoveTarget(row, col) && isRowValid) {
            getTile(row, col).setPiece(piece);
            return true;
        } else {
            return false;
        }
    }

    public void placeSummonedPiece(JButton tileBtn, int i, int j) {
        if (checkSummonValid(getSummonedPiece(), i, j)) {
            tileBtn.setIcon(new ImageIcon(this.getClass().getResource("../" + gfv.getImage())));
            tileBtn.setName(gfv.getImage());
            removeSummonedPiece();
            gfv.getFrame().setCursor(new Cursor(DEFAULT_CURSOR));
            setActionPerformed();
            gfv.getMoveBtn().setVisible(false);
            gfv.getAttackBtn().setVisible(false);
        } else {
            gfv.getStatusLabel().setText(STATUS + "Invalid placement, the first two rows for Royales and the bottom three rows for Rebels.");
        }
    }

    public void placeMovedPiece(JButton[][] tileBtns, int i, int j) {

        // target tile
        JButton tileBtn = tileBtns[i][j];


        if (move(getInitTileCoord()[0], getInitTileCoord()[1], i, j)) {

            gfv.decolour();
            System.out.println("Image= " + gfv.getImage());
            tileBtn.setIcon(new ImageIcon(this.getClass().getResource("../" + gfv.getImage())));
            tileBtns[getInitTileCoord()[0]][getInitTileCoord()[1]].setIcon(new ImageIcon(
                    this.getClass().getResource("../" + gfv.getGrass())));
            resetMoving();
            setActionPerformed();
            gfv.getMoveBtn().setVisible(false);
            gfv.getAttackBtn().setVisible(false);
            tileBtn.setName(gfv.getImage());
            gfv.getFrame().setCursor(new Cursor(DEFAULT_CURSOR));
        } else {
            gfv.getStatusLabel().setText(STATUS + "Tile not valid, press the move button again to cancel.");
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
                tileBtns[i][j].setIcon(new ImageIcon(this.getClass().getResource("../../images/grass.png")));
            }
            gfv.decolour();
            resetAttacking();
            setActionPerformed();
            gfv.getMoveBtn().setVisible(false);
            gfv.getAttackBtn().setVisible(false);
            gfv.getFrame().setCursor(new Cursor(DEFAULT_CURSOR));
            gfv.getStatusLabel().setText(STATUS + message);
        } else {
            gfv.getStatusLabel().setText(STATUS + "Tile not valid, press the attack button again to cancel.");
        }
    }

    public void setOffensive() {
        setInit(coordinate[0], coordinate[1]);
        System.out.println(getInitTileCoord()[0]+ ", " + getInitTileCoord()[1]);
        PieceInterface piece = getPiece(getInitTileCoord()[0], getInitTileCoord()[1]);
        PieceInterface piece1 = new SetOffensiveDecorator(piece);
        piece1.setOffensive();

        System.out.println("Original AP: " + piece.getAttackPower());
        System.out.println("Original HP: " + piece.getHp());
        System.out.println("--------------------------------------------");
        System.out.println("Offensive AP: " + piece1.getAttackPower());
        System.out.println("Offensive HP: " + piece1.getHp());
    }

    public void setDefensive() {
        setInit(coordinate[0], coordinate[1]);
        PieceInterface piece = getPiece(getInitTileCoord()[0], getInitTileCoord()[1]);
        PieceInterface piece1 = new SetDefensiveDecorator(piece);
        piece1.setDefensive();

        System.out.println("Original AP: " + piece.getAttackPower());
        System.out.println("Original HP: " + piece.getHp());
        System.out.println("--------------------------------------------");
        System.out.println("Defensive AP: " + piece1.getAttackPower());
        System.out.println("Defensive HP: " + piece1.getHp());
    }

}
