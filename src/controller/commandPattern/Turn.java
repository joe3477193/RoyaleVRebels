package controller.commandPattern;



public interface Turn {

	CommandInterface returnLastMove();

    void executeTurn(CommandInterface cI);

    void undoTurn();
}
