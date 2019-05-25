package controller.gameActionListeners;

import controller.gameController.GameController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuitBtnActionListener implements ActionListener {

    private GameController c;

    public QuitBtnActionListener(GameController c) {
        this.c = c;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        c.quitGame();
    }
}