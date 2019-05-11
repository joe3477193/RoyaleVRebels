package controller;



public abstract class AbstractTurn implements Turn {

	
	public Move lastMove;
	
	
	public Move returnLastMove() {
	
		return lastMove;
	}
	
	
}
