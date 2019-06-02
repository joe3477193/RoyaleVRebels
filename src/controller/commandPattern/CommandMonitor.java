package controller.commandPattern;

import model.gameEngine.GameEngine;
import model.piece.AbtractPiece.PieceInterface;

import java.util.Stack;

public class CommandMonitor extends AbstractTurn {

    private GameEngine g;

    public CommandMonitor(GameEngine g) {
        moves = new Stack<>();
        this.g = g;
    }

    @Override
    public void executeTurn(String type, String image, int i, int j, PieceInterface p) {
        switch (type) {
            case "Summon":
                boolean sumBool = g.placeSummonedPiece(i, j);
                if (sumBool) {
                    moves.add(new TurnType("Summon", image, 0, 0, i, j, 0, false, 0, p));
                }
                break;
            case "Move":
                boolean movBool = g.placeMovedPiece(i, j);
                if (movBool) {
                    // last move reference for Abstract class as per command pattern
                    moves.add(new TurnType("Move", image, g.getInitTileCoord()[g.getRowIndex()], g.getInitTileCoord()[g.getColIndex()], i, j, 0, false, 0, p));
                }
                break;
            case "Attack":
                TurnType turnType = g.placeAttackPiece(i, j);
                if (turnType != null) {
                    moves.add(turnType);
                }
        }
    }

    @Override
    public void undoTurn() {
        if (moves.size() > 1 && g.checkUndoRemain()) {
            for (int i = 0; i < 2; i++) {
                g.undoTurn(returnLastMove());
            }
        }
    }
}
