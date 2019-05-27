package controller.menuActionListeners;

import app.Game;
import app.GameImpl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StartGameBtnActionListener implements ActionListener {

    private static final int NAME_MIN_LENGTH = 1;

    private static final String ERROR_TITLE = "Input error";
    private static final String EMPTY_PLAYER_NAME_ERROR = "Please make sure both names are entered.";

    private JFrame frame;
    private JTextField name1, name2;
    private JTextField rows, cols;
    private JComboBox undoMoves;

    public StartGameBtnActionListener(JFrame frame, JTextField name1, JTextField name2, JComboBox<String> undoMoves, JTextField rows, JTextField cols) {
        this.frame = frame;
        this.name1 = name1;
        this.name2 = name2;
        this.undoMoves = undoMoves;
        this.rows = rows;
        this.cols = cols;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (name1.getText().length() >= NAME_MIN_LENGTH && name2.getText().length() >= NAME_MIN_LENGTH) {

            ArrayList<String> playerNames = new ArrayList<>();
            playerNames.add(name1.getText());
            playerNames.add(name2.getText());

            int rowsInt = Integer.parseInt(rows.getText());
            int colsInt = Integer.parseInt(cols.getText());

            // Instantiate the GameImpl so player can start to play the game
            Game gameImpl = new GameImpl(playerNames, rowsInt, colsInt);

            // Initialise the game
            if (undoMoves.getSelectedItem() != null) {
                gameImpl.initGame(Integer.parseInt((String) undoMoves.getSelectedItem()));
            }

            // Close Enter Name View
            frame.dispose();
        } else {
            JOptionPane.showMessageDialog(frame, EMPTY_PLAYER_NAME_ERROR, ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
        }
    }
}
