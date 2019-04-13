package controller.actionListeners;

import static java.awt.Cursor.DEFAULT_CURSOR;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import controller.GameController;
import model.board.Board;
import view.*;


// Placing a pieces on the b
public class TileBtnActionListener implements ActionListener{

	private GameController c;

	public TileBtnActionListener(GameController c) {

		this.c= c;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		c.clickTile(e);
	}
}
