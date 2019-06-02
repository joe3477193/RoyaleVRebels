package controller.commandPattern;

import java.util.Stack;

public abstract class AbstractTurn implements Turn {

    Stack<CommandInterface> moves;

    public CommandInterface returnLastMove() {
        return moves.pop();
    }
}
