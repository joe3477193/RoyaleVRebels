package controller.commandPattern;

import model.gameEngine.GameEngine;

public class MoveCommand implements CommandInterface {

    private GameEngine g;
    private String image;
    private int destinationRow;
    private int destinationCol;
    private TurnType tt;

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
            tt = new TurnType(image, g.getInitTileCoord()[0], g.getInitTileCoord()[1], destinationRow, destinationCol, 0, false, 0, null);
            // last move reference for Abstract class as per command pattern
            return this;
        }
        else return null;
    }

    @Override
    public TurnType returnTurnDetails() {
        return tt;
    }
}
