package controller;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import model.Board;
import view.GameFrameView;

public class MoveActionListener implements ActionListener{

	private GameFrameView gfv;
	private Board board;


	MoveActionListener(GameFrameView frame, Board b) {

		gfv = frame;
		board = b;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(board.isMoving() && !board.moved()) {
			board.doneMoving();
			JOptionPane.showMessageDialog(gfv, "Movement cancelled.");
			gfv.decolour();
			gfv.getFrame().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		else if(board.hasCoordinates() && board.checkMoveInit(board.getCoordinate()[0], board.getCoordinate()[1]) && !board.moved() ) {
			if((board.getTurn() == 0 && board.getPiece(board.getCoordinate()[0], board.getCoordinate()[1]).getFaction().equals("Rebel")) ||
					board.getTurn() == 1 && board.getPiece(board.getCoordinate()[0], board.getCoordinate()[1]).getFaction().equals("Royale")) {
				board.setMoving();
				board.setInit(board.getCoordinate()[0], board.getCoordinate()[1]);
				Image icon = new ImageIcon(this.getClass().getResource(gfv.getImage())).getImage();
				gfv.getFrame().setCursor(Toolkit.getDefaultToolkit().createCustomCursor(icon, new Point(0, 0), "name"));
			}
			else {
				JOptionPane.showMessageDialog(gfv, "You cannot move your opponent's piece.");
			}
		}
		else if(board.moved()) {
			JOptionPane.showMessageDialog(gfv, "You have already moved this turn.");
		}
		else  {
			JOptionPane.showMessageDialog(gfv, "You have not chosen a valid tile.");
		}
	}
}
