package controller.gameController;

import controller.commandPattern.CommandMonitor;
import controller.gameActionListeners.*;
import controller.gameMouseAdapters.HoverDeckMouseAdapter;
import controller.gameMouseAdapters.HoverTileMouseAdapter;
import model.gameEngine.GameEngine;
import model.gameEngine.PieceTile;
import model.gameEngine.TileInterface;
import model.piece.AbtractPiece.PieceInterface;
import model.piece.PieceCache;
import view.gameView.GameFrameView;
import view.mainView.MainMenuView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;

public class GameController {

    private static final int TIME_LIMIT = 60;
    private static final int TIME_DELAY = 0;
    private static final int TIME_PERIOD = 1000;
    private static final int TIME_OUT = -1;
    private static final int DECK_LENGTH = 7;

    private static final String HAS_PERFORMED = "You have already perform an action this turn.";
    private static final String CLICK_BRICK_WALL = "Please do not click a brick wall.";
    private static final String TIME_REMAIN = "Time Remaining: ";
    private static final String SUMMON = "Summon";
    private static final String MOVEMENT = "Move";
    private static final String ATTACK = "Attack";
    private static final String GAME_SAVED = "Game has been successfully saved.";

    private Timer timer;

    private GameEngine g;
    private GameFrameView gfv;
    private CommandMonitor cm;

    public GameController(GameEngine g, GameFrameView gfv) {
        this.g = g;
        this.gfv = gfv;
        cm = new CommandMonitor(g);
        addActionListeners();
        startTimer();
        addMouseAdapters();
    }

    private void addMouseAdapters() {
        HoverTileMouseAdapter tileListener = new HoverTileMouseAdapter(this);
        HoverDeckMouseAdapter deckListener = new HoverDeckMouseAdapter(this);
        // add MouseAdapters for tileBtns
        for (JButton[] tileRows : gfv.getTileBtns()) {
            for (JButton tileBtn : tileRows) {
                tileBtn.addMouseListener(tileListener);
            }
        }
        // add MouseAdapters for deckBtns
        for (JButton deckBtns : gfv.getSummonBtns()) {
            deckBtns.addMouseListener(deckListener);
        }
    }

    private void addActionListeners() {
        SummonBtnActionListener summonListener = new SummonBtnActionListener(this);
        TileBtnActionListener tileListener = new TileBtnActionListener(this);
        // add ActionListeners for summonBtns
        for (JButton btn : gfv.getSummonBtns()) {
            btn.addActionListener(summonListener);
        }
        // add ActionListeners for tileBtns
        for (JButton[] tileRows : gfv.getTileBtns()) {
            for (JButton tile : tileRows) {
                tile.addActionListener(tileListener);
            }
        }
        // add ActionListeners for attackBtn, offensiveBtn, defensiveBtn, endTurnBtn, saveBtn, quitBtn, undoBtn
        gfv.getAttackBtn().addActionListener(new AttackBtnActionListener(this));
        gfv.getOffensiveBtn().addActionListener(new OffensiveBtnActionListener(this));
        gfv.getDefensiveBtn().addActionListener(new DefensiveBtnActionListener(this));
        gfv.getEndTurnBtn().addActionListener(new EndTurnBtnActionListener(this));
        gfv.getSaveButton().addActionListener(new SaveBtnActionListener(this));
        gfv.getQuitButton().addActionListener(new QuitBtnActionListener(this));
        gfv.getUndoBtn().addActionListener(new UndoBtnActionListener(this));
    }

    // clicking on a piece to summon
    public void summonButton(ActionEvent e) {
        Cursor cursor = gfv.getCursor();
        JButton[] button;
        String[] name;
        String[] image;
        // reset the previous turn's actions such as moving and attacking
        g.resetMoving();
        g.resetAttacking();
        gfv.setCursor(cursor);
        // check if it is rebel's turn
        if (g.getTurn() == g.getRebelTurn()) {
            button = gfv.getRebelButton();
            name = gfv.getRebelName();
            image = gfv.getRebelImage();
        } else {
            button = gfv.getRoyaleButton();
            name = gfv.getRoyaleName();
            image = gfv.getRoyaleImage();
        }
        for (int i = 0; i < button.length; i++) {
            if (gfv.getSource(e) == button[i]) {
                Image icon = new ImageIcon(this.getClass().getResource(image[i])).getImage();
                // click on the same piece on the deck, i.e. cancel summon
                if (gfv.getCursor().getName().equals(name[i])) {
                    gfv.resetCursor();
                    g.removeSummonedPiece();
                    gfv.removeImage();
                }
                // click on a different piece on the deck when the player has not moved any piece, i.e. change summon
                else if (!g.getPerformed()) {
                    gfv.setCursor(icon, name[i]);
                    g.createSummonedPiece(name[i]);
                    gfv.setImage(image[i]);
                    g.paintSummonRange(name[i]);
                } else {
                    gfv.updateStatus(HAS_PERFORMED);
                }
            }
        }
    }

    public void clickTile(ActionEvent e) {
        // check if tile is clicked when a piece is not moving nor attacking
        if (!g.isMoving() && !g.isAttacking()) {
            gfv.decolour();
        }
        // no piece is chosen in the gameEngine
        g.resetCoordinates();
        int row = gfv.findButtonCoordinates(e)[GameFrameView.ROW_PROPERTY_INDEX];
        int col = gfv.findButtonCoordinates(e)[GameFrameView.COL_PROPERTY_INDEX];
        // click a brick wall
        if (g.isWallTile(row, col)) {
            gfv.updateStatus(CLICK_BRICK_WALL);
        }
        // attempt to place piece
        else {
            // attempt to place a summoned piece
            if (g.getSummonedPiece() != null && !g.getPerformed()) {
                cm.executeTurn(SUMMON, gfv.getImage(), row, col, g.getSummonedPiece());
            }
            // attempt to place a piece during movement
            else if (g.isMoving() && !g.getPerformed()) {
                cm.executeTurn(MOVEMENT, gfv.getImage(), row, col, g.getSummonedPiece());
            }
            // attempt to place a piece during attack
            else if (g.isAttacking() && !g.getPerformed()) {
                cm.executeTurn(ATTACK, gfv.getImage(), row, col, g.getSummonedPiece());
            }
            // attempt to pick a piece for action && also show piece info
            else if (g.isPieceTile(row, col)) {
                gfv.updateStatus("");
                g.clickTile(row, col);
            }
            // attempt to click on an empty tile
            else {
                gfv.updateStatus("");
            }
        }
    }

    public void endTurn() {
        g.unsetPerformed();
        stopTimer();
    }

    public void attack() {
        // Cancel attack, i.e. click attack button twice
        if (g.isAttacking() && !g.getPerformed()) {
            g.resetAttacking();
            gfv.colourAttack();
        }
        // Trigger attack for a piece
        else if (g.hasCoordinates() && g.checkOnBoardPieceAttackable(g.getCoordinates()[g.getRowIndex()], g.getCoordinates()[g.getColIndex()]) && !g.getPerformed()) {
            g.resetMoving();
            g.setAttacking();
        }
    }

    // reused codes in timer section
    private TimerTask timer() {
        return new TimerTask() {

            int second = TIME_LIMIT;

            @Override
            public void run() {
                Duration duration = Duration.ofSeconds(second--);
                gfv.updateTime(TIME_REMAIN + duration.toMinutesPart() + ":" + duration.toSecondsPart());
                if (second == TIME_OUT) {
                    endTurn();
                }
            }
        };
    }

    private void startTimer() {
        TimerTask t = timer();
        timer = new Timer();
        timer.schedule(t, TIME_DELAY, TIME_PERIOD);
    }

    private void stopTimer() {
        TimerTask t = timer();
        timer.cancel();
        timer = new Timer();
        timer.schedule(t, TIME_DELAY, TIME_PERIOD);
    }

    public void setOffensive() {
        g.setOffensive();
    }

    public void setDefensive() {
        g.setDefensive();
    }

    public void undoTurn() {
        cm.undoTurn();
    }

    public void quitGame() {
        gfv.disposeFrame();
        new MainMenuView();
    }

    public void hoverDeck(MouseEvent e) {
        String[] pieceNames;
        if (g.getTurn() == g.getRebelTurn()) {
            pieceNames = gfv.getRebelName();
        } else {
            pieceNames = gfv.getRoyaleName();
        }
        // For Rebel, i varies from 0 to 6, while i varies from 7 - 13 for Royale
        int i = gfv.findButtonIndex(e) % DECK_LENGTH;
        PieceInterface deckPiece = PieceCache.getPiece(pieceNames[i]);
        String pieceInfo = "<html>Name: " + deckPiece.getName() + "<br>Faction: " + deckPiece.getFaction()
                + "<br>Type: " + deckPiece.getType() + "<br>HP: " + deckPiece.getHp() + "<br>Attack Power: "
                + deckPiece.getAttackPower() + "<br>Defence: " + deckPiece.getDefence() + "<br>Attack Range: "
                + deckPiece.getAttackRange() + "<br>Move Speed: " + deckPiece.getMoveSpeed() + "<br>OFFENSIVE: "
                + deckPiece.isOffensive() + "<br>DEFENSIVE: " + deckPiece.isDefensive() + "</html>";
        gfv.showPieceInfo(e, pieceInfo);
    }

    public void hoverTile(MouseEvent e) {
        // show on board piece's info
        int i = gfv.findButtonCoordinates(e)[GameFrameView.ROW_PROPERTY_INDEX];
        int j = gfv.findButtonCoordinates(e)[GameFrameView.COL_PROPERTY_INDEX];
        TileInterface tile = g.getTiles()[i][j];
        // TODO: UNIMPLEMENTED
        // Change attack target color if is attacking
        // g.changeAttackTarget(tile, i, j);
        // check if the tile has piece, show the piece info
        if (tile instanceof PieceTile) {
            PieceInterface piece = tile.getPiece();
            String pieceInfo = "<html>Name: " + piece.getName() + "<br>Faction: " + piece.getFaction()
                    + "<br>Type: " + piece.getType() + "<br>HP: " + piece.getHp() + "<br>Attack Power: "
                    + piece.getAttackPower() + "<br>Defence: " + piece.getDefence() + "<br>Attack Range: "
                    + piece.getAttackRange() + "<br>Move Speed: " + piece.getMoveSpeed() + "<br>OFFENSIVE: "
                    + piece.isOffensive() + "<br>DEFENSIVE: " + piece.isDefensive() + "</html>";
            gfv.showPieceInfo(e, pieceInfo);
        }
    }

    public void saveGame() {
        if (g.saveGame()) {
            gfv.updateStatus(GAME_SAVED);
        }
    }
}

