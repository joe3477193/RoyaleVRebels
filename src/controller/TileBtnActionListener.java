package controller;

import static java.awt.Cursor.DEFAULT_CURSOR;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import model.board.Board;
import view.*;


// Placing a pieces on the b
public class TileBtnActionListener implements ActionListener{

	private GameFrameView gfv;
	private JButton[][] tileBtns;
	private Board b;

	TileBtnActionListener(GameFrameView frame, Board board) {

		gfv = frame;
		tileBtns = frame.getTileBtns();
		b = board;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton tileBtn;

		// Default Cursor
		if(!b.isMoving()) {
			gfv.decolour();
		}
		b.resetCoordinates();

		for (int i = 0; i < tileBtns.length; i++) {
			for (int j = 0; j < tileBtns[i].length; j++) {
				if (e.getSource() == tileBtns[i][j]) {

					// Click a brick wall
					// How to check if it is empty click or click with a pieces???
					if ((i % 5 <= 2) && j % 4 == 3) {
						gfv.getMsgLabel().setText("You cannot place a pieces on a brick wall.");
					}

					// Attempt to place pieces
					else {
						tileBtn = tileBtns[i][j];

						// Attempt to place a summoned pieces
						if(b.getSummonedPiece()!=null && b.getAction()) {
							if (b.placePiece(b.getSummonedPiece(), i, j)) {
								tileBtn.setIcon(new ImageIcon(this.getClass().getResource(gfv.getImage())));
								tileBtn.setName(gfv.getImage());
								b.removeSummonedPiece();
								gfv.getFrame().setCursor(new Cursor(DEFAULT_CURSOR));
								System.out.println("Place here");
								b.setSummonded(true);
								b.setActionDone();
								System.out.println(b.getAction());
							}
							else {
								gfv.getMsgLabel().setText("Please place the pieces on a valid tile,\n"
										+ "The top three rows for Royales,\nThe bottom three rows for Rebels.");
							}
						}

						// Attempt to place a pieces after movement
						else if(b.isMoving() && !b.getMoved()) {
							if(b.move(b.getInitTileCoord()[0], b.getInitTileCoord()[1] , i, j)) {
								gfv.decolour();
								System.out.println("Image= "+ gfv.getImage());
								tileBtn.setIcon(new ImageIcon(this.getClass().getResource(gfv.getImage())));
								tileBtns[b.getInitTileCoord()[0]][b.getInitTileCoord()[1]].setIcon(new ImageIcon(
										this.getClass().getResource(gfv.getGrass())));
								b.doneMoving();
								b.setMoved(true);
								b.setActionDone();
								tileBtn.setName(gfv.getImage());
								gfv.getFrame().setCursor(new Cursor(DEFAULT_CURSOR));
							}
							else {
								gfv.getMsgLabel().setText("Tile not valid, press the move button again to cancel.");
							}
						}

						// Attempt to pick a pieces for movement
						else if(b.checkInit(i, j) && b.getAction()) {
							System.out.println(b.getAction());
							System.out.println(b.getMoved());
							gfv.setImage(tileBtn.getName());
							System.out.println("TileButton Name: " + tileBtn.getName());
							b.setCoordinate(i, j);
							gfv.colourTile(tileBtn);
							if(b.checkMoveInit(i, j)) {
								gfv.colourMove();
							}
						}
					}
				}
			}
		}
	}
}
