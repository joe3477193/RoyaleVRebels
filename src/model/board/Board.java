package model.board;

import model.pieces.Piece;
import view.GameFrameView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static java.awt.Cursor.DEFAULT_CURSOR;
import static java.lang.Math.abs;
import static view.GameFrameView.STATUS;

public class Board {

    static ArrayList<BoardRows> boardRows;

    private GameFrameView gfv;

    public static final int BOARD_ROWS = 13; // increments in 5
    public static final int BOARD_COLS = 15; // increments in 4

    private Piece summonedPiece;

    // Initialize current turn;
    private int turn;
    private int[] turns;
    private int[] coordinate;
    private int[] initTileCoord;
    private boolean isMoving;
    private boolean isAttacking;
    private boolean actionPerformed;

    public Board(GameFrameView gfv) {
        this.gfv = gfv;

        Board.boardRows = new ArrayList<>(BOARD_ROWS);

        for (int i = 0; i < BOARD_ROWS; i++) {
            boardRows.add(new BoardRows());
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

    public void resetAttacking() {
        isAttacking = false;
        gfv.decolour();

        depaintAction();
    }

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
            paintMove(row, col, range);

        }

        // Attempt to move opposite player's piece
        else {
                gfv.getStatusLabel().setText(STATUS + "You cannot move your opponent's pieces.");
        }
    }

    public void resetMoving() {
        isMoving = false;
        gfv.decolour();
        depaintAction();
    }

    public void setMoving() {
        if (isFactionMatched(coordinate[0], coordinate[1])) {
            isMoving = true;
            setInit(coordinate[0], coordinate[1]);
            Image icon = new ImageIcon(this.getClass().getResource("../" + gfv.getImage())).getImage();
            gfv.getFrame().setCursor(Toolkit.getDefaultToolkit().createCustomCursor(icon, new Point(0, 0), "name"));

            int mov = getPiece(coordinate[0], coordinate[1]).getMoveSpeed();
            int row = coordinate[0];
            int col = coordinate[1];
            paintMove(row, col, mov);
        }


        // Attempt to move opposite player's piece
        else {
            gfv.getStatusLabel().setText(STATUS + "You cannot move your opponent's pieces.");
        }
    }

    // want to change colour for showing attack range
    private void paintMove(int row, int col, int radius) {
        for (int i = -radius; i <= radius; i++) {
            for (int j = -radius; j <= radius; j++) {
                if (checkMoveRepaint(row + i, col + j) && abs(i) + abs(j) <= radius) {
                    gfv.colourTile(row + i, col + j);
                }
            }
        }
    }

    private void depaintAction() {
        JButton[][] tileBtns = gfv.getTileBtns();
        for (int i = 0; i < BOARD_ROWS; i++) {
            for (int j = 0; j < BOARD_COLS; j++) {
                if (!isWall(i, j) && !getTile(i, j).hasPiece()) {
                    tileBtns[i][j].setIcon(new ImageIcon(this.getClass().getResource("../../images/grass.png")));
                }
            }
        }
    }

    // Should we show the colour on the tile with opposite piece that player want to attack? I thk we should. --Andy
    private boolean checkMoveRepaint(int i, int j) {
        try {
            return !isWall(i, j) && !getTile(i, j).hasPiece();
        } catch (RuntimeException e) {
            return true;
        }
    }

    public boolean getActionPerformed() {
        return actionPerformed;
    }

    private void setActionPerformed() {
        actionPerformed = true;
        gfv.colourEndTurn();
    }

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
        }
        else if(actionPerformed){
            gfv.decolour();
        }
        else {
            gfv.colourRed(tileBtn);
        }
    }

    public void resetCoordinates() {
        coordinate = null;
        coordinate = new int[2];
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

    private Tile getTile(int row, int tile) {
        return boardRows.get(row).getTile(tile);
    }

    // Gets a pieces from a tile
    private Piece getPiece(int row, int tile) {
        return getTile(row, tile).getPiece();
    }


    // Check if pieces has been initialized successfully in current tile
    public boolean checkInit(int row, int tile) {
        return getTile(row, tile).hasPiece();
    }

    // Check if pieces in current tile is moveable
    public boolean checkMoveInit(int row, int tile) {
        return checkInit(row, tile) && getTile(row, tile).getPiece().isMoveable();
    }

    // Check if pieces in current tile is attackable
    public boolean checkAttackInit(int row, int tile) {
        return checkInit(row, tile) && getTile(row, tile).getPiece().isAttackable();
    }

    // Check if pieces can move from current tile to target tile
    boolean checkMoveTarget(int row, int tile) {
        return !getTile(row, tile).hasPiece();
    }

    // Check if pieces can attack target from current tile
    boolean checkAttackTarget(Piece piece, int tgRow, int tgTile) {
        Tile space = getTile(tgRow, tgTile);
        String inFaction= piece.getFaction();
        String outFaction= space.getPiece().getFaction();
        return space.hasPiece() && !(inFaction.equals(outFaction));
    }

    private boolean isMovRangeValid(int inRow, int inTile, int tgRow, int tgTile, String type) {
        int rowDiff = abs(inRow - tgRow);
        int tileDiff = abs(inTile - tgTile);

        Piece piece = getPiece(inRow, inTile);
        return piece.isMoveValid(rowDiff + tileDiff, type);
    }

    // Check if pieces actionPerformed from current tile to target tile
    boolean move(int inRow, int inTile, int tgRow, int tgTile) {
        if (checkMoveTarget(tgRow, tgTile) && isMovRangeValid(inRow, inTile, tgRow, tgTile, "moveSpeed")) {
            getTile(tgRow, tgTile).setPiece(getPiece(inRow, inTile));
            getTile(inRow, inTile).removePiece();
            return true;
        }
        return false;
    }


    // Check if pieces hasAttacked target pieces
    boolean attack(int inRow, int inTile, int tgRow, int tgTile) {
        if (checkInit(tgRow, tgTile) && checkAttackTarget(getPiece(inRow, inTile), tgRow, tgTile) &&
                isMovRangeValid(inRow, inTile, tgRow, tgTile, "attackRange")) {
            getPiece(tgRow, tgTile).attackedBy(getPiece(inRow, inTile).getAttackPower());
            return true;
        }
        return false;
    }

    public void createPiece(String name) {
        try {
            Class pieceCls = Class.forName("model.pieces.type." + name);
            this.summonedPiece = (Piece) pieceCls.newInstance();

        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            ex.printStackTrace();
        }

    }

    private boolean checkSummonValid(Piece piece, int row, int tile) {
        boolean isRowValid;
        if (piece.getFaction().equals("Royale")) {
            isRowValid = row < 3;
        } else {
            isRowValid = row > 9;
        }

        if (checkMoveTarget(row, tile) && isRowValid) {
            getTile(row, tile).setPiece(piece);
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
            gfv.getStatusLabel().setText(STATUS + "Please place the pieces on a valid tile,\n"
                    + "The top three rows for Royales,\nThe bottom three rows for Rebels.");
        }
    }

    public void placeMovedPiece(JButton[][] tileBtns, int i, int j) {
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

    public void placeAttackPiece(int i, int j){
        JButton[][] tileBtns = gfv.getTileBtns();
        if (attack(getInitTileCoord()[0], getInitTileCoord()[1], i, j)) {
            Piece piece= getPiece(getInitTileCoord()[0], getInitTileCoord()[1]);
            String message;
            message= String.format("%d damage dealt! Remaining HP: %d", piece.getAttackPower(),
            getPiece(i,j).getHp());
            if(getPiece(i,j).isDead()){
                message+=String.format(", %s is dead!", getPiece(i,j).getName());
                getTile(i,j).removePiece();
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

}
