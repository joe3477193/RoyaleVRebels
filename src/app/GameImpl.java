package app;

import controller.gameController.GameController;
import model.gameEngine.GameEngine;
import model.gameEngine.GameEngineFacade;
import model.player.Player;
import model.player.RebelPlayer;
import model.player.RoyalePlayer;
import view.gameView.GameFrameView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class GameImpl implements Game {
    private Player royale;
    private Player rebel;
    private GameFrameView gfv;
    private GameEngine g;
    private ArrayList<String[]> tileData;

    public GameImpl(ArrayList<String> playerNames) {
        ArrayList<Player> players = new ArrayList<>();

        Random r = new Random();
        int turn = r.nextInt(playerNames.size());

        // Randomly assign team for player
        if (turn == 0) {
            royale = new RoyalePlayer(playerNames.get(turn));
            rebel = new RebelPlayer(playerNames.get(turn + 1));
        } else {
            royale = new RoyalePlayer(playerNames.get(turn));
            rebel = new RebelPlayer(playerNames.get(turn - 1));
        }

        players.add(rebel);
        players.add(royale);

        for (Player player : players) {
            System.out.println(player.getName());
        }
    }

    public GameImpl(Player rebel, Player royale, GameEngine g, GameFrameView gfv, ArrayList<String[]> tileData) {
        this.tileData = tileData;
        this.gfv = gfv;
        this.g = g;
        this.rebel = rebel;
        this.royale = royale;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                gfv.assembleBoard(rebel, royale, g);
                new GameController(g, gfv);
                g.setTileIcon(tileData);
            }
        });
    }

    public void initGame() {

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {

                // Instantiate the GUI view for game
                gfv = new GameFrameView();

                // Instantiate the GameEngineFacade
                g = new GameEngineFacade(gfv);
                gfv.assembleBoard(rebel, royale, g);
                new GameController(g, gfv);
            }
        });
    }
}
