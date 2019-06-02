package controller.commandPattern;

import model.gameEngine.GameEngine;
import model.piece.AbtractPiece.PieceInterface;

import java.util.Stack;

public class CommandMonitor extends AbstractTurn {

    private GameEngine g;

    public CommandMonitor(GameEngine g) {
        moves = new Stack<>();
        this.g = g;
    }

    @Override
    public void executeTurn(CommandInterface cI) {
    	
    	CommandInterface tt = cI.execute();
    	if( tt != null) {
    		moves.add(tt);
    	}
    	
    }

    @Override
    public void undoTurn() {
        if (moves.size() > 1 && g.checkUndoRemain()) {
            for (int i = 0; i < 2; i++) {
                g.undoTurn(returnLastMove());
            }
        }
        else if(moves.size()<2) {
        	g.notifyUndoRule();
        }
    }
}
