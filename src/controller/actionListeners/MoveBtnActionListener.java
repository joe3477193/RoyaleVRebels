package controller.actionListeners;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import model.board.Board;
import view.GameFrameView;

public class MoveBtnActionListener implements ActionListener{

	private GameFrameView gfv;
	private Board b;


	public MoveBtnActionListener(GameFrameView frame, Board board) {

		gfv = frame;
		b = board;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

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
}
