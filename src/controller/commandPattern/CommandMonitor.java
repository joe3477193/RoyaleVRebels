package controller.commandPattern;

import java.util.Stack;

import javax.swing.JButton;

import model.gameEngine.GameEngine;
import model.piece.AbtractPiece.PieceInterface;

public class CommandMonitor extends AbstractTurn {

	private GameEngine g;

    
    public CommandMonitor(GameEngine g) {
    	
    	moves = new Stack<TurnType>();
    	this.g =g;
    }
	
	
	@Override
	public void executeTurn(String type, String image, int i, int j, PieceInterface p) {

		switch(type){
			
		case "Summon":
			boolean sumBool = g.placeSummonedPiece( i, j);
	        if (sumBool) {
	            moves.add(new TurnType("Summon",image, 0,0,i,j,0,false,0,p ));
	        }
	        break;
	        
		case "Move":
			boolean movBool = g.placeMovedPiece(i, j);
	        if (movBool) {
	            // last move reference for Abstract class as per command pattern
	            moves.add(new TurnType("Move", image, g.getInitTileCoord()[0], g.getInitTileCoord()[1], i, j,0,false,0,p));
	        }
		    break;
		    
		case "Attack":
			TurnType tt = g.placeAttackPiece(i, j);
			if(tt != null) {
				moves.add(tt);
			}
		}
				
	}

	@Override
	public void undoTurn() {
		if(moves.size()>1 && g.checkUndoRem()) {
			for(int i =0; i<2; i++) {
				g.undoTurn(returnLastMove());
			}
		}
	}
	
	

}
