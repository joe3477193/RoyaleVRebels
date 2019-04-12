package model;

import controller.GameController;
import view.GameFrameView;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;


public class Game{
	ArrayList<Player> players = new ArrayList<>();
	Royale royale;
	Rebel rebel;
	Player currPlayer;
	boolean isRunning = false;
	GameFrameView view;
	Board b;

	public Game(GameFrameView view, ArrayList<String> playerNames){
		
		this.view = view;
		Random r = new Random();
		int turn = r.nextInt(playerNames.size());

		// Randomly assign team for players
		if(turn == 0) {
			royale = new Royale(playerNames.get(turn));
			rebel =  new Rebel(playerNames.get(turn + 1));
		}
		else {
			royale = new Royale(playerNames.get(turn));
			rebel =  new Rebel(playerNames.get(turn - 1));
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
	
	public void initGame() {

		// Initialise board
		this.b = new Board();

		view.assembleBoard(rebel,royale, b);
		view.initSummonButtons();

		// Rebel goes first
		currPlayer = rebel;
		isRunning = true;
	}

	public Board getBoard() {
		return b;
	}
}
