package controller.command_mementoPattern;



public interface Turn {

	CommandInterface returnLastMove();

    void executeTurn(CommandInterface commandInterface);

    void undoTurn();
}
