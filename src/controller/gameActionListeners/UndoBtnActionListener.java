package controller.gameActionListeners;

import controller.GameController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UndoBtnActionListener implements ActionListener {

    private GameController c;

    public UndoBtnActionListener(GameController c) {
        this.c = c;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        c.undoTurn();
    }
}
