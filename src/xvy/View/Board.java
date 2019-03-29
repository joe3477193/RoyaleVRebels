package xvy;

import java.util.ArrayList;
import java.util.List;

public class Board {
	
	protected static ArrayList<BoardRows> gridrows;
    public static final int BOARD_ROWS = 13;	//increments in 5
    public static final int BOARD_COLS = 15;	//increments in 4
	
    public Board(List<Player> players) {
        this.gridrows = new ArrayList<>(BOARD_ROWS);

        for (int i = 0; i < BOARD_ROWS; i++) {
        	gridrows.add(new BoardRows());
        }
    }
	
    public void moveGridP() {
    		//method to update movements
    }
}
