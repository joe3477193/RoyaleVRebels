package controller.gameActionListeners;

import controller.GameController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OffensiveBtnActionListener implements ActionListener {

    private GameController c;

    public OffensiveBtnActionListener(GameController c) {
        this.c = c;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        c.setOffensive();
    }
}
