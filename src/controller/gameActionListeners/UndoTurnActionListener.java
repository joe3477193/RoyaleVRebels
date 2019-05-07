package controller.gameActionListeners;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.GameController;

public class UndoTurnActionListener implements ActionListener {

	private GameController c;

	public UndoTurnActionListener( GameController c) {
		this.c= c;
	}

	@Override
	public void actionPerformed( ActionEvent e) {
		c.undoTurn();
	}
}
