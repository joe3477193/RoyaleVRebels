package app;

import javax.swing.JFrame;

import controller.*;
import model.*;
import view.*;

public class Game extends JFrame{
	
	
	/**
     * 
     */
    private static final long serialVersionUID = 1L;
    private View v;
	private Controller c;
	private Board b;
	
	public Game() {
		
		b = new Board();
		v = new View();
		c = new Controller(v, b);
		
		new StartPlayerInfo(new StartPlayerInfoView(c));
	}
	

	
	void showBoard() {
		

        
       
        
        //c.linkmodelview
        
        //runGame
	}
	
	public static void main(String args[]) {
		new Game();
	}

}
