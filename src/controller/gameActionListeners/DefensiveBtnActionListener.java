package controller.gameActionListeners;

import com.google.java.contract.Invariant;
import controller.gameController.GameController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Invariant("c != null")
public class DefensiveBtnActionListener implements ActionListener {

    private GameController c;

    public DefensiveBtnActionListener(GameController c) {
        this.c = c;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        c.setDefensive();
    }
}