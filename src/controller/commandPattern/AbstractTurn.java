package controller.commandPattern;

import com.google.java.contract.Ensures;

import java.util.Stack;

public abstract class AbstractTurn implements Turn {

    Stack<CommandInterface> moves;

    @Ensures("moves.size() + 1 == old(moves.size())")
    public CommandInterface returnLastMove() {
        return moves.pop();
    }
}
