package controller;

import static java.awt.Cursor.DEFAULT_CURSOR;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import model.*;
import view.*;


// Placing a piece on the board
public class ClickTileActionListener implements ActionListener{

	private GameFrameView gfv;
	private JButton[][] tileBtns;
	private Board board;


	ClickTileActionListener(GameFrameView frame, Board b) {

		gfv = frame;
		tileBtns = frame.getTileBtns();
		board = b;

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		JButton tileBtn;

		// Default Cursor
		if(!board.isMoving()) {
			gfv.decolour();
		}
		board.resetCoordinates();

		for (int i = 0; i < tileBtns.length; i++) {
			for (int j = 0; j < tileBtns[i].length; j++) {
				if (e.getSource() == tileBtns[i][j]) {

					// Click a brick wall
					// How to check if it is empty click or click with a piece???
					if ((i % 5 <= 2) && j % 4 == 3) {
						JOptionPane.showMessageDialog(gfv, "You cannot place a piece on a brick wall.");
					}

					// Attempt to place piece
					else {
						tileBtn = tileBtns[i][j];

						// Attempt to place a summoned piece
						if(board.getSummonedPiece()!=null && !board.moved()) {
							if (board.placePiece(board.getSummonedPiece(), i, j)) {
								tileBtn.setIcon(new ImageIcon(this.getClass().getResource(gfv.getImage())));
								tileBtn.setName(gfv.getImage());
								board.removeSummonedPiece();
								gfv.getFrame().setCursor(new Cursor(DEFAULT_CURSOR));
								board.setMoved(true);
								//gfv.updateBar();
							}
							else {
								JOptionPane.showMessageDialog(gfv, "Please place the piece on a valid tile,\n"
										+ "The top three rows for Royales,\nThe bottom three rows for Rebels.");
							}
						}

						// Attempt to place a piece after movement
						else if(board.isMoving() && !board.moved()) {
							if(board.move(board.getInitTileCoord()[0],board.getInitTileCoord()[1] , i, j)) {
								gfv.decolour();
								System.out.println("Image= "+ gfv.getImage());
								tileBtn.setIcon(new ImageIcon(this.getClass().getResource(gfv.getImage())));
								tileBtns[board.getInitTileCoord()[0]][board.getInitTileCoord()[1]].setIcon(new ImageIcon(
										this.getClass().getResource(gfv.getGrass())));
								board.doneMoving();
								board.setMoved(true);
								tileBtn.setName(gfv.getImage());
								gfv.getFrame().setCursor(new Cursor(DEFAULT_CURSOR));
							}
							else {
								JOptionPane.showMessageDialog(gfv, "Tile not valid, press the move button again to cancel.");
							}
						}

						// Attempt to pick a piece for movement
						else if(board.checkInit(i, j)) {
							gfv.setImage(tileBtn.getName());
							System.out.println("TileButton Name: " + tileBtn.getName());
							board.setCoordinate(i, j);
							gfv.colourTile(tileBtn);
							if(board.checkMoveInit(i, j)) {
								gfv.colourMove();
							}
						}
					}
				}
			}
		}
	}
}
