package model;

import model.board.Board;
import model.players.Player;
import model.players.RebelPlayer;
import model.players.RoyalePlayer;
import view.GameFrameView;

import java.util.ArrayList;
import java.util.Random;


public class Game{
	ArrayList<Player> players = new ArrayList<>();
	RoyalePlayer royale;
	RebelPlayer rebel;
	Player currPlayer;
	boolean isRunning = false;
	GameFrameView gfv;
	Board b;

	public Game(GameFrameView frame, ArrayList<String> playerNames){
		
		this.gfv = frame;
		Random r = new Random();
		int turn = r.nextInt(playerNames.size());

		// Randomly assign team for players
		if(turn == 0) {
			royale = new RoyalePlayer(playerNames.get(turn));
			rebel =  new RebelPlayer(playerNames.get(turn + 1));
		}
		else {
			royale = new RoyalePlayer(playerNames.get(turn));
			rebel =  new RebelPlayer(playerNames.get(turn - 1));
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

		gfv.assembleBoard(rebel,royale, b);
		gfv.initSummonButtons();

		// RebelPlayer goes first
		currPlayer = rebel;
		isRunning = true;
	}

	public Board getBoard() {
		return b;
	}
}
