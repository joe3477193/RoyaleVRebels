package controller.menuActionListeners;

import app.Game;
import app.GameImpl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StartGameBtnActionListener implements ActionListener {

    private JFrame frame;
    private JTextField name1, name2;
    private JComboBox undoMoves;

    public StartGameBtnActionListener(JFrame frame, JTextField name1, JTextField name2, JComboBox<String> undoMoves2) {
        this.frame = frame;
        this.name1 = name1;
        this.name2 = name2;
        this.undoMoves = undoMoves2;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (name1.getText().length() > 0 && name2.getText().length() > 0) {

            ArrayList<String> playerNames = new ArrayList<>();
            playerNames.add(name1.getText());
            playerNames.add(name2.getText());
            System.out.println(name1.getText() + " " + name2.getText());

            // Instantiate the GameImpl so player can start to play the game
            Game gameImpl = new GameImpl(playerNames);

            // Initialise the game
            gameImpl.initGame(Integer.parseInt((String) undoMoves.getSelectedItem()));

            // Close Register Frame
            frame.dispose();
        } else {
            JOptionPane.showMessageDialog(frame, "Please make sure both names are entered.", "Input error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
