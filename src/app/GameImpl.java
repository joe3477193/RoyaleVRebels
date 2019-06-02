package app;

import com.google.java.contract.Invariant;
import com.google.java.contract.Requires;
import controller.gameController.GameController;
import model.gameEngine.GameEngine;
import model.gameEngine.GameEngineFacade;
import model.player.RebelPlayer;
import model.player.RoyalePlayer;
import view.gameView.GameFrameView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

@Invariant({"royale != null", "rebel != null", "gfv != null", "g != null", "rows > 0", "cols > 0"})
public class GameImpl implements Game {
    private RoyalePlayer royale;
    private RebelPlayer rebel;
    private GameFrameView gfv;
    private GameEngine g;

    private int rows;
    private int cols;

    public GameImpl(ArrayList<String> playerNames, int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        Random r = new Random();
        int turn = r.nextInt(playerNames.size());
        // Randomly assign team for player
        if (turn == GameEngineFacade.REBEL_TURN) {
            royale = new RoyalePlayer(playerNames.get(turn));
            rebel = new RebelPlayer(playerNames.get(turn + 1));
        }
        else {
            royale = new RoyalePlayer(playerNames.get(turn));
            rebel = new RebelPlayer(playerNames.get(turn - 1));
        }
    }

    public GameImpl(GameEngine g, GameFrameView gfv, ArrayList<String[]> tileData) {
        this.gfv = gfv;
        this.g = g;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                gfv.assembleBoard(g);
                new GameController(g, gfv);
                g.setTileIcon(tileData);
            }
        });
    }

    @Requires("undoMoves >= 0")
    public void initGame(int undoMoves) {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                GameEngineFacade.BOARD_ROW_LENGTH = rows;
                GameEngineFacade.BOARD_COL_LENGTH = cols;
                // instantiate the GUI view for game
                gfv = new GameFrameView();
                // instantiate the GameEngineFacade
                g = new GameEngineFacade(gfv, undoMoves, royale, rebel);
                gfv.assembleBoard(g);
                new GameController(g, gfv);
            }
        });
    }
}