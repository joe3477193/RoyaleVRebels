package controller.actionListeners;

import controller.GameController;
import model.board.Board;
import view.GameFrameView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AttackBtnActionListener implements ActionListener {

    private GameController c;

    public AttackBtnActionListener(GameController c){
        this.c= c;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        c.attack(e);
    }
}
