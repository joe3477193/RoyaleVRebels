package controller;

import app.Game;
import app.GameImpl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StartBtnActionListener implements ActionListener {

    private JFrame frame;
    private JTextField name1, name2;
    private JTextField rows;
    private JTextField cols;

    public StartBtnActionListener(JFrame frame,   JTextField name1,   JTextField name2, JTextField rows, JTextField cols) {
        this.frame = frame;
        this.name1 = name1;
        this.name2 = name2;
        this.rows = rows;
        this.cols = cols;
    }

    @Override
    public void actionPerformed( ActionEvent e) {

        if (name1.getText().length() > 0 && name2.getText().length() > 0 && rows.getText().length() > 0 && cols.getText().length() > 0) {

            ArrayList<String> playerNames = new ArrayList<>();
            playerNames.add(name1.getText());
            playerNames.add(name2.getText());
            System.out.println(name1.getText() +" "+name2.getText());
            int rowsInt = Integer.parseInt(rows.getText());
            int colsInt = Integer.parseInt(cols.getText());

                // Instantiate the GameImpl so player can start to play the game
                Game gameImpl = new GameImpl(playerNames, rowsInt, colsInt);

                // Initialise the game
                gameImpl.initGame();

            // Close Register Frame
            frame.dispose();
        } else {
            JOptionPane.showMessageDialog(frame, "Please make sure both names are entered.", "Input error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
