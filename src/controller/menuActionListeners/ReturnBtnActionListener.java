package controller.menuActionListeners;

import view.mainView.MainMenuView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReturnBtnActionListener implements ActionListener {

    private JFrame frame;

    public ReturnBtnActionListener(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new MainMenuView();
        frame.dispose();
    }
}
