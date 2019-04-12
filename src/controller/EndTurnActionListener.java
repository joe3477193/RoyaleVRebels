package controller;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Board;
import view.GameFrameView;

public class EndTurnActionListener implements ActionListener {

	private GameFrameView gfv;
	private Board b;
	
	EndTurnActionListener(GameFrameView frame, Board board){
		gfv = frame;
		b = board;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {

		gfv.getFrame().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		b.cycleTurn();
		b.setMoved(false);
		gfv.updateBar(b.getTurn());

		new GameController().addDeckActionListeners();
	}
}
