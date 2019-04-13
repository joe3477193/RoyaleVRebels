package controller;

import controller.actionListeners.*;
import model.Game;
import model.board.Board;
import view.GameFrameView;

import javax.swing.*;

public class GameController{

    private Board b;
    private GameFrameView gfv;

    public GameController(Board b, GameFrameView gfv){
        this.b= b;
        this.gfv= gfv;
        addActionListeners();
    }

    public void addActionListeners() {
        SummonBtnActionListener summonListener= new SummonBtnActionListener(gfv,b);
        TileBtnActionListener tileListener= new TileBtnActionListener(gfv, b);

        // Add ActionListeners for summonBtns
        for (JButton btn : gfv.getSummonBtns()) {
            btn.addActionListener(summonListener);
        }

        // Add ActionListeners for tileBtns
        for (JButton[] tileRows : gfv.getTileBtns()) {
            for (JButton tile : tileRows) {
                tile.addActionListener(tileListener);
            }
        }

        // Add ActionListeners for moveBtn, attackBtn, endTurnBtn
        gfv.getMoveBtn().addActionListener(new MoveBtnActionListener(gfv, b));
        gfv.getAttackBtn().addActionListener(new AttackBtnActionListener(gfv, b));
        gfv.getEndTurnBtn().addActionListener(new EndTurnBtnActionListener(gfv, b));
    }
}
