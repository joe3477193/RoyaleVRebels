package controller;

import view.EnterNameView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewGameActionListener implements ActionListener {

    JFrame frame;

    public NewGameActionListener(JFrame frame) {
        this.frame= frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new EnterNameView();
        frame.dispose();
    }
}
