package controller.commandPattern;

import model.gameEngine.GameEngine;

import javax.swing.*;

public class SummonCommand extends AbstractTurn {

    private GameEngine g;

    public SummonCommand(GameEngine g) {
        this.g = g;
    }


    @Override
    public void executeTurn(JButton[][] tileBtn,String image, int i, int j) {
    	
    	boolean temp = g.placeSummonedPiece(i, j);
    	
        if (temp) {
            this.lastMove = new TurnType("Summon",image, 0,0,i,j );
        }
    }
}
