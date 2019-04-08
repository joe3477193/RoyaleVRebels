package model;

import java.util.ArrayList;
import java.util.Random;

import view.GameFrameView;

public class Game {
	ArrayList<Player> players = new ArrayList<>();
	Royale royal;
	Rebel rebel;
	Player currPlayer;
	boolean isRunning = false;
	GameFrameView view;
	
	public Game(GameFrameView view, ArrayList<String> playerName){
		
		this.view = view;
		Random r = new Random();
		int t = r.nextInt(2);
		if(t==0) {
			royal = new Royale(playerName.get(t));
			rebel =  new Rebel(playerName.get(t+1));
		}
		else {
			royal = new Royale(playerName.get(t));
			rebel =  new Rebel(playerName.get(t-1));
		}
		players.add(rebel);
		players.add(royal);
		
		startGame();
        
        Thread gameLoop = new Thread(new Runnable() {
            @Override
            public void run() {
                while(isRunning) {
                	//MainGameLoop
                }
            }
        });
	
	}
	
	public void startGame() {
		Board b = new Board();
		view.assembleBoard(rebel, b);
		currPlayer = rebel;
		isRunning = true;
	}
}
