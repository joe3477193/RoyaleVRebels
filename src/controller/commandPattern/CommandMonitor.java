package controller.commandPattern;

import java.util.Stack;

import javax.swing.JButton;

import model.gameEngine.GameEngine;

public class CommandMonitor extends AbstractTurn {

	private GameEngine g;

    
    public CommandMonitor(GameEngine g) {
    	
    	moves = new Stack<TurnType>();
    	this.g =g;
    }
	
	
	@Override
	public void executeTurn(String type, JButton[][] tileBtns, String image, int i, int j) {
		
		switch(type){
			
		case "Summon":
			boolean sumBool = g.placeSummonedPiece(tileBtns[i][j], i, j);
	        if (sumBool) {
	            moves.add(new TurnType("Summon",image, 0,0,i,j,0,false,0 ));
	        }
	        break;
	        
		case "Move":
			boolean movBool = g.placeMovedPiece(tileBtns, i, j);
	        if (movBool) {
	            // last move reference for Abstract class as per command pattern
	            moves.add(new TurnType("Move", image, g.getInitTileCoord()[0], g.getInitTileCoord()[1], i, j,0,false,0));
	        }
		    break;
		    
		case "Attack":
			TurnType tt = g.placeAttackPiece(tileBtns, i, j);
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
