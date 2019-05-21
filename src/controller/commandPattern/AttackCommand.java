package controller.commandPattern;

import javax.swing.JButton;

import model.gameEngine.GameEngine;

public class AttackCommand extends AbstractTurn {

	
    private GameEngine g;

    public AttackCommand(GameEngine g) {
        this.g = g;
    }
    
    
	@Override
	public void executeTurn(JButton[][] tileBtn, String image, int i, int j) {
		
		boolean temp = g.placeAttackPiece(tileBtn, i, j);
		
		if(temp) {
			 this.lastMove = new TurnType("Attack", image, g.getInitTileCoord()[0], g.getInitTileCoord()[1], i, j);
		}
	}
	
	
}
