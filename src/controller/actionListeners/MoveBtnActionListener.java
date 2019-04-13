package controller.actionListeners;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import controller.GameController;
import model.board.Board;
import view.GameFrameView;

public class MoveBtnActionListener implements ActionListener{

	private GameController c;

	public MoveBtnActionListener(GameController c) {
		this.c= c;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		c.move(e);
	}
}
