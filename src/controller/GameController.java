package controller;

import controller.gameActionListeners.*;
import model.board.Board;
import view.GameFrameView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static view.GameFrameView.STATUS;

public class GameController {

    private Board b;
    private GameFrameView gfv;

    public GameController(Board b, GameFrameView gfv) {
        this.b = b;
        this.gfv = gfv;
        addActionListeners();
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

        // Add ActionListeners for moveBtn, attackBtn, endTurnBtn
        gfv.getMoveBtn().addActionListener(new MoveBtnActionListener(this));
        gfv.getAttackBtn().addActionListener(new AttackBtnActionListener(this));
        gfv.getEndTurnBtn().addActionListener(new EndTurnBtnActionListener(this));
    }

    public void summonButton(ActionEvent e) {

        JButton source = (JButton) e.getSource();

        JButton[] button;
        String[] name;
        String[] image;

        if (b.getTurn() == 0) {
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

                    gfv.getStatusLabel().setText(STATUS + "Summon canceled.");
                    gfv.getFrame().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    b.removeSummonedPiece();
                    gfv.removeImage();
                }

                // Click on a different piece on the deck when the player has not moved any piece
                else if (b.canDoAction()) {
                    gfv.getStatusLabel().setText(STATUS + "You can summon piece on valid tile. The top three rows for Royales, The bottom three rows for Rebels. Click again to cancel summon.");
                    gfv.getFrame().setCursor(Toolkit.getDefaultToolkit().createCustomCursor(icon, new Point(0, 0), name[i]));
                    b.createPiece(name[i]);
                    gfv.setImage(image[i]);
                }
            }
        }
    }

    public void clickTile(ActionEvent e) {
        JButton[][] tileBtns;
        tileBtns = gfv.getTileBtns();
        JButton tileBtn;

        // Default Cursor
        if (!b.isMoving() && !b.isAttacking()) {
            gfv.decolour();
        }
        b.resetCoordinates();

        for (int i = 0; i < tileBtns.length; i++) {
            for (int j = 0; j < tileBtns[i].length; j++) {
                if (e.getSource() == tileBtns[i][j]) {

                    // Click a brick wall
                    if (b.isWall(i, j)) {
                        gfv.getStatusLabel().setText(STATUS + "Invalid action: brick wall clicked.");
                    }

                    else {

                        tileBtn = tileBtns[i][j];

                        if (b.canDoAction()) {

                            //
                            if (!b.isMoving() && !b.isAttacking()) {
                                gfv.getStatusLabel().setText(STATUS + "You can either summon, move your valid piece or attack valid opposite piece in this turn.");

                            }

                            // Attempt to place a summoned pieces
                            if (b.getSummonedPiece() != null) {
                                b.placeSummonedPiece(tileBtn, i, j);
                            }
                            // Attempt to place a pieces after movement
                            else if (b.isMoving()) {
                                b.placeMovedPiece(tileBtns, i, j);
                            }
                            else if(b.isAttacking()){
                                b.placeAttackPiece(i,j);
                            }
                            // Attempt to select a pieces for movement
                            else if (b.checkInit(i, j)) {
                                b.clickTile(tileBtn, i, j);
                            }
                        }
                        else {
                            gfv.getStatusLabel().setText(STATUS + "You cannot do any more action in this turn.");
                        }
                    }
                }
            }
        }
    }

    public void move() {

        // Cancel movement (click move button twice)
        if (b.isMoving() && !b.hasMoved()) {
            b.resetMoving();
            gfv.getStatusLabel().setText(GameFrameView.STATUS + "Movement cancelled.");
            gfv.getFrame().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }

        // Trigger movement for a piece
        else if (b.hasCoordinates() && b.checkMoveInit(b.getCoordinates()[0], b.getCoordinates()[1]) && !b.hasMoved()) {
            gfv.getStatusLabel().setText(STATUS + "You can move the piece within its move range. Click move button again to cancel movement.");
            b.setMoving();
        }

        // Player has hasMoved already
        else if (b.hasMoved()) {
            gfv.getStatusLabel().setText(STATUS + "You have already moved a piece this turn.");
        } else {
            gfv.getStatusLabel().setText(STATUS + "You have not chosen a valid tile.");
        }
    }

    public void endTurn() {
        b.resetAction();
    }

    public void attack() {
        // Cancel attack (click attack button twice)
        if (b.isAttacking() && !b.hasAttacked()) {
            b.resetAttacking();
            gfv.getFrame().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            gfv.getStatusLabel().setText(GameFrameView.STATUS + "Attack cancelled.");
        }

        // Trigger attack for a piece
        else if (b.hasCoordinates() && b.checkAttackInit(b.getCoordinates()[0], b.getCoordinates()[1]) && !b.hasMoved()) {
            gfv.getStatusLabel().setText(STATUS + "You can make the piece to attack opposite piece within its attack range.  Click attack button again to cancel attack.");
            b.setAttacking();
        }

        // Player has hasMoved already
        else if (b.hasAttacked()) {
            gfv.getStatusLabel().setText(STATUS + "You have already attacked a piece this turn.");
        } else {
            gfv.getStatusLabel().setText(STATUS + "You have not chosen a valid tile.");
        }
    }
}

