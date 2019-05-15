package controller.viewActionListeners;

import view.EnterNameView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewGameBtnActionListener implements ActionListener {

    private JFrame frame;

    public NewGameBtnActionListener(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new EnterNameView();
        frame.dispose();
    }
}
