package controller.actionListeners;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.board.Board;
import view.GameFrameView;

public class EndTurnBtnActionListener implements ActionListener {

	private GameFrameView gfv;
	private Board b;
	
	public EndTurnBtnActionListener(GameFrameView frame, Board board){
		gfv = frame;
		b = board;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {

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
}
