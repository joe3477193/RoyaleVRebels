package controller;

import model.board.Board;
import view.GameFrameView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AttackBtnActionListener implements ActionListener {
    private GameFrameView gfv;
    private Board b;

    AttackBtnActionListener(GameFrameView frame, Board board){
        gfv = frame;
        b = board;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        b.getCoordinates();
        gfv.getFrame().getCursor();
    }
}
