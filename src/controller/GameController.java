package controller;

import model.Game;
import view.GameFrameView;

import javax.swing.*;

public class GameController{

    private Game g;
    private GameFrameView gfv;

    public void addGame(Game game) {
        this.g = game;
    }

    public void addView(GameFrameView gfv) {
        this.gfv = gfv;
    }

    public void initGame() {
        g.initGame();
    }

    public void addActionListeners() {

        // Add ActionListeners for summonBtns
        for (JButton btn : gfv.getSummonBtns()) {
            btn.addActionListener(new SummonBtnActionListener(gfv, g.getBoard()));
        }

        // Add ActionListeners for tileBtns
        for (JButton[] tileRows : gfv.getTileBtns()) {
            for (JButton tile : tileRows) {
                tile.addActionListener(new TileBtnActionListener(gfv, g.getBoard()));
            }
        }

        // Add ActionListeners for moveBtn, attackBtn, endTurnBtn
        gfv.getMoveBtn().addActionListener(new MoveBtnActionListener(gfv, g.getBoard()));
        gfv.getAttackBtn().addActionListener(new AttackBtnActionListener(gfv, g.getBoard()));
        gfv.getEndTurnBtn().addActionListener(new EndTurnBtnActionListener(gfv, g.getBoard()));
    }
}
