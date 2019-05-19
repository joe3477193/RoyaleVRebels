package controller.commandPattern;

import model.gameEngine.GameEngine;

import javax.swing.*;

public class SummonCommand extends AbstractTurn {

    public String image;
    public int initTileRow;
    public int initTileCol;
    private GameEngine g;

    public SummonCommand(GameEngine g) {
        this.g = g;
    }

    public SummonCommand(String image, int initTileRow, int initTileCol) {
        this.image = image;
        this.initTileRow = initTileRow;
        this.initTileCol = initTileCol;
    }


    @Override
    public void executeTurn(JButton[][] tileBtn, int i, int j) {

        if (g.placeSummonedPiece(tileBtn[i][j], i, j)) {
            System.out.println("summCom");
        }
    }
}
