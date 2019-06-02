package controller.commandPattern;

import java.util.Stack;

public abstract class AbstractTurn implements Turn {

    public Stack<TurnType> moves;

    public TurnType returnLastMove() {
        return moves.pop();
    }
}
