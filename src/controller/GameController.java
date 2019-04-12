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

        for (JButton[] tileRows : gfv.getTileBtns()) {
            for (JButton tile : tileRows) {
                tile.addActionListener(new ClickTileActionListener(gfv, g.getBoard()));
            }
        }

        gfv.getMoveBtn().addActionListener(new MoveActionListener(gfv, g.getBoard()));

        gfv.getAttackBtn().addActionListener(new AttackActionListener());

        gfv.getEndTurnBtn().addActionListener(new EndTurnActionListener(gfv, g.getBoard()));
    }

    public void addDeckActionListeners() {
        for (JButton btn : gfv.getSummonButtons()) {
            btn.addActionListener(new ClickSummonButtonActionListener(gfv, g.getBoard()));
        }
    }
}
