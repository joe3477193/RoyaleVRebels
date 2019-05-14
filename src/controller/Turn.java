package controller;

import javax.swing.JButton;

public interface Turn {
	
	TurnType returnLastMove();
	void executeTurn(JButton[][] tileBtn, int i, int j);
	
}
