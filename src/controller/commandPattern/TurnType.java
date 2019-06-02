package controller.commandPattern;

import model.piece.AbtractPiece.PieceInterface;

public class TurnType {

    private String image;
    private int fromRow;
    private int fromCol;
    private int tooRow;
    private int tooCol;
    private int damageDealt;
    private boolean death;
    private int prevHp;
    private PieceInterface p;

    public TurnType(String image, int fr, int fc, int tr, int tc, int dmg, boolean killed, int prevHp, PieceInterface p) {
        this.image = image;
        this.fromRow = fr;
        this.fromCol = fc;
        this.tooRow = tr;
        this.tooCol = tc;
        this.damageDealt = dmg;
        this.death = killed;
        this.prevHp = prevHp;
        this.p = p;
    }

    public String returnImage() {
        return image;
    }

    public int fromRow() {
        return fromRow;
    }

    public int fromCol() {
        return fromCol;
    }

    public int tooRow() {
        return tooRow;
    }

    public int tooCol() {
        return tooCol;
    }

    public int damageDealt() {
        return damageDealt;
    }

    public int prevHp() {
        return prevHp;
    }

    public boolean death() {
        return death;
    }

    public PieceInterface returnPiece() {
        return p;
    }
}
