package app;

import controller.GameController;
import model.board.GameEngine;
import model.board.GameEngineFacade;
import model.players.Player;
import model.players.RebelPlayer;
import model.players.RoyalePlayer;
import view.GameFrameView;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;

public class GameImpl implements Game {
    private Player royale;
    private Player rebel;
    private GameFrameView gfv;
    private GameEngine g;

    public GameImpl(ArrayList<String> playerNames) {
        ArrayList<Player> players = new ArrayList<>();

        Random r = new Random();
        int turn = r.nextInt(playerNames.size());

        // Randomly assign team for players
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

    public GameImpl(GameEngine g){
        this.g= g;
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                gfv= g.getView();
                new GameController(g, gfv);
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

