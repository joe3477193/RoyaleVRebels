package controller.gameController;

import controller.commandPattern.AttackCommand;
import controller.commandPattern.MoveCommand;
import controller.commandPattern.SummonCommand;
import controller.gameActionListeners.*;
import controller.gameChangeListeners.HoverDeckChangeListener;
import controller.gameChangeListeners.HoverTileChangeListener;
import model.gameEngine.GameEngine;
import model.gameEngine.Tile;
import model.piece.AbtractPiece.PieceInterface;
import model.piece.PieceCache;
import view.gameView.GameFrameView;
import view.mainView.MainMenuView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

    private Timer timer;

    private GameEngine g;
    private GameFrameView gfv;


    public GameController(GameEngine g, GameFrameView gfv) {

        this.g = g;
        this.gfv = gfv;

        addActionListeners();
        startTimer();
        addChangeListeners();
    }

    private void addChangeListeners() {

        HoverTileChangeListener tileListener = new HoverTileChangeListener(this);
        HoverDeckChangeListener deckListener = new HoverDeckChangeListener(this);

        // add ChangeListeners for tileBtns
        for (JButton[] tileRows : gfv.getTileBtns()) {
            for (JButton tileBtn : tileRows) {
                tileBtn.getModel().addChangeListener(tileListener);
            }
        }

        for (JButton deckPiece : gfv.getSummonBtns()) {
            deckPiece.getModel().addChangeListener(deckListener);
        }


//        // TODO: show piece info on the deck
//        for (JButton btn : gfv.getSummonBtns()) {
//            btn.getModel().addChangeListener(new ChangeListener() {
//                @Override
//                public void stateChanged(ChangeEvent e) {
//                    if (btn.getModel().isRollover()) {
//
//                    }
//                }
//            });
//        }
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
                    gfv.getFrame().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    g.removeSummonedPiece();
                    gfv.removeImage();
                }

                // click on a different piece on the deck when the player has not moved any piece, i.e. change summon
                else if (!g.getActionPerformed()) {
                    gfv.getFrame().setCursor(Toolkit.getDefaultToolkit().createCustomCursor(icon, new Point(ORIGINAL_ROW, ORIGINAL_COL), name[i]));
                    g.createSummonedPiece(name[i]);
                    gfv.setImage(image[i]);
                    g.paintSummonRange(g.whoseTurn(), name[i]);
                } else {
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

        for (int i = g.getOriginalRow(); i < tileBtns.length; i++) {
            for (int j = g.getOriginalCol(); j < tileBtns[i].length; j++) {
                if (e.getSource() == tileBtns[i][j]) {

                    // click a brick wall
                    if (g.isWall(i, j)) {
                        gfv.getStatusLabel().setText(STATUS + "Please do not click a brick wall.");
                    }

                    // attempt to place piece
                    else {
                        tileBtn = tileBtns[i][j];
                        // attempt to place a summoned piece
                        if (g.getSummonedPiece() != null && !g.getActionPerformed()) {
                            // turn is consumed and run through turn command
                            SummonCommand sum = new SummonCommand(g);
                            sum.executeTurn(tileBtns, gfv.getImage(), i, j);
                            g.pushTurnStack(sum);


                        }
                        // attempt to place a piece during movement
                        else if (g.isMoving() && !g.getActionPerformed()) {
                            // turn is consumed and run through turn command
                            MoveCommand mc = new MoveCommand(g);
                            mc.executeTurn(tileBtns, gfv.getImage(), i, j);
                            g.pushTurnStack(mc);
                        }
                        // attempt to place a piece during attack
                        else if (g.isAttacking() && !g.getActionPerformed()) {
                            AttackCommand ac = new AttackCommand(g);
                            ac.executeTurn(tileBtns, gfv.getImage(), i, j);

                            // TODO: turn is consumed and run through turn command

                        }
                        // attempt to pick a piece for action && also show piece info
                        else if (g.checkInit(i, j)) {
                            gfv.getStatusLabel().setText(STATUS);
                            g.clickTile(tileBtn, i, j);
                        }

                        // attempt to click on an empty tile
                        else {
                            gfv.getStatusLabel().setText(STATUS);
                        }
                    }
                }
            }
        }
    }

    public void endTurn() {
        g.unsetActionPerformed();

        stopTimer();
    }

    public void attack() {

        // cancel attack, i.e. click attack button twice
        if (g.isAttacking() && !g.getActionPerformed()) {
            g.resetAttacking();
            gfv.colourAttack();

        }

        // trigger attack for a piece
        else if (g.hasCoordinates() && g.checkAttackInit(g.getCoordinates()[g.getRow()], g.getCoordinates()[g.getCol()]) && !g.getActionPerformed()) {
            g.resetMoving();
            g.setAttacking();
        }

        // check if player has performed action already
        else if (g.getActionPerformed()) {
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
        g.undoTurn();
    }

    public void quitGame() {
        gfv.getFrame().dispose();
        new MainMenuView();
    }

    public void saveGame() {

        try {
            PrintWriter output = new PrintWriter(new FileWriter("savegame.dat"));
            for (String data : gfv.getPlayerData()) {
                output.print(data + "|");
            }
            output.println();
            int[] undoLimit = g.getUndoLimit();
            output.println(undoLimit[0] + "|" + undoLimit[1]);
            output.println(g.getTurn());
            output.println(g.getActionPerformed());

            for (Tile[] tileRow : g.getTiles()) {
                for (Tile tile : tileRow) {
                    if (tile.hasPiece()) {
                        PieceInterface piece = tile.getPiece();
                        output.printf("%d|%d|%s|%d|%n", tile.getRow(), tile.getCol(), piece.getName(), piece.getHp());
                    }
                }
            }

            output.close();
            System.out.println("Game has been successfully saved.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void hoverDeck() {

        String[] pieceNames;
        JButton[] summonBtns;

        if (g.getTurn() == g.getRebelTurn()) {
            pieceNames = gfv.getRebelName();
            summonBtns = gfv.getRebelButton();
        } else {
            pieceNames = gfv.getRoyaleName();
            summonBtns = gfv.getRoyaleButton();
        }

        for (int i = 0; i < summonBtns.length; i++) {
            PieceInterface deckPiece = PieceCache.getPiece(pieceNames[i]);
            if (summonBtns[i].getModel().isRollover()) {
                String pieceInfo = "<html>Name: " + deckPiece.getName() + "<br>Faction: " + deckPiece.getFaction()
                        + "<br>Type: " + deckPiece.getType() + "<br>HP: " + deckPiece.getHp() + "<br>Attack Power: "
                        + deckPiece.getAttackPower() + "<br>Defence: " + deckPiece.getDefence() + "<br>Attack Range: "
                        + deckPiece.getAttackRange() + "<br>Move Speed: " + deckPiece.getMoveSpeed() + "<br>OFFENSIVE: "
                        + deckPiece.isOffensive() + "<br>DEFENSIVE: " + deckPiece.isDefensive() + "</html>";
                summonBtns[i].setToolTipText(pieceInfo);
            }
        }
    }

    public void hoverTile() {

        JButton[][] tileBtns = gfv.getTileBtns();

        // show on board piece's info
        for (int i = g.getOriginalRow(); i < tileBtns.length; i++) {
            for (int j = g.getOriginalCol(); j < tileBtns[i].length; j++) {
                Tile tile = g.getTiles()[i][j];
                JButton btn = gfv.getTileBtns()[i][j];

                // check if the tile has piece, show the piece info
                if (tile.hasPiece()) {
                    if (btn.getModel().isRollover()) {
                        PieceInterface piece = tile.getPiece();
                        String pieceInfo = "<html>Name: " + piece.getName() + "<br>Faction: " + piece.getFaction()
                                + "<br>Type: " + piece.getType() + "<br>HP: " + piece.getHp() + "<br>Attack Power: "
                                + piece.getAttackPower() + "<br>Defence: " + piece.getDefence() + "<br>Attack Range: "
                                + piece.getAttackRange() + "<br>Move Speed: " + piece.getMoveSpeed() + "<br>OFFENSIVE: "
                                + piece.isOffensive() + "<br>DEFENSIVE: " + piece.isDefensive() + "</html>";
                        btn.setToolTipText(pieceInfo);
                    }
                }
            }
        }
    }
}

