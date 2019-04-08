package controller;

import static java.awt.Cursor.DEFAULT_CURSOR;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import model.Board;
import model.Tile;
import view.GameFrameView;


// Placing a piece on the board
public class PlacePieceActionListener implements ActionListener{
	
	JFrame gameFrameView; 
	JButton[][] tileBtns;
	Board board;
	
	public PlacePieceActionListener(GameFrameView gameFrameView){
		this.gameFrameView = gameFrameView;
		tileBtns = gameFrameView.getTileBtns();
		board = gameFrameView.getBoard();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {	
        
        for (int i = 0; i < tileBtns.length; i++) {
			for (int j = 0; j < tileBtns[i].length; j++) {
				if (e.getSource() == tileBtns[i][j]) {	
					JButton tileBtn = tileBtns[i][j];
					// if attempted to place piece on top of wall
					if ((i % 5 <= 2) && j % 4 == 3) {
						System.out.println("Attempted to place on wall");
					}
					/*// if attempted to place piece on an an occupied tile
					if (tile.hasPiece()) {
						System.out.println("Attempted to place on occupied tile");
					}*/
					else {
						tileBtn.setIcon(new ImageIcon(this.getClass().getResource(GameFrameView.ONE_IMAGE)));
						//board.placePiece(tile, piece);
						
						
					}
				}
			}
		}
	}

}
