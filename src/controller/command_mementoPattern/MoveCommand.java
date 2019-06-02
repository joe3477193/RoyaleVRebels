package controller.command_mementoPattern;

import model.gameEngine.GameEngine;

public class MoveCommand implements CommandInterface {

    private static final int DAMAGE_DEALT = 0;
    private static final int PREV_HP = 0;

    private GameEngine g;
    private String image;
    private int destinationRow;
    private int destinationCol;
    private TurnType turnType;

    public MoveCommand(GameEngine g, int destinationRow, int destinationCol, String image) {
        this.g = g;
        this.destinationRow = destinationRow;
        this.destinationCol = destinationCol;
        this.image = image;
    }

    @Override
    public CommandInterface execute() {
        boolean movBool = g.placeMovedPiece(destinationRow, destinationCol);
        if (movBool) {
            turnType = new TurnType(image, g.getInitTileCoord()[g.getRowIndex()], g.getInitTileCoord()[g.getColIndex()], destinationRow, destinationCol, DAMAGE_DEALT, false, PREV_HP, null);
            // last move reference for Abstract class as per command pattern
            return this;
        }
        else return null;
    }

    @Override
    public TurnType returnTurnDetails() {
        return turnType;
    }
}
