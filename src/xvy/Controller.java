package xvy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class Controller implements ActionListener {

	private View view;
	private Grid grid;

	
    public Controller(View v) {
        this(v,null);

    }
    
    public Controller(View v, Grid g) {
        this.view = v;
        this.grid = g;

    }

 
    public void generateBoard() {
        
    }

 
    public void updateBoard() {
        
    }
    
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
