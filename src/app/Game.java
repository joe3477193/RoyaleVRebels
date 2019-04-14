package app;

import controller.GameController;
import model.board.Board;
import model.players.Player;
import model.players.RebelPlayer;
import model.players.RoyalePlayer;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.Pre;
import view.GameFrameView;

import java.util.ArrayList;
import java.util.Random;

@Guarded
public class Game{
	private RoyalePlayer royale;
	private RebelPlayer rebel;
	private GameFrameView gfv;


	public Game(@NotNull ArrayList<String> playerNames) {
        ArrayList<Player> players = new ArrayList<>();
		
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

	@Pre(expr = "_this.gfv != null && _this.rebel != null && this.royale != null", lang = "groovy")
	private void initGame() {
		// Initialise board
		Board b = new Board(gfv);
		gfv.assembleBoard(rebel,royale, b);
		new GameController(b, gfv);
	}
}