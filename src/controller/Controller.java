package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import model.*;
import view.*;

public class Controller implements ActionListener {

	private View view;
	private Board board;
	List<Player> players;
	private int playercount =0;
	
    public Controller(View v) {
        this(v,null);

    }
    
    public Controller(View v, Board b) {
    	players = new ArrayList<>();
        this.view = v;
        this.board = b;
        
    }
 
    public void updateBoard() {
        
    }
    
    public void initView() {
    	view.assembleBoard(players.get(0));
    }
    
    public void addPlayer(String p) {
    	playercount++;
    	players.add(new Player(playercount++, p));
    }
    
    public void removePlayer(String p) {
    	playercount--;
    	//TODO
    }
    
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	

}
