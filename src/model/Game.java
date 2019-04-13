package model;

import controller.GameController;
import model.board.Board;
import model.players.Player;
import model.players.RebelPlayer;
import model.players.RoyalePlayer;
import view.GameFrameView;

import java.util.ArrayList;
import java.util.Random;


public class Game{
	ArrayList<Player> players;
	RoyalePlayer royale;
	RebelPlayer rebel;
	GameFrameView gfv;
	Board b;
	GameController c;

	public Game(ArrayList<String> playerNames){
		players = new ArrayList<>();
		
		gfv= new GameFrameView();
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

		initGame();
	}
	
	public void initGame() {
		// Initialise board
		this.b = new Board(gfv);
		gfv.assembleBoard(rebel,royale, b);
		c= new GameController(b, gfv);
		// RebelPlayer goes first
	}
}
