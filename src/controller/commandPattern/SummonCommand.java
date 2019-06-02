package controller.commandPattern;

import model.gameEngine.GameEngine;

public class SummonCommand implements CommandInterface {

    private GameEngine g;
    private int row;
    private int col;
    private String image;
    private TurnType tt;

    public SummonCommand(GameEngine g, int row, int col, String image) {
        this.g = g;
        this.row = row;
        this.col = col;
        this.image = image;
    }

    @Override
    public CommandInterface execute() {
        boolean sumBool = g.placeSummonedPiece(row, col);
        if (sumBool) {
            tt = new TurnType(image, 0, 0, row, col, 0, false, 0, null);
            return this;
        }
        else return null;
    }

    @Override
    public TurnType returnTurnDetails() {
        return tt;
    }
}
