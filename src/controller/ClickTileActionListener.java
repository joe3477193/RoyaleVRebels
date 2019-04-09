package controller;

import static java.awt.Cursor.DEFAULT_CURSOR;

import java.awt.Color;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import javax.swing.text.View;

import model.*;
import view.*;


// Placing a piece on the board
public class ClickTileActionListener implements ActionListener{

	private GameFrameView frame; 
	private JButton[][] tileBtns;
	Board board;


	public ClickTileActionListener(GameFrameView gameFrameView) {

		this.frame = gameFrameView;
		tileBtns = gameFrameView.getTileBtns();
		board = gameFrameView.getBoard();

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		JButton tileBtn = null;
		if(!board.isMoving()) {
			frame.decolour();
		}
		board.removeCoordinate();

		for (int i = 0; i < tileBtns.length; i++) {
			for (int j = 0; j < tileBtns[i].length; j++) {
				if (e.getSource() == tileBtns[i][j]) {	
					if ((i % 5 <= 2) && j % 4 == 3) {
						JOptionPane.showMessageDialog(frame, "You cannot place a piece on a brick wall.");
					}
					else {
						tileBtn = tileBtns[i][j];
						// if attempted to place piece on top of wall
						if((frame).hasSummon() && !board.moved()) {
							if (board.placePiece(board.getSummonedPiece(), i, j)) {
								tileBtn.setIcon(new ImageIcon(this.getClass().getResource(frame.getImage())));
								tileBtn.setName(frame.getImage());
								frame.getBoard().removeSummonedPiece();
								frame.getFrame().setCursor(DEFAULT_CURSOR);
								board.setMoved(true);
								//frame.updateBar();
							}
							else {
								JOptionPane.showMessageDialog(frame, "Please place the piece on a valid tile,\n"
										+ "The top three rows for Royales,\nThe bottom three rows for Rebels.");
							}
						}
						else if(board.isMoving() && !board.moved()) {
							if(board.move(board.getInit()[0],board.getInit()[1] , i, j)) {
								frame.decolour();
								System.out.println("Image= "+frame.getImage());
								tileBtn.setIcon(new ImageIcon(this.getClass().getResource(frame.getImage())));
								tileBtns[board.getInit()[0]][board.getInit()[1]].setIcon(new ImageIcon(
										this.getClass().getResource(frame.getGrass())));
								board.doneMoving();
								board.setMoved(true);
								board.cycleTurn();
								//frame.updateBar();
								tileBtn.setName(frame.getImage());
								frame.getFrame().setCursor(0);
							}
							else {
								JOptionPane.showMessageDialog(frame, "Tile not valid, press the move button again to cancel.");
							}
						}
						else if(board.checkInit(i, j)) {
							frame.setImage(tileBtn.getName());
							System.out.println("TileButton Name: " + tileBtn.getName());
							board.setCoordinate(i, j);
							frame.colourTile(tileBtn);
							if(board.checkMoveInit(i, j)) {
								frame.colourMove();
							}
						}
					}
				}
			}
		}
	}
}
