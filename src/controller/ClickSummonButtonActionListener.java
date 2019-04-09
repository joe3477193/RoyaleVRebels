package controller;

import static java.awt.Cursor.DEFAULT_CURSOR;

import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import model.*;
import view.GameFrameView;


// Clicking on a piece to summon
public class ClickSummonButtonActionListener implements ActionListener{

	GameFrameView frame; 

	private JButton[] button;
	private String[] name;
	private String[] image;
	private Board board;
	
	public ClickSummonButtonActionListener(GameFrameView frame, Board b){
		this.frame = frame;
		this.board = b;
	}

	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton) e.getSource();
		board.doneMoving();

		if(board.getTurn()==0) {
			button= frame.getRebelButton();
			name= frame.getRebelName();
			image= frame.getRebelImage();
		}
		else {
			button= frame.getRoyalButton();
			name= frame.getRoyalName();
			image= frame.getRoyalImage();
		}
		

		for(int i=0;i<button.length;i++) {
			if(source==button[i]) {
				Image icon = new ImageIcon(this.getClass().getResource(image[i])).getImage();
				if(frame.getFrame().getCursor().getName().equals(name[i])) {
					frame.getFrame().setCursor(DEFAULT_CURSOR);
					board.removeSummonedPiece();
					frame.removeImage();
				}
				else if(!board.moved()){
					frame.getFrame().setCursor(Toolkit.getDefaultToolkit().createCustomCursor(icon, new Point(0, 0), name[i]));
					System.out.println(source.getName());
					board.setSummonedPiece(new Piece(name[i]));
					frame.setImage(image[i]);
				}
			}

		}

	}
}
