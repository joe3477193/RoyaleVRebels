package model;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

import controller.GameController;
import view.GameFrameView;


public class Game{
	ArrayList<Player> players = new ArrayList<>();
	Royale royale;
	Rebel rebel;
	Player currPlayer;
	boolean isRunning = false;
	GameFrameView view;
	Board b;

	public Game(GameFrameView view, ArrayList<String> playerName){
		
		this.view = view;
		Random r = new Random();
		int t = r.nextInt(2);
		if(t==0) {
			royale = new Royale(playerName.get(t));
			rebel =  new Rebel(playerName.get(t+1));
		}
		else {
			royale = new Royale(playerName.get(t));
			rebel =  new Rebel(playerName.get(t-1));
		}
		players.add(rebel);
		players.add(royale);
		
       /* //MainGameLoop
        Thread gameLoop = new Thread(new Runnable() {
            @Override
            public void run() {
                while(isRunning) {


                	if(rebel.getCP() <= 0) {
                		//Game end
                	}
                	else if(royale.getHp()<=0) {
                		//Game end
                	}

                }
            }
        });

        gameLoop.start();*/
	}
	
	public void startGame() {

		// Initialise board
		this.b = new Board();
		
		view.assembleBoard(rebel,royale, b);
		view.initSummonButtons();
		view.moveAddActionL(new GameController());
		view.endAddActionL(new GameController());
		currPlayer = rebel;
		isRunning = true;
	}

	public Board getBoard() {
		return b;
	}

	public void init(String s) {
	}
}
