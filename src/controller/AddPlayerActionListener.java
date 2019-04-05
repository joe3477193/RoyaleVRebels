package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JTextField;

import model.*;
import view.*;

public class AddPlayerActionListener implements ActionListener {

    private JFrame frame;
    private String name1, name2;

    public AddPlayerActionListener(JFrame frame, JTextField name1, JTextField name2) {
        this.frame = frame;
        this.name1 = name1.getText();
        this.name2 = name2.getText();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        PlayerInfo pi = new PlayerInfo(name1, name2);
        GameFrame gameFrame = new GameFrame();
        pi.initGameFrame(gameFrame);

        // I don't know how to get the text from the field to add players
        new Board(pi.getAllPlayers());

        // I don't know how to close the add player frame after hitting the start button
        frame.setVisible(false);
    }
}
