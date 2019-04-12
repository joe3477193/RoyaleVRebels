package controller;

import app.StartGame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StartController implements ActionListener {

    private JFrame frame;
    private JTextField name1, name2;

    public StartController(JFrame frame, JTextField name1, JTextField name2) {
        this.frame = frame;
        this.name1 = name1;
        this.name2 = name2;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (name1.getText().length() > 0 && name2.getText().length() > 0) {

        	ArrayList<String> playerNames = new ArrayList<>();
        	playerNames.add(name1.getText());
        	playerNames.add(name2.getText());
        	System.out.println(name1.getText() +" "+name2.getText());

        	// Start the game
        	new StartGame(playerNames);

        	// Close Register
            frame.dispose();
        } else {
            JOptionPane.showMessageDialog(frame, "Please make sure both names are entered.", "Input error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
