package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import model.*;
import view.*;

public class AddPlayerActionListener implements ActionListener {

    private JFrame frame;
    private JTextField name1, name2;

    public AddPlayerActionListener(JFrame frame, JTextField name1, JTextField name2) {
        this.frame = frame;
        this.name1 = name1;
        this.name2 = name2;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String n1 = name1.getText();
        String n2 = name2.getText();

        if (n1.length() > 0 && n2.length() > 0) {

            PlayerInfo pi = new PlayerInfo(n1, n2);

            // Game game = new Game();
            GameFrame gameFrame = new GameFrame();
            gameFrame.initGameFrame(pi.getAllPlayers().get(0));
            // pi.initGameFrame(gameFrame);

            // I don't know how to get the text from the field to add players
            new Board(pi.getAllPlayers());

            frame.dispose();
        } else {
            JOptionPane.showMessageDialog(frame, "Please make sure both names are entered.", "Input error",
                    JOptionPane.ERROR_MESSAGE);
        }

    }
}
