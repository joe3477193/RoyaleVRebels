package controller.commandPattern;

import model.gameEngine.GameEngine;
import model.piece.AbtractPiece.PieceInterface;

import java.util.Stack;

public class CommandMonitor extends AbstractTurn {

    private static final String SUMMON_TYPE = "Summon";
    private static final String MOVEMENT_TYPE = "Move";
    private static final String ATTACK_TYPE = "Attack";
    private static final int NUMBER_OF_PLAYERS = 2;

    private GameEngine g;

    public CommandMonitor(GameEngine g) {
        moves = new Stack<>();
        this.g = g;
    }

    @Override
    public void executeTurn(String type, String image, int row, int col, PieceInterface pieceInterface) {
        switch (type) {
            case SUMMON_TYPE:
                boolean sumBool = g.placeSummonedPiece(row, col);
                if (sumBool) {
                    moves.add(new TurnType(SUMMON_TYPE, image, 0, 0, row, col, 0, false, 0, pieceInterface));
                }
                break;
            case MOVEMENT_TYPE:
                boolean movBool = g.placeMovedPiece(row, col);
                if (movBool) {
                    // last move reference for Abstract class as per command pattern
                    moves.add(new TurnType(MOVEMENT_TYPE, image, g.getInitTileCoord()[g.getRowIndex()], g.getInitTileCoord()[g.getColIndex()], row, col, 0, false, 0, pieceInterface));
                }
                break;
            case ATTACK_TYPE:
                TurnType turnType = g.placeAttackPiece(row, col);
                if (turnType != null) {
                    moves.add(turnType);
                }
        }
    }

    @Override
    public void undoTurn() {
        if (moves.size() > 1 && g.checkUndoRemain()) {
            for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
                g.undoTurn(returnLastMove());
            }
        }
    }
}
