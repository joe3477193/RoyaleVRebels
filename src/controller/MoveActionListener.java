package controller;

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


	public MoveActionListener(GameFrameView gameFrameView) {

		this.frame = gameFrameView;
		tileBtns = gameFrameView.getTileBtns();
		board = gameFrameView.getBoard();

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(board.isMoving()) {
			board.doneMoving();
			JOptionPane.showMessageDialog(frame, "Movement cancelled.");
			frame.decolour();
			frame.getFrame().setCursor(0);
		}
		else if(board.hasCoordinate() && board.checkMoveInit(board.getCoordinate()[0], board.getCoordinate()[1])) {
			board.setMoving();
			board.setInit(board.getCoordinate()[0], board.getCoordinate()[1]);
			Image icon = new ImageIcon(this.getClass().getResource(frame.getImage())).getImage();
			frame.getFrame().setCursor(Toolkit.getDefaultToolkit().createCustomCursor(icon, new Point(0, 0), "name"));
		}
		else {
			JOptionPane.showMessageDialog(frame, "You have not chosen any tile.");
		}
	}
}
