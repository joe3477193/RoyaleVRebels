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

    public static ArrayList<BoardRows> boardRows;

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
    private boolean hasSummoned;
    private boolean hasMoved;
    private boolean hasAttacked;
    private boolean canDoAction = true;

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

        hasMoved = false;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    // Click button to reset attacking
    public void resetAttacking() {
        isAttacking = false;
        gfv.getFrame().setCursor(new Cursor(DEFAULT_CURSOR));
        if(canDoAction) {
            gfv.getStatusLabel().setText(STATUS + "You can either summon, move your valid piece or attack valid opposite piece in this turn.");
        }
        gfv.decolour();
        depaintAction();
    }

    public void setAttacking() {
        if (isFactionMatched(coordinate[0], coordinate[1])) {
            isAttacking = true;
            setInit(coordinate[0], coordinate[1]);
            Image icon = new ImageIcon(this.getClass().getResource("../../images/target.png")).getImage();
            gfv.getFrame().setCursor(Toolkit.getDefaultToolkit().createCustomCursor(icon, new Point(0, 0), "name"));

            int range = getPiece(coordinate[0], coordinate[1]).getRange();
            int row = coordinate[0];
            int col = coordinate[1];

            paintActionRange(row, col, range, "attack");
        }

        // Attempt to make opposite player's piece to attack
        else {
                gfv.getStatusLabel().setText(GameFrameView.STATUS + "You cannot make your opponent's pieces to attack.");
        }
    }

    // Click button to reset moving and summon
    public void resetMoving() {
        isMoving = false;
        gfv.getFrame().setCursor(new Cursor(DEFAULT_CURSOR));

        if(canDoAction) {
            gfv.getStatusLabel().setText(STATUS + "You can either summon, move your valid piece or attack valid opposite piece in this turn.");
        }

        else if (hasSummoned) {
            gfv.getStatusLabel().setText(GameFrameView.STATUS + "You cannot summon another piece in this turn.");
        }

        gfv.decolour();
        depaintAction();
    }

    public void setMoving() {
        if (isFactionMatched(coordinate[0], coordinate[1])) {
            isMoving = true;
            setInit(coordinate[0], coordinate[1]);
            Image icon = new ImageIcon(this.getClass().getResource("../" + gfv.getImage())).getImage();
            gfv.getFrame().setCursor(Toolkit.getDefaultToolkit().createCustomCursor(icon, new Point(0, 0), "name"));
            int mov = getPiece(coordinate[0], coordinate[1]).getMov();
            int row = coordinate[0];
            int col = coordinate[1];
            paintActionRange(row, col, mov, "move");
        }

        // Attempt to move opposite player's piece
        else {
            gfv.getStatusLabel().setText(GameFrameView.STATUS + "You cannot move your opponent's pieces.");
        }
    }

    private void paintActionRange(int row, int col, int radius, String isActing) {

        for (int i = -radius; i <= radius; i++) {
            for (int j = -radius; j <= radius; j++) {
                if (checkMoveRepaint(row + i, col + j) && abs(i) + abs(j) <= radius) {
                    gfv.colourTile(row + i, col + j, isActing);
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

    private boolean checkMoveRepaint(int i, int j) {
        try {
            return !isWall(i, j) && !getTile(i, j).hasPiece();
        } catch (RuntimeException e) {
            return true;
        }
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public boolean hasAttacked() {
        return hasAttacked;
    }

    public boolean hasSummoned() {
        return hasSummoned;
    }

    private void setMoved() {
        this.hasMoved = true;
        canDoAction = false;
        gfv.colourEndTurn();
    }

    private void setAttcked() {
        this.hasAttacked = true;
        canDoAction = false;
        gfv.colourEndTurn();
    }

    private void setSummoned() {
        this.hasSummoned = true;
        canDoAction = false;
        gfv.colourEndTurn();
    }

    public boolean canDoAction() {
        return canDoAction;
    }

    public void resetAction() {
        hasMoved = false;
        hasSummoned = false;
        hasAttacked = false;
        canDoAction = true;
        gfv.getMoveBtn().setVisible(true);
        gfv.getAttackBtn().setVisible(true);
        gfv.decolourEndTurn();
        gfv.decolour();
        cycleTurn();
        gfv.updateBar(turn);
        gfv.getFrame().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        gfv.getStatusLabel().setText(GameFrameView.STATUS + "");
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

        // can move or attack ideally
        if (match && canDoAction) {
            gfv.setImage(tileBtn.getName());
            gfv.colourTile(tileBtn);

            // check if moveable
            if (checkMoveInit(i, j)) {
                gfv.colourMove();
            } else {
                gfv.colourRedMove();
            }

            // check if attackable
            if (checkAttackInit(i, j)) {
                gfv.colourAttack();
            } else {
                gfv.colourRedAttack();
            }
        }

        // cannot do actions in this turn
        else if(!canDoAction){
            gfv.decolour();
        }

        // click on opposite team's pieces
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
    public boolean checkMoveTarget(int row, int tile) {
        return !getTile(row, tile).hasPiece();
    }

    // Check if pieces can attack target from current tile
    public boolean checkAttackTarget(Piece piece, int tgRow, int tgTile) {
        Tile space = getTile(tgRow, tgTile);
        String inFaction= piece.getFaction();
        String outFaction= space.getPiece().getFaction();
        return space.hasPiece() && !(inFaction.equals(outFaction));
    }

    private boolean isActionRangeValid(int inRow, int inTile, int tgRow, int tgTile, String type) {
        int rowDiff = abs(inRow - tgRow);
        int tileDiff = abs(inTile - tgTile);

        Piece piece = getPiece(inRow, inTile);
        return piece.isActionValid(rowDiff + tileDiff, type);
    }

    // Check if pieces hasMoved from current tile to target tile
    public boolean move(int inRow, int inTile, int tgRow, int tgTile) {
        if (checkMoveTarget(tgRow, tgTile) && isActionRangeValid(inRow, inTile, tgRow, tgTile, "mov")) {
            getTile(tgRow, tgTile).setPiece(getPiece(inRow, inTile));
            getTile(inRow, inTile).removePiece();
            return true;
        }
        return false;
    }


    // Check if pieces hasAttacked target pieces
    public boolean attack(int inRow, int inTile, int tgRow, int tgTile) {
        if (checkInit(tgRow, tgTile) && checkAttackTarget(getPiece(inRow, inTile), tgRow, tgTile) &&
                isActionRangeValid(inRow, inTile, tgRow, tgTile, "range")) {
            getPiece(tgRow, tgTile).attackedBy(getPiece(inRow, inTile).getAttack());
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
        if (checkSummonValid(getSummonedPiece(), i, j) && !hasSummoned) {
            tileBtn.setIcon(new ImageIcon(this.getClass().getResource("../" + gfv.getImage())));
            
            tileBtn.setName(gfv.getImage());
            gfv.getStatusLabel().setText(STATUS + "A new " + getSummonedPiece().getName() + " has been summoned!");
            removeSummonedPiece();
            gfv.getFrame().setCursor(new Cursor(DEFAULT_CURSOR));
            setSummoned();
            gfv.getMoveBtn().setVisible(false);
            gfv.getAttackBtn().setVisible(false);
        } else {
            gfv.getStatusLabel().setText(GameFrameView.STATUS + "Please place the pieces on a valid tile,\n"
                    + "The top three rows for Royales,\nThe bottom three rows for Rebels.");
        }
    }

    public void placeMovedPiece(JButton[][] tileBtns, int i, int j) {
        JButton tileBtn = tileBtns[i][j];
        if (move(getInitTileCoord()[0], getInitTileCoord()[1], i, j)) {
            gfv.decolour();
            gfv.getMoveBtn().setVisible(false);
            gfv.getAttackBtn().setVisible(false);
            System.out.println("Image= " + gfv.getImage());
            tileBtn.setIcon(new ImageIcon(this.getClass().getResource("../" + gfv.getImage())));
            tileBtns[getInitTileCoord()[0]][getInitTileCoord()[1]].setIcon(new ImageIcon(
                    this.getClass().getResource("../" + gfv.getGrass())));
            resetMoving();
            setMoved();
            tileBtn.setName(gfv.getImage());
            gfv.getStatusLabel().setText(gfv.STATUS + "Piece has been moved to the target tile.");
        } else {
            gfv.getStatusLabel().setText(gfv.STATUS + "Tile not valid, press the move button again to cancel.");
        }
    }

    public boolean isWall(int i, int j) {
        return i % 5 <= 2 && j % 4 == 3;
    }

    public void placeAttackPiece(int i, int j){
        JButton tileBtns[][] = gfv.getTileBtns();
        if (attack(getInitTileCoord()[0], getInitTileCoord()[1], i, j)) {
            Piece piece= getPiece(getInitTileCoord()[0], getInitTileCoord()[1]);
            String message;
            message= String.format("%d damage dealt! Remaining HP: %d", piece.getAttack(),
            getPiece(i,j).getHp());
            if(getPiece(i,j).isDead()){
                message+=String.format(", %s is dead!", getPiece(i,j).getName());
                getTile(i,j).removePiece();
                tileBtns[i][j].setIcon(new ImageIcon(this.getClass().getResource("../../images/grass.png")));
            }
            gfv.decolour();
            resetAttacking();
            setAttcked();
            gfv.getMoveBtn().setVisible(false);
            gfv.getAttackBtn().setVisible(false);
            gfv.getStatusLabel().setText(gfv.STATUS + message);
        } else {
            gfv.getStatusLabel().setText(GameFrameView.STATUS + "Tile not valid, press the attack button again to cancel.");
        }
    }

}
