package xvy.Model;

import java.util.ArrayList;
import java.util.List;

import xvy.Controller.*;
import xvy.View.*;
import xvy.Model.*;

public class Board {
	
	List<Player> playersB;
	
	protected static ArrayList<BoardRows> gridrows;
    public static final int BOARD_ROWS = 13;	//increments in 5
    public static final int BOARD_COLS = 15;	//increments in 4
	
    public Board() {
    	
        this.gridrows = new ArrayList<>(BOARD_ROWS);
        
        this.playersB = new ArrayList<>();;
        
        for (int i = 0; i < BOARD_ROWS; i++) {
        	gridrows.add(new BoardRows());
        }
    }
	
    public void moveGridP() {
    	
    		//method to update movements
    }
    
    private boolean checkInit(int row, int tile) {
    	return gridrows.get(row).getTile(tile).hasCard();
    }
    
    public boolean checkMoveInit(int row, int tile) {
    	return checkInit(row, tile) && gridrows.get(row).getTile(tile).getCard().isMoveable();
    }
    
    public boolean checkAttackInit(int row, int tile) {
    	return checkInit(row, tile) && gridrows.get(row).getTile(tile).getCard().isAttackable();
    }
    
    public boolean checkMoveTarget(int row, int tile) {
    	return !gridrows.get(row).getTile(tile).hasCard();
    }
    
    public boolean checkAttackTarget(Card card, int row, int tile) {
    	Tile space = gridrows.get(row).getTile(tile);
    	if (space.hasCard() && card.getFaction() != space.getCard().getFaction()) {
    		return true;
    	}
    	return false;
    }
    
    public void move(int inRow, int inTile, int tgRow, int tgTile) {
    	Tile inSpace = gridrows.get(inRow).getTile(inTile);
    	Tile tgSpace = gridrows.get(tgRow).getTile(tgTile);
    	tgSpace.setCard(inSpace.getCard());
    	inSpace.removeCard();
    }
    
    public void attack(Card card, int row, int tile) {
    	Tile space = gridrows.get(row).getTile(tile);
    	Card tgCard = space.getCard();
    	tgCard.attackBy(card.getAttack());
    	if (tgCard.isDead()) {
    		space.removeCard();
    	}
    }
    
	
}
