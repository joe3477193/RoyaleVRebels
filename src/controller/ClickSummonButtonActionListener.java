package controller;

import model.Board;
import model.Piece;
import view.GameFrameView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


// Clicking on a piece to summon
public class ClickSummonButtonActionListener implements ActionListener{

	private GameFrameView gfv;

	private Board b;
	
	ClickSummonButtonActionListener(GameFrameView frame, Board board){
		gfv = frame;
		this.b = board;
	}

	public void actionPerformed(ActionEvent e) {

		JButton source = (JButton) e.getSource();

		JButton[] button;
		String[] name;
		String[] image;

		// Indicate that player cannot do actions any more in this turn
		b.doneAction();

		if(b.getTurn() == 0) {
			System.out.println(b.getTurn());
			button = gfv.getRebelButton();
			name = gfv.getRebelName();
			image = gfv.getRebelImage();
		}
		else {
			button = gfv.getRoyaleButton();
			name = gfv.getRoyaleName();
			image = gfv.getRoyaleImage();
		}

		for(int i = 0; i < button.length; i++) {
			if(source == button[i]) {
				Image icon = new ImageIcon(this.getClass().getResource(image[i])).getImage();

				// Click on the same piece on the deck
				if(gfv.getFrame().getCursor().getName().equals(name[i])) {
					gfv.getFrame().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					b.removeSummonedPiece();
					gfv.removeImage();
				}

				// Click on a different piece on the deck
				// else if(!b.getAction()) ???
				else if(!b.moved()){
					gfv.getFrame().setCursor(Toolkit.getDefaultToolkit().createCustomCursor(icon, new Point(0, 0), name[i]));
					System.out.println(source.getName());
					b.setSummonedPiece(new Piece(name[i]));
					gfv.setImage(image[i]);
				}
			}
		}
	}
}
