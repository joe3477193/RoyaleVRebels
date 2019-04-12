package app;

import controller.GameController;
import model.Game;
import view.GameFrameView;

import javax.swing.*;
import java.util.ArrayList;

public class StartGame {

    public StartGame(ArrayList<String> playerNames) {

        GameFrameView gfv = new GameFrameView();

        SwingUtilities.invokeLater(() -> {

            // Create Game Model
            Game game = new Game(gfv, playerNames);

            // Create Controller
            GameController gameController = new GameController();
            gameController.addView(gfv);
            gameController.addGame(game);
            gameController.initGame();

            // Add ActionListeners for pieces on deck

            // Add ActionListeners for View
            gameController.addActionListeners();

            /*// Tell View about Controller
            gfv.addController(gameController);*/
        });


    }
}
