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

import static view.gameView.GameFrameView.STATUS;

public class GameController {

    private static final int TIME_LIMIT = 60;
    private static final int TIME_DELAY = 0;
    private static final int TIME_PERIOD = 1000;
    private static final int TIME_OUT = -1;
    private static final int ORIGINAL_BTN_INDEX = 0;
    private static final int ORIGINAL_ROW = 0;
    private static final int ORIGINAL_COL = 0;
    private static final int DECK_LENGTH = 7;

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

        JButton source = (JButton) e.getSource();
        Cursor cursor = gfv.getFrame().getCursor();

        JButton[] button;
        String[] name;
        String[] image;

        // reset the previous turn's actions such as moving and attacking
        g.resetMoving();
        g.resetAttacking();

        gfv.getFrame().setCursor(cursor);

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

        for (int i = ORIGINAL_BTN_INDEX; i < button.length; i++) {
            if (source == button[i]) {
                Image icon = new ImageIcon(this.getClass().getResource(image[i])).getImage();

                // click on the same piece on the deck, i.e. cancel summon
                if (gfv.getFrame().getCursor().getName().equals(name[i])) {
                    gfv.resetCursor();
                    g.removeSummonedPiece();
                    gfv.removeImage();
                }

                // click on a different piece on the deck when the player has not moved any piece, i.e. change summon
                else if (!g.getHasPerformed()) {

                    // TODO: JCOMPONENTS IN CONTROLLER
                    gfv.getFrame().setCursor(Toolkit.getDefaultToolkit().createCustomCursor(icon, new Point(ORIGINAL_ROW, ORIGINAL_COL), name[i]));
                    g.createSummonedPiece(name[i]);
                    gfv.setImage(image[i]);
                    g.paintSummonRange(name[i]);
                } else {

                    // TODO: JCOMPONENTS IN CONTROLLER
                    gfv.getStatusLabel().setText(STATUS + "You have already perform an action this turn.");
                }
            }
        }
    }

    public void clickTile(ActionEvent e) {
        JButton[][] tileBtns;
        tileBtns = gfv.getTileBtns();
        JButton tileBtn;

        // check if tile is clicked when a piece is not moving nor attacking
        if (!g.isMoving() && !g.isAttacking()) {
            gfv.decolour();
        }

        // no piece is chosen in the gameEngine
        g.resetCoordinates();

        int i = gfv.findButtonCoordinates(e)[0];
        int j = gfv.findButtonCoordinates(e)[1];
        // click a brick wall
        if (g.isWallTile(i, j)) {
            gfv.getStatusLabel().setText(STATUS + "Please do not click a brick wall.");
        }

        // attempt to place piece
        else {
            tileBtn = tileBtns[i][j];
            // attempt to place a summoned piece
            if (g.getSummonedPiece() != null && !g.getHasPerformed()) {
                // turn is consumed and run through turn command
                cm.executeTurn("Summon", gfv.getImage(), i, j, g.getSummonedPiece());

            }
            // attempt to place a piece during movement
            else if (g.isMoving() && !g.getHasPerformed()) {
                // turn is consumed and run through turn command
                cm.executeTurn("Move", gfv.getImage(), i, j, g.getSummonedPiece());

            }
            // attempt to place a piece during attack
            else if (g.isAttacking() && !g.getHasPerformed()) {

                cm.executeTurn("Attack", gfv.getImage(), i, j, g.getSummonedPiece());

            }
            // attempt to pick a piece for action && also show piece info
            else if (g.isPieceTile(i, j)) {
                gfv.getStatusLabel().setText(STATUS);
                g.clickTile(i, j);
            }

            // attempt to click on an empty tile
            else {
                gfv.getStatusLabel().setText(STATUS);
            }
        }

    }

    public void endTurn() {
        g.unsetActionPerformed();

        stopTimer();
    }

    public void attack() {

        // cancel attack, i.e. click attack button twice
        if (g.isAttacking() && !g.getHasPerformed()) {
            g.resetAttacking();
            gfv.colourAttack();

        }

        // trigger attack for a piece
        else if (g.hasCoordinates() && g.checkAttackInit(g.getCoordinates()[g.getRow()], g.getCoordinates()[g.getCol()]) && !g.getHasPerformed()) {
            g.resetMoving();
            g.setAttacking();
        }

        // check if player has performed action already
        else if (g.getHasPerformed()) {
            gfv.getStatusLabel().setText(STATUS + "You have already perform an action this turn.");
        } else {
            g.resetMoving();
            gfv.getStatusLabel().setText(STATUS + "You have not chosen a valid tile.");
        }
    }

    // reused codes in timer section
    private TimerTask timer() {

        return new TimerTask() {

            int second = TIME_LIMIT;

            @Override
            public void run() {
                Duration duration = Duration.ofSeconds(second--);
                gfv.setTime("Time Remaining: " + duration.toMinutesPart() + ":" + duration.toSecondsPart());
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
        int i = gfv.findButtonCoordinates(e)[0];
        int j = gfv.findButtonCoordinates(e)[1];

        TileInterface tile = g.getTiles()[i][j];

        // TODO: UNIMPLEMENTED
        // Change attack target color if is attacking
        // g.changeAttackTarget(tile, i, j);

        // check if the tile has piece, show the piece info
        if (tile instanceof PieceTile) {

            PieceInterface piece = ((PieceTile) tile).getPiece();

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
            System.out.println("Game has been successfully saved.");
        }
    }
}

