package controller.actionListeners;

import model.board.Board;
import model.pieces.Piece;
import view.GameFrameView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


// Clicking on a pieces to summon
public class SummonBtnActionListener implements ActionListener{

	private GameFrameView gfv;

	private Board b;
	
	public SummonBtnActionListener(GameFrameView frame, Board board){
		gfv = frame;
		b = board;
	}

	public void actionPerformed(ActionEvent e) {

		JButton source = (JButton) e.getSource();

		JButton[] button;
		String[] name;
		String[] image;

		// What is this line of code for?
		b.doneMoving();

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

				// Click on the same pieces on the deck
				if(gfv.getFrame().getCursor().getName().equals(name[i])) {
					gfv.getFrame().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					b.removeSummonedPiece();
					gfv.removeImage();
				}

				// Click on a different piece on the deck when the player has not moved any piece
				// else if(!b.getAction()) ???
				else if(b.getAction()){
					gfv.getFrame().setCursor(Toolkit.getDefaultToolkit().createCustomCursor(icon, new Point(0, 0), name[i]));
					System.out.println(source.getName());
					try {
						Class pieceCls = Class.forName("model.pieces.type." + name[i]);
						Piece piece = (Piece) pieceCls.newInstance();
						b.setSummonedPiece(piece);
					} catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
						ex.printStackTrace();
					}

					gfv.setImage(image[i]);
				}
			}
		}
	}
}
