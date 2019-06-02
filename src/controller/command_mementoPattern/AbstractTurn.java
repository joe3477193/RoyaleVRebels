package controller.command_mementoPattern;

import java.util.Stack;

public abstract class AbstractTurn implements Turn {

    Stack<CommandInterface> moves;

    public CommandInterface returnLastMove() {
        return moves.pop();
    }
}
