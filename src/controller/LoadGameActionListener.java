package controller;

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

public class LoadGameActionListener implements ActionListener {
    private JFrame frame;
    private JTextField name1, name2;

    public LoadGameActionListener(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GameEngine g;
        GameFrameView gfv;
        try {
            BufferedReader input = new BufferedReader(new FileReader("savegame.dat"));
            String[] playerName = input.readLine().split("\\|");
            String turn = input.readLine();
            String hasPerformed = input.readLine();
            ArrayList<String[]> tileData = new ArrayList<String[]>();
            String line;
            while ((line = input.readLine()) != null) {
                String[] data = line.split("\\|");
                tileData.add(data);
            }
            gfv = new GameFrameView();
            g = new GameEngineFacade(gfv, turn, hasPerformed, tileData);
            GameImpl gameImpl = new GameImpl(new RebelPlayer(playerName[0]), new RoyalePlayer(playerName[1]), g, gfv, tileData);
            input.close();
            frame.dispose();
        } catch (FileNotFoundException e1) {
            System.out.println("File not found");
        } catch (IOException e2) {
            System.out.println("Error initializing stream");
        }
    }
}
