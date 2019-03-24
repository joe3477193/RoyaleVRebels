package xvy;

import java.util.ArrayList;

public class GridRows {
	
    private ArrayList<GridElement> elements;


    public GridRows() {
    	elements = new ArrayList<>(View.BOARD_COLS);

        for(int i = 0; i < View.BOARD_COLS; i++) {
        	elements.add(new GridElement());
        }
    }
    
    
    public ArrayList<GridElement> getRow() {
        if(elements != null) {
            return elements;
        } else {
            return null;
        }
    }
}
