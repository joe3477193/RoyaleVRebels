package controller.commandPattern;

import model.piece.AbtractPiece.PieceInterface;

public class TurnType {

    public String MoveType;
    public String image;
    public int fromRow;
    public int fromCol;
    public int tooRow;
    public int tooCol;
    public int damageDealt;
    public boolean isKilled;
    public int prevHp;
    public PieceInterface pieceInterface;

    public TurnType(String MoveType, String image, int frowRow, int fromCol, int tooRow, int tooCol, int damageDealt, boolean isKilled, int prevHp, PieceInterface pieceInterface) {
        this.MoveType = MoveType;
        this.image = image;
        this.fromRow = frowRow;
        this.fromCol = fromCol;
        this.tooRow = tooRow;
        this.tooCol = tooCol;
        this.damageDealt = damageDealt;
        this.isKilled = isKilled;
        this.prevHp = prevHp;
        this.pieceInterface = pieceInterface;
    }
}
