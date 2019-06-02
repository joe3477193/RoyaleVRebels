package controller.menuActionListeners;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExitBtnActionListener implements ActionListener {

    private static final int EXIT_SUCCESS = 0;

    private JFrame frame;

    public ExitBtnActionListener(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // close Main Menu View
        frame.dispose();
        // quit the application
        System.exit(EXIT_SUCCESS);
    }
}
