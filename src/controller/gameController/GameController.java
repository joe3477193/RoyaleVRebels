package controller.gameController;

import controller.commandPattern.AttackCommand;
import controller.commandPattern.CommandMonitor;
import controller.commandPattern.MoveCommand;
import controller.commandPattern.SummonCommand;
import controller.gameActionListeners.*;
import controller.gameMouseAdapters.HoverDeckMouseAdapter;
import controller.gameMouseAdapters.HoverTileMouseAdapter;
import model.gameEngine.GameEngine;
import model.piece.AbtractPiece.PieceInterface;
import model.piece.PieceCache;
import model.tile.PieceTile;
import model.tile.TileInterface;
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

    private static final String SUMMON_MESSAGE= "Summoning Piece ";
    private static final String NOT_ENOUGH_CP = "Your CP is not enough to summon ";
    private static final String HAS_PERFORMED = "You have already perform an action this turn.";
    private static final String CLICK_BRICK_WALL = "Please do not click a brick wall.";
    private static final String TIME_REMAIN = "Time Remaining: ";
    private static final String GAME_SAVED = "Game has been successfully saved.";
    private static final String DECK_PIECE_INFO = "<html>Name: %s<br>CP consumed: %d<br>Faction: %s<br>HP: %d<br>Attack Power: %d<br>Defence: %d<br>Attack Range: %d<br>Move Speed: %d<br>OFFENSIVE: %b<br>DEFENSIVE: %b</html>";
    private static final String ONBOARD_PIECE_INFO = "<html>Name: %s<br>Faction: %s<br>HP: %d<br>Attack Power: %d<br>Defence: %d<br>Attack Range: %d<br>Move Speed: %d<br>OFFENSIVE: %b<br>DEFENSIVE: %b</html>";
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
        String cursorName = gfv.getCursor().getName();
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
        }
        else {
            button = gfv.getRoyaleButton();
            name = gfv.getRoyaleName();
            image = gfv.getRoyaleImage();
        }
        for (int i = 0; i < button.length; i++) {
            if (gfv.getSource(e) == button[i]) {
                Image icon = new ImageIcon(this.getClass().getResource(image[i])).getImage();
                // click on the same piece on the deck, i.e. cancel summon
                if (cursorName.equals(name[i])) {
                    gfv.resetCursor();
                    g.removeSummonedPiece();
                    gfv.removeImage();
                }
                // click on a different piece on the deck when the player has not moved any piece, i.e. change summon
                else if (!g.getPerformed()) {
                    if(g.getCurrentPlayer().isEnoughCP(PieceCache.getPiece(name[i]).getCp())) {
                        gfv.updateStatus(SUMMON_MESSAGE + name[i]);
                        gfv.setCursor(icon, name[i]);
                        g.createSummonedPiece(name[i]);
                        gfv.setImage(image[i]);
                        g.paintSummonRange(name[i]);
                    }
                    else{
                        gfv.updateStatus(NOT_ENOUGH_CP + name[i]);
                    }
                }
                else {
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
                SummonCommand sc = new SummonCommand(g, row, col, gfv.getImage());
                cm.executeTurn(sc);
            }
            // attempt to place a piece during movement
            else if (g.isMoving() && !g.getPerformed()) {
                MoveCommand mc = new MoveCommand(g, row, col, gfv.getImage());
                cm.executeTurn(mc);
                //cm.executeTurn(MOVEMENT, gfv.getImage(), row, col, g.getSummonedPiece());
            }
            // attempt to place a piece during attack
            else if (g.isAttacking() && !g.getPerformed()) {
                AttackCommand ac = new AttackCommand(g, row, col);
                cm.executeTurn(ac);
                //cm.executeTurn(ATTACK, gfv.getImage(), row, col, g.getSummonedPiece());
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
        if (g.checkWin()) {
            g.gameOver(g.getRoyalePlayer().getName());
        }
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
        }
        else {
            pieceNames = gfv.getRoyaleName();
        }
        // for Rebel, i varies from 0 to 6, while i varies from 7 - 13 for Royale
        int i = gfv.findButtonIndex(e) % DECK_LENGTH;
        PieceInterface deckPiece = PieceCache.getPiece(pieceNames[i]);
        String pieceInfo = String.format(DECK_PIECE_INFO, deckPiece.getName(), deckPiece.getCp(), deckPiece.getFaction(), deckPiece.getHp(), deckPiece.getAttackPower(), deckPiece.getDefence(), deckPiece.getAttackRange(), deckPiece.getMoveSpeed(), deckPiece.isOffensive(), deckPiece.isDefensive());
        gfv.showPieceInfo(e, pieceInfo);
    }

    public void hoverTile(MouseEvent e) {
        // show on board piece's info
        int row = gfv.findButtonCoordinates(e)[GameFrameView.ROW_PROPERTY_INDEX];
        int col = gfv.findButtonCoordinates(e)[GameFrameView.COL_PROPERTY_INDEX];
        TileInterface tile = g.getTiles()[row][col];
        if (g.isAttacking()) {
            // change attack target color if is attacking
            g.changeAttackIconColor(tile, row, col);
        }
        // check if the tile has piece, show the piece info
        if (tile instanceof PieceTile) {
            PieceInterface piece = tile.getPiece();
            String pieceInfo = String.format(ONBOARD_PIECE_INFO, piece.getName(), piece.getFaction(), piece.getHp(), piece.getAttackPower(), piece.getDefence(), piece.getAttackRange(), piece.getMoveSpeed(), piece.isOffensive(), piece.isDefensive());
            gfv.showPieceInfo(e, pieceInfo);
        }
    }

    public void saveGame() {
        if (g.saveGame()) {
            gfv.updateStatus(GAME_SAVED);
        }
    }
}

