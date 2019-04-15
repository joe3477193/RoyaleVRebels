package controller;

import controller.gameActionListeners.*;
import model.board.Board;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Pre;
import view.GameFrameView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.Duration;
import java.util.Timer; 
import java.util.TimerTask;

import static view.GameFrameView.STATUS;

public class GameController {
    Timer timer;

    private Board b;
    private GameFrameView gfv;

    public GameController(@NotNull Board b, @NotNull GameFrameView gfv) {
        this.b = b;
        this.gfv = gfv;
        addActionListeners();
        startTimer();
    }

    @Pre(expr = "_this.gfv != null", lang = "groovy")
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

    @Pre(expr = "_this.gfv != null && _this.b != null", lang = "groovy")
    public void summonButton(@NotNull ActionEvent e) {

        JButton source = (JButton) e.getSource();
        Cursor cursor= gfv.getFrame().getCursor();

        JButton[] button;
        String[] name;
        String[] image;

        //resets the previous turn's actions such as moving and attacking
        b.resetMoving();
        b.resetAttacking();

        gfv.getFrame().setCursor(cursor);

        //if rebel's turn
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
                    gfv.getFrame().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    b.removeSummonedPiece();
                    gfv.removeImage();
                }

                // Click on a different piece on the deck when the player has not moved any piece
                else if (!b.getActionPerformed()) {
                    gfv.getFrame().setCursor(Toolkit.getDefaultToolkit().createCustomCursor(icon, new Point(0, 0), name[i]));
                    b.createPiece(name[i]);
                    gfv.setImage(image[i]);
                } else {
                    gfv.getStatusLabel().setText(STATUS + "You have already perform an action this turn.");
                }
            }
        }
    }

    @Pre(expr = "_this.gfv != null && _this.b != null", lang = "groovy")
    public void clickTile(@NotNull ActionEvent e) {
        JButton[][] tileBtns;
        tileBtns = gfv.getTileBtns();
        JButton tileBtn;

        //if tile is clicked when a piece is not moving nor attacking
        if (!b.isMoving() && !b.isAttacking()) {
            gfv.decolour();
        }

        //no piece is chosen in the board
        b.resetCoordinates();

        for (int i = 0; i < tileBtns.length; i++) {
            for (int j = 0; j < tileBtns[i].length; j++) {
                if (e.getSource() == tileBtns[i][j]) {

                    // Click a brick wall
                    if (b.isWall(i, j)) {
                        gfv.getStatusLabel().setText(STATUS + "Please do not click a brick wall.");
                    }

                    // Attempt to place pieces
                    else {
                        tileBtn = tileBtns[i][j];
                        // Attempt to place a summoned pieces
                        if (b.getSummonedPiece() != null && !b.getActionPerformed()) {
                            b.placeSummonedPiece(tileBtn, i, j);
                        }
                        // Attempt to place a piece during movement
                        else if (b.isMoving() && !b.getActionPerformed()) {
                            b.placeMovedPiece(tileBtns, i, j);
                        }
                        // Attempt to place a piece during attack
                        else if (b.isAttacking() && !b.getActionPerformed()) {
                            b.placeAttackPiece(i, j);
                        }
                        // Attempt to pick a pieces for action && also show piece info
                        else if (b.checkInit(i, j)) {
                            gfv.getStatusLabel().setText(STATUS);
                            b.clickTile(tileBtn, i, j);
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

    @Pre(expr = "_this.gfv != null && _this.b != null", lang = "groovy")
    public void move() {
        // Cancel movement (click move button twice)
        if (b.isMoving() && !b.getActionPerformed()) {
            b.resetMoving();
            gfv.colourMove();
            gfv.colourAttack();
        }

        // Trigger movement for a piece
        else if (b.hasCoordinates() && b.checkMoveInit(b.getCoordinates()[0], b.getCoordinates()[1]) && !b.getActionPerformed()) {
            b.resetAttacking();
            b.setMoving();
        }

        // Player has getActionPerformed already
        else if (b.getActionPerformed()) {
            gfv.getStatusLabel().setText(STATUS + "You have already perform an action this turn.");
        } else {
            b.resetAttacking();
            gfv.getStatusLabel().setText(STATUS + "You have not chosen a valid tile.");
        }
    }

    @Pre(expr = "_this.b != null", lang = "groovy")
    public void endTurn() {
        b.unsetActionPerformed();
        stopTimer();
    }

    @Pre(expr = "_this.gfv != null && _this.b != null", lang = "groovy")
    public void attack() {
        // Cancel movement (click move button twice)
        if (b.isAttacking() && !b.getActionPerformed()) {
            b.resetAttacking();
            gfv.colourAttack();
            gfv.colourMove();
        }

        // Trigger movement for a piece
        else if (b.hasCoordinates() && b.checkAttackInit(b.getCoordinates()[0], b.getCoordinates()[1]) && !b.getActionPerformed()) {
            b.resetMoving();
            b.setAttacking();
        }

        // Player has performed action already
        else if (b.getActionPerformed()) {
            gfv.getStatusLabel().setText(STATUS + "You have already perform an action this turn.");
        } else {
            b.resetMoving();
            gfv.getStatusLabel().setText(STATUS + "You have not chosen a valid tile.");
        }
    }
    
    public void startTimer() {
        TimerTask t = new TimerTask(){

        	int second = 60;
         
       	 	@Override
       	 	public void run() {
       	 		Duration duration = Duration.ofSeconds(second--);	 
       	 		gfv.setTime("Time Remaining: "+duration.toMinutesPart()+":"+duration.toSecondsPart());
	       	 	if(second == -1) {
	   	 			endTurn();
	   	 		}
       	 	} 
       	 };
        timer = new Timer();
        timer.schedule(t,0, 1000);  
    }
    
    public void stopTimer() {
        TimerTask t = new TimerTask(){
        	int second = 60;
          
       	 	@Override
       	 	public void run() {
       	 		Duration duration = Duration.ofSeconds(second--);	 
       	 		gfv.setTime("Time Remaining: "+duration.toMinutesPart()+":"+duration.toSecondsPart()+" ");
       	 		if(second == -1) {
       	 			endTurn();
       	 		}
       	 	} 
       	 };
       	timer.cancel();
       	timer = new Timer();
        timer.schedule(t,0, 1000); 
    }
}

