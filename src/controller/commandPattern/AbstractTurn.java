package controller.commandPattern;

import java.util.Stack;

public abstract class AbstractTurn implements Turn {

    Stack<TurnType> moves;

    public TurnType returnLastMove() {
        return moves.pop();
    }
}
