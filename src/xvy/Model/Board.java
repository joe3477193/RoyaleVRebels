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
    
    
	
}
