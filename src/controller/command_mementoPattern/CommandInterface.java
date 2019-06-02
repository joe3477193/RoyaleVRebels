package controller.command_mementoPattern;

public interface CommandInterface {

	CommandInterface execute();
	
	TurnType returnTurnDetails();
}
