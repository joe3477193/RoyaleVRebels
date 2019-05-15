package controller;


import controller.gameActionListeners.*;
import model.board.GameEngine;
import model.board.Tile;
import model.pieces.PieceInterface;
import view.GameFrameView;
import view.StartGameView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;

import static view.GameFrameView.STATUS;

public class GameController {
    private Timer timer;

    private GameEngine g;
    private GameFrameView gfv;
    private MoveCommand move;
    private SummonCommand summon;

    public GameController(GameEngine g, GameFrameView gfv) {
        this.g = g;
        this.gfv = gfv;

        addActionListeners();
        startTimer();
        move = new MoveCommand(g);
        summon = new SummonCommand(g);
    }

    private void addActionListeners() {
        SummonBtnActionListener summonListener = new SummonBtnActionListener(this);
        TileBtnActionListener tileListener = new TileBtnActionListener(this);

        // Add ActionListeners for summonBtns
        for (JButton btn : gfv.getSummonBtns()) {
            btn.addActionListener(summonListener);
        }

        // Add ActionListeners for tileBtns
        for (JButton[] tileRows : gfv.getTileBtns()) {
            for (JButton tile : tileRows) {
                tile.addActionListener(tileListener);
            }
        }

        // Add ActionListeners for moveBtn, attackBtn, offensiveBtn, defensiveBtn, endTurnBtn
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

        //resets the previous turn's actions such as moving and attacking
        g.resetMoving();
        g.resetAttacking();

        gfv.getFrame().setCursor(cursor);

        //if rebel's turn
        if (g.getTurn() == 0) {
            button = gfv.getRebelButton();
            name = gfv.getRebelName();
            image = gfv.getRebelImage();
        } else {
            button = gfv.getRoyaleButton();
            name = gfv.getRoyaleName();
            image = gfv.getRoyaleImage();
        }

        for (int i = 0; i < button.length; i++) {
            if (source == button[i]) {
                Image icon = new ImageIcon(this.getClass().getResource(image[i])).getImage();

                // Click on the same pieces on the deck
                if (gfv.getFrame().getCursor().getName().equals(name[i])) {
                    gfv.getFrame().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    g.removeSummonedPiece();
                    gfv.removeImage();
                }

                // Click on a different piece on the deck when the player has not moved any piece
                else if (!g.getActionPerformed()) {
                    gfv.getFrame().setCursor(Toolkit.getDefaultToolkit().createCustomCursor(icon, new Point(0, 0), name[i]));
                    g.createPiece(name[i]);
                    gfv.setImage(image[i]);
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

        //if tile is clicked when a piece is not moving nor attacking
        if (!g.isMoving() && !g.isAttacking()) {
            gfv.decolour();
        }

        //no piece is chosen in the board
        g.resetCoordinates();

        for (int i = 0; i < tileBtns.length; i++) {
            for (int j = 0; j < tileBtns[i].length; j++) {
                if (e.getSource() == tileBtns[i][j]) {

                    // Click a brick wall
                    if (g.isWall(i, j)) {
                        gfv.getStatusLabel().setText(STATUS + "Please do not click a brick wall.");
                    }

                    // Attempt to place pieces
                    else {
                        tileBtn = tileBtns[i][j];            //TODO Confirm btn needs to be passed to model in summo
                        // Attempt to place a summoned pieces
                        if (g.getSummonedPiece() != null && !g.getActionPerformed()) {

                            //Turn is consumed and run through turn command
                            summon.executeTurn(tileBtns, i, j);


                        }
                        // Attempt to place a piece during movement
                        else if (g.isMoving() && !g.getActionPerformed()) {
                            //Turn is consumed and run through turn command
                            move.executeTurn(tileBtns, i, j);


                        }
                        // Attempt to place a piece during attack
                        else if (g.isAttacking() && !g.getActionPerformed()) {
                            g.placeAttackPiece(i, j);
                        }
                        // Attempt to pick a pieces for action && also show piece info
                        else if (g.checkInit(i, j)) {
                            gfv.getStatusLabel().setText(STATUS);
                            g.clickTile(tileBtn, i, j);
                        }

                        // Attempt to click on an empty tile
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
        // Cancel movement (click move button twice)
        if (g.isAttacking() && !g.getActionPerformed()) {
            g.resetAttacking();
            gfv.colourAttack();

        }

        // Trigger movement for a piece
        else if (g.hasCoordinates() && g.checkAttackInit(g.getCoordinates()[0], g.getCoordinates()[1]) && !g.getActionPerformed()) {
            g.resetMoving();
            g.setAttacking();
        }

        // Player has performed action already
        else if (g.getActionPerformed()) {
            gfv.getStatusLabel().setText(STATUS + "You have already perform an action this turn.");
        } else {
            g.resetMoving();
            gfv.getStatusLabel().setText(STATUS + "You have not chosen a valid tile.");
        }
    }

    private void startTimer() {
        TimerTask t = new TimerTask() {

            int second = 60;

            @Override
            public void run() {
                Duration duration = Duration.ofSeconds(second--);
                gfv.setTime("Time Remaining: " + duration.toMinutesPart() + ":" + duration.toSecondsPart());
                if (second == -1) {
                    endTurn();
                }
            }
        };
        timer = new Timer();
        timer.schedule(t, 0, 1000);
    }

    private void stopTimer() {
        TimerTask t = new TimerTask() {
            int second = 60;

            @Override
            public void run() {
                Duration duration = Duration.ofSeconds(second--);
                gfv.setTime("Time Remaining: " + duration.toMinutesPart() + ":" + duration.toSecondsPart() + " ");
                if (second == -1) {
                    endTurn();
                }
            }
        };
        timer.cancel();
        timer = new Timer();
        timer.schedule(t, 0, 1000);
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
        new StartGameView();
    }

    public void saveGame() {
        try {
            PrintWriter out = new PrintWriter(new FileWriter("savegame.dat"));
            for (String data : gfv.getPlayerData()) {
                out.print(data + "|");
            }
            out.println();
            out.println(g.getTurn());
            out.println(g.getActionPerformed());

            for (Tile[] tileRow : g.getTiles()) {
                for (Tile tile : tileRow) {
                    if (tile.hasPiece()) {
                        PieceInterface piece = tile.getPiece();
                        out.printf("%d|%d|%s|%d|%n", tile.getRow(), tile.getCol(), piece.getName(), piece.getHp());
                    }
                }
            }

            out.close();
            System.out.println("Game has been successfully saved.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

