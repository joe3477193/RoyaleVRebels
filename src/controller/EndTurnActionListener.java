package controller;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Board;
import view.GameFrameView;

public class EndTurnActionListener implements ActionListener {

	private GameFrameView game;
	private Board b;
	
	public EndTurnActionListener(GameFrameView game, Board b){
		this.game = game;
		this.b = b;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		game.getFrame().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		b.cycleTurn();
		b.setMoved(false);
		int turn= b.getTurn();
		
		game.updateBar(turn);
		
	}

	
}
