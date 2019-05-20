package app;

import controller.gameController.GameController;
import model.gameEngine.GameEngine;
import model.gameEngine.GameEngineFacade;
import model.player.RebelPlayer;
import model.player.RoyalePlayer;
import view.gameView.GameFrameView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class GameImpl implements Game {
    private RoyalePlayer royale;
    private RebelPlayer rebel;
    private GameFrameView gfv;
    private GameEngine g;
    private ArrayList<String[]> tileData;
    private int rows;
    private int cols;
    

    public GameImpl(ArrayList<String> playerNames, int rows, int cols) {

        this.rows = rows;
        this.cols = cols;

        Random r = new Random();
        int turn = r.nextInt(playerNames.size());

        // randomly assign team for player
        if (turn == 0) {
            royale = new RoyalePlayer(playerNames.get(turn));
            rebel = new RebelPlayer(playerNames.get(turn + 1));
        } else {
            royale = new RoyalePlayer(playerNames.get(turn));
            rebel = new RebelPlayer(playerNames.get(turn - 1));
        }




    }

    public GameImpl(GameEngine g, GameFrameView gfv, ArrayList<String[]> tileData) {
        this.tileData = tileData;
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

    public void initGame(int undo) {

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {

                // instantiate the GUI view for game
                gfv = new GameFrameView();

                // instantiate the GameEngineFacade
                g = new GameEngineFacade(gfv, undo,royale,rebel);
                GameEngineFacade.BOARD_MAX_ROWS = rows;
                GameEngineFacade.BOARD_MAX_COLS = cols;
                gfv.assembleBoard(g);
                new GameController(g, gfv);
            }
        });
    }
}
