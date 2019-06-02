package controller.command_mementoPattern;

import model.gameEngine.GameEngine;

public class SummonCommand implements CommandInterface {

    private static final int FROM_ROW = 0;
    private static final int FROM_COL = 0;
    private static final int DAMAGE_DEALT = 0;
    private static final int PREV_HP = 0;

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
            tt = new TurnType(image, FROM_ROW, FROM_COL, row, col, DAMAGE_DEALT, false, PREV_HP, null);
            return this;
        }
        else return null;
    }

    @Override
    public TurnType returnTurnDetails() {
        return tt;
    }
}
