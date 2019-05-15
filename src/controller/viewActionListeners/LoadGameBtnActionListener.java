package controller.viewActionListeners;

import app.GameImpl;
import model.board.GameEngine;
import model.board.GameEngineFacade;
import model.players.RebelPlayer;
import model.players.RoyalePlayer;
import view.GameFrameView;

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
        try {
            BufferedReader input = new BufferedReader(new FileReader("savegame.dat"));
            String[] playerName = input.readLine().split("\\|");
            String turn = input.readLine();
            String hasPerformed = input.readLine();
            ArrayList<String[]> tileData = new ArrayList<>();
            String line;

            while ((line = input.readLine()) != null) {
                String[] data = line.split("\\|");
                tileData.add(data);
            }

            // "Recover" the game status so player can continue to play the game
            gfv = new GameFrameView();
            g = new GameEngineFacade(gfv, turn, hasPerformed, tileData);
            new GameImpl(new RebelPlayer(playerName[0]), new RoyalePlayer(playerName[1]), g, gfv, tileData);
            input.close();

            frame.dispose();
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("File not found");
        } catch (IOException iOException) {
            System.out.println("Error initializing stream");
        }
    }
}
