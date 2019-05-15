package controller.commandPattern;

public class TurnType {

    private String MoveType;
    private int fromRow;
    private int fromCol;
    private int tooRow;
    private int tooCol;


    TurnType(String MoveType, int fr, int fc, int tr, int tc) {
        this.MoveType = MoveType;
        this.fromRow = fromRow;
        this.fromCol = fromCol;
        this.tooRow = tooRow;
        this.tooCol = tooCol;
    }


}
