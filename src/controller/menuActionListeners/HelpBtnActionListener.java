package controller.menuActionListeners;

import view.subView.HelpView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HelpBtnActionListener implements ActionListener {

    private JFrame frame;

    public HelpBtnActionListener(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new HelpView();
        // close Main Menu View
        frame.dispose();
    }
}
