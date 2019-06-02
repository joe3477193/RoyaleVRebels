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

    private static final int REBEL_PLAYER_INDEX = 0;
    private static final int ROYALE_PLAYER_INDEX = 1;
    private static final int DEFAULT_UNDO_LEVEL = 0;

    private static final String REGEX = "\\|";
    private static final String FILE_NOT_FOUND_EXCEPTION_MSG = "File not found.";
    private static final String IOEXCEPTION_MSG = "Error initializing file stream.";
    private static final String FNFE_TITLE = "FileNotFound";
    private static final String IOE_TITLE = "IOE";

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
            BufferedReader input = new BufferedReader(new FileReader(GameEngineFacade.FULL_SAVE_FILE_NAME));
            //Load castle hp
            String castleHp = input.readLine();
            //Load board size
            String[] boardSize = input.readLine().split(REGEX);
            // Load players' names
            String[] playerName = input.readLine().split(REGEX);
            //Load undo level
            String[] undoLevel = input.readLine().split(REGEX);
            // Load current turn
            String turn = input.readLine();
            // Load hasPerformed
            String hasPerformed = input.readLine();
            // Load pieces' status, e.g. hp
            ArrayList<String[]> tileData = new ArrayList<>();
            String line;
            while ((line = input.readLine()) != null) {
                String[] data = line.split(REGEX);
                tileData.add(data);
            }
            // "Recover" the game status so player can continue to play the game
            gfv = new GameFrameView();
            GameEngineFacade.BOARD_ROW_LENGTH = Integer.parseInt(boardSize[GameEngineFacade.ROW_INDEX]);
            GameEngineFacade.BOARD_COL_LENGTH = Integer.parseInt(boardSize[GameEngineFacade.COL_INDEX]);
            g = new GameEngineFacade(gfv, DEFAULT_UNDO_LEVEL, new RoyalePlayer(playerName[ROYALE_PLAYER_INDEX]), new RebelPlayer(playerName[REBEL_PLAYER_INDEX]));
            g.loadGame(castleHp, undoLevel, turn, hasPerformed, tileData);
            // For adding Controller using existed Game model
            new GameImpl(g, gfv, tileData);
            // Close file stream
            input.close();
            // Close Main Menu View
            frame.dispose();
        } catch (FileNotFoundException fileNotFoundException) {
            JOptionPane.showMessageDialog(frame, FILE_NOT_FOUND_EXCEPTION_MSG, FNFE_TITLE, JOptionPane.ERROR_MESSAGE);
        } catch (IOException iOException) {
            JOptionPane.showMessageDialog(frame, IOEXCEPTION_MSG, IOE_TITLE, JOptionPane.ERROR_MESSAGE);
        }
    }
}
