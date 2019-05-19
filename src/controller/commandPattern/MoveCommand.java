package controller.commandPattern;

import model.gameEngine.GameEngine;

import javax.swing.*;

public class MoveCommand extends AbstractTurn {


    private GameEngine g;

    public MoveCommand(GameEngine g) {
        this.g = g;
    }


    @Override
    public void executeTurn(JButton[][] tileBtns, String image, int i, int j) {

        boolean temp = g.placeMovedPiece(tileBtns, i, j);

        if (temp) {

            // last move reference for Abstract class as per command pattern
            this.lastMove = new TurnType("Move", image, g.getInitTileCoord()[0], g.getInitTileCoord()[1], i, j);
        }
    }
}
