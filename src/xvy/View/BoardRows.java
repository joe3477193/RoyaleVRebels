package xvy;

import java.util.ArrayList;

public class BoardRows {
	
    private ArrayList<Tile> elements;


    public BoardRows() {
    	elements = new ArrayList<>(Board.BOARD_COLS);

        for(int i = 0; i < Board.BOARD_COLS; i++) {
        	elements.add(new Tile());
        }
    }
    
    
    public ArrayList<Tile> getRow() {
        if(elements != null) {
            return elements;
        } else {
            return null;
        }
    }
}
