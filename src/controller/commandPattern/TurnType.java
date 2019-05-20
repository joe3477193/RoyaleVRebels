package controller.commandPattern;

public class TurnType {

    public String MoveType;
    public String image;
    public int fromRow;
    public int fromCol;
    public int tooRow;
    public int tooCol;

    TurnType(String MoveType,String image, int fr, int fc, int tr, int tc) {
        this.MoveType = MoveType;
        this.image = image;
        this.fromRow = fr;
        this.fromCol = fc;
        this.tooRow = tr;
        this.tooCol = tc;
    }
}
