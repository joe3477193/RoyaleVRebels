package controller.menuActionListeners;

import app.GameImpl;
import model.gameEngine.GameEngine;
import model.gameEngine.GameEngineFacade;
import model.player.RebelPlayer;
import model.player.RoyalePlayer;
import view.gameView.GameFrameView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LoadGameBtnActionListener implements ActionListener {

    private JFrame frame;

    public LoadGameBtnActionListener(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        GameEngine g;
        GameFrameView gfv;

        // Load the game status from saved file
        // TODO: Need to save more details of the pieces on board, e,g, hp, ap, ar, ms
        try {
            BufferedReader input = new BufferedReader(new FileReader("savegame.dat"));

            // Load players' names
            String[] playerName = input.readLine().split("\\|");

            //Load undo limits
            String[] undoLimit = input.readLine().split("\\|");

            // Load turn
            String turn = input.readLine();

            // Load actionPerformed
            String hasPerformed = input.readLine();

            // Load pieces' status, e.g. hp
            // TODO
            ArrayList<String[]> tileData = new ArrayList<>();
            String line;

            while ((line = input.readLine()) != null) {
                String[] data = line.split("\\|");
                tileData.add(data);
            }

            // "Recover" the game status so player can continue to play the game
            gfv = new GameFrameView();
            g = new GameEngineFacade(gfv, undoLimit, turn, hasPerformed, tileData, new RebelPlayer(playerName[0]), new RoyalePlayer(playerName[1]));
            new GameImpl(g, gfv, tileData);
            input.close();

            frame.dispose();
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("File not found");
        } catch (IOException iOException) {
            System.out.println("Error initializing stream");
        }
    }
}
