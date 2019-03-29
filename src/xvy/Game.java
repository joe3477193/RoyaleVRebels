package xvy;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import xvy.Controller.*;
import xvy.View.*;
import xvy.Model.*;

public class Game extends JFrame{
	
	
	private View v;
	private Controller c;
	private Board b;
	
	Game() {
		
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
