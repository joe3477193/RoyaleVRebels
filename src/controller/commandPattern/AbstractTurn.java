package controller.commandPattern;


public abstract class AbstractTurn implements Turn {

    public TurnType lastMove;

    public TurnType returnLastMove() {
        return lastMove;
    }
}
