package xvy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class Controller implements ActionListener {

	private View view;
	private Board board;

	
    public Controller(View v) {
        this(v,null);

    }
    
    public Controller(View v, Board b) {
        this.view = v;
        this.board = b;
        
       // v.assembleBoard(playerOne);

    }


 
    public void updateBoard() {
        
    }
    
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	

}
