package controller;

import javax.swing.JButton;

import model.board.GameEngine;

public class SummonCommand extends AbstractTurn  {
	
	public String image;
	private GameEngine g;
	public int initTileRow;
	public int initTileCol;
	
	
	public SummonCommand(GameEngine g) {
		this.g = g;
		
	}
	
	public SummonCommand(String image, int initTileRow, int initTileCol) {
		this.image = image;
		this.initTileRow = initTileRow;
		this.initTileCol =initTileCol;
	
	}
		
	
	@Override
	public void executeTurn(JButton[][] tileBtn, int i, int j) {
		// TODO Auto-generated method stub
		if(g.placeSummonedPiece(tileBtn[i][j], i, j)) {
			System.out.println("summCom");
		}
		
	}

}
