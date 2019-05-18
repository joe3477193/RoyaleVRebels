package controller.menuActionListeners;

import view.subView.AboutView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AboutBtnActionListener implements ActionListener {

    private JFrame frame;

    public AboutBtnActionListener(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new AboutView();
        frame.dispose();
    }
}
