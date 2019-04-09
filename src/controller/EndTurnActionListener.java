package controller;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.GameFrameView;

public class EndTurnActionListener implements ActionListener {

	private GameFrameView game;
	
	public EndTurnActionListener(GameFrameView game){
		this.game = game;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		game.getFrame().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		game.updateBar();
		
	}

	
}
