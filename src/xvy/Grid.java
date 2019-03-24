package xvy;

import java.util.ArrayList;
import java.util.List;

public class Grid {
	
	protected static ArrayList<GridRows> gridrows;
	
    public Grid(List<Player> players) {
        this.gridrows = new ArrayList<>(View.BOARD_ROWS);

        for (int i = 0; i < View.BOARD_ROWS; i++) {
        	gridrows.add(new GridRows());
        }
    }
	
    public void moveGridP() {
    		//method to update movements
    }
}
