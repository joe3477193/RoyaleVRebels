package controller;

import controller.actionListeners.*;
import model.Game;
import model.board.Board;
import model.pieces.Piece;
import view.GameFrameView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static java.awt.Cursor.DEFAULT_CURSOR;

public class GameController{

    private Board b;
    private GameFrameView gfv;

    public GameController(Board b, GameFrameView gfv){
        this.b= b;
        this.gfv= gfv;
        addActionListeners();
    }

    public void addActionListeners() {
        SummonBtnActionListener summonListener= new SummonBtnActionListener(this);
        TileBtnActionListener tileListener= new TileBtnActionListener(this);

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

    public void summonButton(ActionEvent e){

        JButton source = (JButton) e.getSource();

        JButton[] button;
        String[] name;
        String[] image;

        // What is this line of code for?
        b.doneMoving();

        if(b.getTurn() == 0) {
            System.out.println(b.getTurn());
            button = gfv.getRebelButton();
            name = gfv.getRebelName();
            image = gfv.getRebelImage();
        }
        else {
            button = gfv.getRoyaleButton();
            name = gfv.getRoyaleName();
            image = gfv.getRoyaleImage();
        }

        for(int i = 0; i < button.length; i++) {
            if(source == button[i]) {
                Image icon = new ImageIcon(this.getClass().getResource(image[i])).getImage();

                // Click on the same pieces on the deck
                if(gfv.getFrame().getCursor().getName().equals(name[i])) {
                    gfv.getFrame().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    b.removeSummonedPiece();
                    gfv.removeImage();
                }

                // Click on a different piece on the deck when the player has not moved any piece
                // else if(!b.getAction()) ???
                else if(b.getAction()){
                    gfv.getFrame().setCursor(Toolkit.getDefaultToolkit().createCustomCursor(icon, new Point(0, 0), name[i]));
                    System.out.println(source.getName());
                    try {
                        Class pieceCls = Class.forName("model.pieces.type." + name[i]);
                        Piece piece = (Piece) pieceCls.newInstance();
                        b.setSummonedPiece(piece);
                    } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
                        ex.printStackTrace();
                    }

                    gfv.setImage(image[i]);
                }
            }
        }
    }

    public void clickTile(ActionEvent e){
        JButton[][] tileBtns;
        tileBtns = gfv.getTileBtns();
        JButton tileBtn;

        // Default Cursor
        if(!b.isMoving()) {
            gfv.decolour();
        }
        b.resetCoordinates();

        for (int i = 0; i < tileBtns.length; i++) {
            for (int j = 0; j < tileBtns[i].length; j++) {
                if (e.getSource() == tileBtns[i][j]) {

                    // Click a brick wall
                    // How to check if it is empty click or click with a pieces???
                    if ((i % 5 <= 2) && j % 4 == 3) {
                        gfv.getStatusLabel().setText(gfv.STATUS + "You cannot place a pieces on a brick wall.");
                    }

                    // Attempt to place pieces
                    else {
                        tileBtn = tileBtns[i][j];

                        // Attempt to place a summoned pieces
                        if(b.getSummonedPiece()!=null && b.getAction()) {
                            if (b.placePiece(b.getSummonedPiece(), i, j)) {
                                tileBtn.setIcon(new ImageIcon(this.getClass().getResource(gfv.getImage())));
                                tileBtn.setName(gfv.getImage());
                                b.removeSummonedPiece();
                                gfv.getFrame().setCursor(new Cursor(DEFAULT_CURSOR));
                                System.out.println("Place here");
                                b.setSummonded(true);
                                b.setActionDone();
                                System.out.println(b.getAction());
                            }
                            else {
                                gfv.getStatusLabel().setText(gfv.STATUS + "Please place the pieces on a valid tile,\n"
                                        + "The top three rows for Royales,\nThe bottom three rows for Rebels.");
                            }
                        }

                        // Attempt to place a pieces after movement
                        else if(b.isMoving() && !b.getMoved()) {
                            if(b.move(b.getInitTileCoord()[0], b.getInitTileCoord()[1] , i, j)) {
                                gfv.decolour();
                                System.out.println("Image= "+ gfv.getImage());
                                tileBtn.setIcon(new ImageIcon(this.getClass().getResource(gfv.getImage())));
                                tileBtns[b.getInitTileCoord()[0]][b.getInitTileCoord()[1]].setIcon(new ImageIcon(
                                        this.getClass().getResource(gfv.getGrass())));
                                b.doneMoving();
                                b.setMoved(true);
                                b.setActionDone();
                                tileBtn.setName(gfv.getImage());
                                gfv.getFrame().setCursor(new Cursor(DEFAULT_CURSOR));
                            }
                            else {
                                gfv.getStatusLabel().setText(gfv.STATUS + "Tile not valid, press the move button again to cancel.");
                            }
                        }

                        // Attempt to pick a pieces for movement
                        else if(b.checkInit(i, j) && b.getAction()) {
                            System.out.println(b.getAction());
                            System.out.println(b.getMoved());
                            gfv.setImage(tileBtn.getName());
                            System.out.println("TileButton Name: " + tileBtn.getName());
                            b.setCoordinate(i, j);
                            gfv.colourTile(tileBtn);
                            if(b.checkMoveInit(i, j)) {
                                gfv.colourMove();
                            }
                        }
                    }
                }
            }
        }
    }

    public void move(ActionEvent e){
        // Cancel movement (click move button twice)
        if(b.isMoving() && !b.getMoved()) {
            b.doneMoving();
            gfv.getStatusLabel().setText(gfv.STATUS + "Movement cancelled.");
            gfv.decolour();
            gfv.getFrame().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }

        // Trigger movement for a piece
        else if(b.hasCoordinates() && b.checkMoveInit(b.getCoordinates()[0], b.getCoordinates()[1]) && !b.getMoved() ) {
            if((b.getTurn() == 0 && b.getPiece(b.getCoordinates()[0], b.getCoordinates()[1]).getFaction().equals("Rebel")) ||
                    b.getTurn() == 1 && b.getPiece(b.getCoordinates()[0], b.getCoordinates()[1]).getFaction().equals("Royale")) {
                b.setMoving();
                b.setInit(b.getCoordinates()[0], b.getCoordinates()[1]);
                Image icon = new ImageIcon(this.getClass().getResource(gfv.getImage())).getImage();
                gfv.getFrame().setCursor(Toolkit.getDefaultToolkit().createCustomCursor(icon, new Point(0, 0), "name"));
            }

            // Attempt to move opposite player's piece
            else {
                gfv.getStatusLabel().setText(gfv.STATUS + "You cannot move your opponent's pieces.");
            }
        }

        // Player has getMoved already
        else if(b.getMoved()) {
            gfv.getStatusLabel().setText(gfv.STATUS + "You have already moved a piece this turn.");
        }

        else  {
            gfv.getStatusLabel().setText(gfv.STATUS + "You have not chosen a valid tile.");
        }
    }

    public void endTurn(ActionEvent e){

        if (!b.getAction()) {
            gfv.getFrame().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            b.cycleTurn();
            b.setAction(true);
            b.setMoved(false);
            gfv.updateBar(b.getTurn());
        } else {
            gfv.getFrame().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            b.cycleTurn();
            b.setAction(true);
            b.setMoved(false);
            gfv.updateBar(b.getTurn());
            // Reminder that player has not made any action in this turn
        }
    }

    public void attack(ActionEvent e){
        b.getCoordinates();
        gfv.getFrame().getCursor();
    }
}
