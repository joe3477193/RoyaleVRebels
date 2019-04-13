package controller.actionListeners;

import controller.GameController;
import model.board.Board;
import model.pieces.Piece;
import view.GameFrameView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


// Clicking on a pieces to summon
public class SummonBtnActionListener implements ActionListener{

	private GameController c;
	
	public SummonBtnActionListener(GameController c){
		this.c = c;
	}

	public void actionPerformed(ActionEvent e) {
		c.summonButton(e);
	}
}
