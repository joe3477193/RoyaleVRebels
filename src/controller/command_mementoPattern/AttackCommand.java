package controller.command_mementoPattern;

import model.gameEngine.GameEngine;

public class AttackCommand implements CommandInterface  {
	
	private GameEngine g;
	private int targetRow;
	private int targetCol;
	private TurnType turnType;
	
	public AttackCommand(GameEngine g, int targetRow, int targetCol) {
		this.g = g;
		this.targetRow = targetRow;
		this.targetCol=targetCol;
	}
	
	
	@Override
	public CommandInterface execute() {
		turnType = g.placeAttackPiece(targetRow, targetCol);
		if (turnType != null) {
			return this;
		}
		else 
			return null;
	}


	@Override
	public TurnType returnTurnDetails() {
		return turnType;
	}


}
