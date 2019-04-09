package controller;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import model.Board;
import view.GameFrameView;

public class MoveActionListener implements ActionListener{

	private GameFrameView frame; 
	private JButton[][] tileBtns;
	Board board;


	public MoveActionListener(GameFrameView gameFrameView, Board b) {
		this.board = b;
		this.frame = gameFrameView;
		tileBtns = gameFrameView.getTileBtns();
		

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(board.isMoving() && !board.moved()) {
			board.doneMoving();
			JOptionPane.showMessageDialog(frame, "Movement cancelled.");
			frame.decolour();
			frame.getFrame().setCursor(new Cursor(0));
		}
		else if(board.hasCoordinate() && board.checkMoveInit(board.getCoordinate()[0], board.getCoordinate()[1]) && !board.moved() ) {
			if((board.getTurn() == 0 && board.getPiece(board.getCoordinate()[0], board.getCoordinate()[1]).getFaction() == "Rebel") ||
					board.getTurn() == 1 && board.getPiece(board.getCoordinate()[0], board.getCoordinate()[1]).getFaction() == "Royale") {
				board.setMoving();
				board.setInit(board.getCoordinate()[0], board.getCoordinate()[1]);
				Image icon = new ImageIcon(this.getClass().getResource(frame.getImage())).getImage();
				frame.getFrame().setCursor(Toolkit.getDefaultToolkit().createCustomCursor(icon, new Point(0, 0), "name"));
			}
			else {
				JOptionPane.showMessageDialog(frame, "You cannot move your opponent's piece.");
			}
		}
		else if(board.moved()) {
			JOptionPane.showMessageDialog(frame, "You have already moved this turn.");
		}
		else  {
			JOptionPane.showMessageDialog(frame, "You have not chosen a valid tile.");
		}
	}
}
