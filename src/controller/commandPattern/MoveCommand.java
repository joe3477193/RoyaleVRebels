package controller.commandPattern;

import model.gameEngine.GameEngine;

import javax.swing.*;

public class MoveCommand extends AbstractTurn {

    public String image;
    public int fromTileRow;
    public int fromTileCol;
    public int tooTileRow;
    public int tooTileCol;
    private GameEngine g;


    public MoveCommand(GameEngine g) {
        this.g = g;
    }


    public MoveCommand(String image, int fromTileRow, int fromTileCol, int tooTileRow, int tooTileCol) {
        this.image = image;
        this.fromTileRow = fromTileRow;
        this.fromTileCol = fromTileCol;
        this.tooTileRow = tooTileRow;
        this.tooTileCol = tooTileCol;


    }


    @Override
    public void executeTurn(JButton[][] tileBtns, int i, int j) {

        boolean temp = g.placeMovedPiece(tileBtns, i, j);

        if (temp) {
            //Last move reference for Abstract class as per command pattern
            this.lastMove = new TurnType("Move", g.getInitTileCoord()[0], g.getInitTileCoord()[1], i, j);
        }

    }


}
