package controller.commandPattern;

import model.gameEngine.GameEngine;

import java.util.Stack;

public class CommandMonitor extends AbstractTurn {

    private static final int NUM_OF_PLAYERS = 2;

    private GameEngine g;

    public CommandMonitor(GameEngine g) {
        moves = new Stack<>();
        this.g = g;
    }

    @Override
    public void executeTurn(CommandInterface cI) {
        CommandInterface tt = cI.execute();
        if (tt != null) {
            moves.add(tt);
        }
    }

    @Override
    public void undoTurn() {
        if (moves.size() > 1 && g.checkUndoRemain()) {
            for (int i = 0; i < NUM_OF_PLAYERS; i++) {
                g.undoTurn(returnLastMove());
            }
        }
        else if (moves.size() < NUM_OF_PLAYERS) {
            g.notifyUndoRule();
        }
    }
}
