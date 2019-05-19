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
		// TODO Auto-generated method stub
		
		
	}
	
	
}
