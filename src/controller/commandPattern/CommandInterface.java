package controller.commandPattern;

public interface CommandInterface {

    CommandInterface execute();

    TurnType returnTurnDetails();
}
