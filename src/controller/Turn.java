package controller;

import javax.swing.JButton;

public interface Turn {
	
	Move returnLastMove();
	void executeTurn(JButton[][] tileBtn, int i, int j);
	
}
