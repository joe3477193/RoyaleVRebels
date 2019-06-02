package controller.commandPattern;

import com.google.java.contract.Invariant;
import com.google.java.contract.Requires;
import model.gameEngine.GameEngine;

@Invariant({"g != null", "targetRow >= 0", "targetCol >=0"})
public class AttackCommand implements CommandInterface {

    private GameEngine g;
    private int targetRow;
    private int targetCol;
    private TurnType tt;

    public AttackCommand(GameEngine g, int targetRow, int targetCol) {
        this.g = g;
        this.targetRow = targetRow;
        this.targetCol = targetCol;
    }

    @Override
    public CommandInterface execute() {
        tt = g.placeAttackPiece(targetRow, targetCol);
        if (tt != null) {
            return this;
        }
        else return null;
    }

    @Override
    @Requires("tt != null")
    public TurnType returnTurnDetails() {
        return tt;
    }
}
