package controller.menuActionListeners;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExitBtnActionListener implements ActionListener {

    private JFrame frame;

    public ExitBtnActionListener(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        frame.dispose();
        System.exit(0);
    }
}
