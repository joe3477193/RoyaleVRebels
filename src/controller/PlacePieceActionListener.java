package controller;

import static java.awt.Cursor.DEFAULT_CURSOR;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.text.View;

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
	GameFrameView frame; 
	Boolean placing;
	
	public PlacePieceActionListener(GameFrameView frame){
		this.frame = frame;
	 
	}
	
	@SuppressWarnings({ "deprecation"})
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
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton source = (JButton) e.getSource();
		
		if(source ==  frame.spawn_General || source ==  frame.spawn_Lieutenant || source ==  frame.spawn_Footman || source ==  frame.spawn_Cannon
				|| source ==  frame.spawn_Archer || source ==  frame.spawn_Spearman) {
			placing = true;
	
			
			switch(source.getName()) {
			
			case "spawn_General":
		        	frame.getFrame().setCursor(Toolkit.getDefaultToolkit().
		                    createCustomCursor(new ImageIcon(this.getClass().getResource(frame.ONE_IMAGE)).getImage(),
		                            new Point(0, 0), "General"));
		        	System.out.println(source.getName());
		        	break;
			        
		        
			case "spawn_Lieutenant":
		        	frame.getFrame().setCursor(Toolkit.getDefaultToolkit().
		                    createCustomCursor(new ImageIcon(this.getClass().getResource(frame.TWO_IMAGE)).getImage(),
		                            new Point(0, 0), "Lieutenant"));
		            break;
			        
		        
			case "spawn_Footman":
		        	frame.getFrame().setCursor(Toolkit.getDefaultToolkit().
		                    createCustomCursor(new ImageIcon(this.getClass().getResource(frame.FOUR_IMAGE)).getImage(),
		                            new Point(0, 0), "Footman"));
		            break;
			        
		        
			case "spawn_Cannon":
		        	frame.getFrame().setCursor(Toolkit.getDefaultToolkit().
		                    createCustomCursor(new ImageIcon(this.getClass().getResource(frame.FOUR_IMAGE)).getImage(),
		                            new Point(0, 0), "Cannon"));
		            break;

		        
			case "spawn_Archer":
		        	frame.getFrame().setCursor(Toolkit.getDefaultToolkit().
		                    createCustomCursor(new ImageIcon(this.getClass().getResource(frame.FIVE_IMAGE)).getImage(),
		                            new Point(0, 0), "Archer"));
		            break;

			case "spawn_Spearman":
		        	frame.getFrame().setCursor(Toolkit.getDefaultToolkit().
		                    createCustomCursor(new ImageIcon(this.getClass().getResource(frame.THREE_IMAGE)).getImage(),
		                            new Point(0, 0), "Spearman"));
		            break;
			
			}

	       
		}

	}

}
